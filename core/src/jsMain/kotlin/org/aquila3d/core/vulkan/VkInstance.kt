package org.aquila3d.core.vulkan

actual class VkInstance actual constructor(
    applicationInfo: VkApplicationInfo,
    requiredExtensions: List<String>,
    layers: List<String>,
    debugUtilsMessengerCallbackCreateInfo: VkDebugUtilsMessengerCallbackCreateInfo?
) {
    actual fun destroy() {
    }

    actual fun getCapabilities(): List<String> {
        TODO("Not yet implemented")
    }

}