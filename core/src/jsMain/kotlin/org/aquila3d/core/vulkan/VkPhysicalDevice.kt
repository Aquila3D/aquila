package org.aquila3d.core.vulkan

import org.aquila3d.core.surface.swapchain.SwapchainFeatures

actual class VkPhysicalDevice {
    actual fun getQueueFamilyIndices(): Map<VkQueueFamilies, Int> {
        TODO("Not yet implemented")
    }

    actual fun getDeviceExtensions(): Map<String, Int> {
        TODO("Not yet implemented")
    }

    actual fun getSwapchainFeatures(): SwapchainFeatures {
        TODO("Not yet implemented")
    }

    actual fun getMemoryProperties(): VkPhysicalDeviceMemoryProperties {
        TODO("Not yet implemented")
    }
}
