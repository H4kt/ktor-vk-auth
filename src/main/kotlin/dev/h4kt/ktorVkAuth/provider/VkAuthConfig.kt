package dev.h4kt.ktorVkAuth.provider

import dev.h4kt.ktorVkAuth.services.vk.auth.IVkAuthService
import dev.h4kt.ktorVkAuth.services.vk.auth.VkAuthService
import io.ktor.server.application.*
import io.ktor.server.auth.*

typealias AuthServiceFactory = (secretKey: String) -> IVkAuthService
typealias AuthenticationFunction = suspend ApplicationCall.(vkUserId: String) -> Principal?

class VkAuthConfig(
    name: String?
) : AuthenticationProvider.Config(name) {

    var secretKey: String = ""
    var serviceFactory: AuthServiceFactory = ::VkAuthService

    var authenticate: AuthenticationFunction = { null }

}
