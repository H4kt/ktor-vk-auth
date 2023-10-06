package dev.h4kt.ktorVkAuth.services.vk.auth.results

sealed class VkAuthenticationResult {

    data object NoSignatureProvided : VkAuthenticationResult()
    data object NoUserIdProvided : VkAuthenticationResult()

    data object InvalidSignature : VkAuthenticationResult()

    data class Success(
        val vkUserId: String
    ) : VkAuthenticationResult()

}
