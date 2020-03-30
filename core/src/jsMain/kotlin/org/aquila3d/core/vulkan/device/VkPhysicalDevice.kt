package org.aquila3d.core.vulkan.device

import org.aquila3d.core.surface.swapchain.SwapchainFeatures
import org.aquila3d.core.vulkan.device.VkQueueFamilies
import org.aquila3d.core.vulkan.memory.VkPhysicalDeviceMemoryProperties

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
