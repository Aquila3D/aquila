package org.aquila3d.core.vulkan

import org.aquila3d.core.vulkan.debug.VkDebugUtilsMessengerCallbackCreateInfo

internal expect fun getRequiredInstanceExtensions(): MutableList<String>

expect class VkInstance(
    applicationInfo: VkApplicationInfo,
    requiredExtensions: List<String>,
    layers: List<String>,
    debugUtilsMessengerCallbackCreateInfo: VkDebugUtilsMessengerCallbackCreateInfo? = null
) {
    fun destroy()
    fun getCapabilities(): List<String>
}
