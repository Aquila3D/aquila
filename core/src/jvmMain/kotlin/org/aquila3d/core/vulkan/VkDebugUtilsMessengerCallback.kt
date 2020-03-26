package org.aquila3d.core.vulkan

import com.toxicbakery.logging.Arbor
import org.lwjgl.vulkan.EXTDebugUtils.*
import org.lwjgl.vulkan.VK10.VK_FALSE
import org.lwjgl.vulkan.VkDebugUtilsMessengerCallbackDataEXT
import org.lwjgl.vulkan.VkDebugUtilsMessengerCallbackEXT

actual open class VkDebugUtilsMessengerCallback {

    internal val function: VkDebugUtilsMessengerCallbackEXT = VkDebugUtilsMessengerCallbackEXT.create {
            messageSeverity, messageTypes, pCallbackData, pUserData ->
        val type: String = when {
            messageTypes and VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT != 0 -> {
                "GENERAL"
            }
            messageTypes and VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT != 0 -> {
                "VALIDATION"
            }
            messageTypes and VK_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT != 0 -> {
                "PERFORMANCE WARNING"
            }
            else -> {
                "UNKNOWN"
            }
        }
        val data = VkDebugUtilsMessengerCallbackDataEXT.create(pCallbackData)
        val message = String.format("%s: [%s] Code %d : %s\n", type, data.pMessageString())
        when (messageSeverity) {
            VK_DEBUG_UTILS_MESSAGE_SEVERITY_VERBOSE_BIT_EXT -> {
                Arbor.v(message)
            }
            VK_DEBUG_UTILS_MESSAGE_SEVERITY_INFO_BIT_EXT -> {
                Arbor.i(message)
            }
            VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT -> {
                Arbor.w(message)
            }
            VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT -> {
                Arbor.e(message)
            }
        }
        /*
             * false indicates that layer should not bail-out of an API call that had validation failures. This may mean
             * that the app dies inside the driver due to invalid parameter(s). That's what would happen without
             * validation layers, so we'll keep that behavior here.
             */
        VK_FALSE
    }
}