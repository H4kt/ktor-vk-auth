package dev.h4kt.ktorVkAuth.services.vk.auth

import dev.h4kt.ktorVkAuth.services.vk.auth.results.VkAuthenticationResult
import io.ktor.http.*

interface VkAuthService {

    fun authenticate(
        queryParameters: Parameters
    ): VkAuthenticationResult

}
