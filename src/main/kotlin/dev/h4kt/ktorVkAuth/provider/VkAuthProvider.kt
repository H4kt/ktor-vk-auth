package dev.h4kt.ktorVkAuth.provider

import dev.h4kt.ktorVkAuth.services.vk.auth.results.VkAuthenticationResult
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*

class VkAuthProvider(
    private val config: VkAuthConfig
) : AuthenticationProvider(config) {

    private val authService = config.serviceFactory(config.secretKey)

    override suspend fun onAuthenticate(context: AuthenticationContext) {

        val call = context.call

        val authHeader = call
            .request
            .headers[HttpHeaders.Authorization]
            ?.split(" ")
            ?: run {
                call.respond(HttpStatusCode.Unauthorized)
                return
            }

        if (authHeader.first() != "VK") {
            call.respond(HttpStatusCode.Unauthorized)
            return
        }

        val rawUrl = authHeader.getOrNull(1)
            ?: run {
                call.respond(HttpStatusCode.Unauthorized)
                return
            }

        val url = try {
            Url(rawUrl)
        } catch (ex: Exception) {
            call.respond(HttpStatusCode.Unauthorized)
            return
        }

        val authResult = authService.authenticate(url.parameters)

        if (authResult !is VkAuthenticationResult.Success) {
            call.respond(HttpStatusCode.Unauthorized)
            return
        }

        val principal = config.authenticate(
            context.call,
            authResult.vkUserId
        )

        if (principal != null) {
            context.principal(principal)
        } else {
            call.respond(HttpStatusCode.Unauthorized)
        }

    }

}

fun AuthenticationConfig.vk(
    name: String? = null,
    configure: (VkAuthConfig.() -> Unit)? = null
) {
    val provider = VkAuthProvider(VkAuthConfig(name).apply { configure?.invoke(this) })
    register(provider)
}
