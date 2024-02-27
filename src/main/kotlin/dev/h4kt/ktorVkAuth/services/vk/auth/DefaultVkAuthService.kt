package dev.h4kt.ktorVkAuth.services.vk.auth

import dev.h4kt.ktorVkAuth.services.vk.auth.results.VkAuthenticationResult
import io.ktor.http.*
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class DefaultVkAuthService(
    secretKey: String
) : VkAuthService {

    private val secretKey = SecretKeySpec(
        secretKey.encodeToByteArray(),
        "HmacSHA256"
    )

    override fun authenticate(
        queryParameters: Parameters
    ): VkAuthenticationResult {

        val signature = queryParameters["sign"]
            ?: return VkAuthenticationResult.NoSignatureProvided

        val vkUserId = queryParameters["vk_user_id"]
            ?: return VkAuthenticationResult.NoUserIdProvided

        val vkParams = queryParameters
            .entries()
            .filter { (key, _) -> key.startsWith("vk_") }
            .sortedBy { it.key }
            .joinToString("&") { (key, values) ->

                val encodedKey = key.encodeURLQueryComponent()

                val encodedValue = values.joinToString(",")
                    .encodeURLQueryComponent()
                    .replace(",", "%2C")

                return@joinToString "$encodedKey=$encodedValue"
            }

        val signatureBytes = Mac
            .getInstance("HmacSHA256")
            .apply { init(secretKey) }
            .doFinal(vkParams.encodeToByteArray())

        val computedSignature = Base64.getEncoder()
            .withoutPadding()
            .encodeToString(signatureBytes)
            .replace("/", "_")
            .replace("+", "-")
            .replace("=$", "")

        return if (signature == computedSignature) {
            VkAuthenticationResult.Success(
                vkUserId = vkUserId
            )
        } else {
            VkAuthenticationResult.InvalidSignature
        }
    }

}
