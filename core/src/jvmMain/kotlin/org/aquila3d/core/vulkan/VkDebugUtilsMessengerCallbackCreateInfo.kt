package org.aquila3d.core.vulkan

import org.lwjgl.system.MemoryUtil.NULL
import org.lwjgl.vulkan.EXTDebugUtils
import org.lwjgl.vulkan.EXTDebugUtils.*
import org.lwjgl.vulkan.VkDebugUtilsMessengerCreateInfoEXT


actual class VkDebugUtilsMessengerCallbackCreateInfo(internal val callback: VkDebugUtilsMessengerCallback) {

    /**
     * It is expected that the JVM implementation of [VkInstance] will free the native memory associated with this.
     */
    internal var dbgCreateInfo: VkDebugUtilsMessengerCreateInfoEXT = VkDebugUtilsMessengerCreateInfoEXT.malloc()
        .sType(VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT)
        .pNext(NULL)
        .flags(0)
        .pfnUserCallback(callback.function)
        .pUserData(NULL)
        .messageSeverity(
            VK_DEBUG_UTILS_MESSAGE_SEVERITY_VERBOSE_BIT_EXT or
                    VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT or
                    VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT
        )
        .messageType(
            VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT or
                    VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT or
                    VK_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT
        )

}