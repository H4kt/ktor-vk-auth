package dev.h4kt.ktorVkAuth.services.vk.auth

import dev.h4kt.ktorVkAuth.services.vk.auth.results.VkAuthenticationResult
import io.ktor.http.*

interface IVkAuthService {

    fun authenticate(
        queryParameters: Parameters
    ): VkAuthenticationResult

}
