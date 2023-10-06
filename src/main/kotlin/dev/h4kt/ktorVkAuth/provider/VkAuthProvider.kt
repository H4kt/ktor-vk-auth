package dev.h4kt.ktorVkAuth.provider

import dev.h4kt.ktorVkAuth.services.vk.auth.results.VkAuthenticationResult
import io.ktor.http.*
import io.ktor.server.auth.*
import java.lang.Exception

class VkAuthProvider(
    private val config: VkAuthConfig
) : AuthenticationProvider(config) {

    private val authService = config.serviceFactory(config.secretKey)

    override suspend fun onAuthenticate(context: AuthenticationContext) {

        val authHeader = context
            .call
            .request
            .headers[HttpHeaders.Authorization]
            ?.split(" ")
            ?: return

        if (authHeader.first() != "VK") {
            return
        }

        val rawUrl = authHeader.getOrNull(1)
            ?: return

        val url = try {
            Url(rawUrl)
        } catch (ex: Exception) {
            return
        }

        val authResult = authService.authenticate(url.parameters)

        if (authResult !is VkAuthenticationResult.Success) {
            return
        }

        val principal = config.authenticate(
            context.call,
            authResult.vkUserId
        )

        principal?.let {
            context.principal(it)
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
