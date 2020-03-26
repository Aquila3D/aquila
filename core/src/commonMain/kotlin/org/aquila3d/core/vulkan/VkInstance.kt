package org.aquila3d.core.vulkan

expect class VkInstance(
    applicationInfo: VkApplicationInfo,
    requiredExtensions: List<String>,
    layers: List<String>,
    debugUtilsMessengerCallbackCreateInfo: VkDebugUtilsMessengerCallbackCreateInfo? = null
) {
    fun destroy()
    fun getCapabilities(): List<String>
}