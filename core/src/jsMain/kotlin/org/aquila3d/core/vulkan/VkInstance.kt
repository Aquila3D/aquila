package org.aquila3d.core.vulkan

import org.aquila3d.core.vulkan.debug.VkDebugUtilsMessengerCallbackCreateInfo

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

internal actual fun getRequiredInstanceExtensions(): MutableList<String> {
    TODO("Not yet implemented")
}
