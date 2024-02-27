package dev.h4kt.ktorVkAuth.services.vk.auth.results

sealed interface VkAuthenticationResult {

    sealed interface Error : VkAuthenticationResult

    data object NoSignatureProvided : Error
    data object NoUserIdProvided : Error

    data object InvalidSignature : Error

    data class Success(
        val vkUserId: String
    ) : VkAuthenticationResult

}
