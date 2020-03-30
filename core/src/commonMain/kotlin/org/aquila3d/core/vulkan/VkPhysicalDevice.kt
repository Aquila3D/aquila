package org.aquila3d.core.vulkan

import org.aquila3d.core.surface.swapchain.SwapchainFeatures

enum class VkQueueFamilies {
    VK_QUEUE_GRAPHICS,
    VK_QUEUE_COMPUTE,
    VK_QUEUE_TRANSFER,
    VK_QUEUE_SPARSE_BINDING,
    VK_QUEUE_PROTECTED,
    VK_QUEUE_PRESENTATION
}

expect class VkPhysicalDevice {
    fun getQueueFamilyIndices(): Map<VkQueueFamilies, Int>
    fun getDeviceExtensions(): Map<String, Int>
    fun getSwapchainFeatures(): SwapchainFeatures
    fun getMemoryProperties(): VkPhysicalDeviceMemoryProperties
}
