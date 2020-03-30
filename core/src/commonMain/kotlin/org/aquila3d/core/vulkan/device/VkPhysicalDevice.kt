package org.aquila3d.core.vulkan.device

import org.aquila3d.core.surface.swapchain.SwapchainFeatures
import org.aquila3d.core.vulkan.memory.VkPhysicalDeviceMemoryProperties

enum class VkQueueFamilies(val simpleName: String) {
    VK_QUEUE_GRAPHICS("Graphics"),
    VK_QUEUE_COMPUTE("Compute"),
    VK_QUEUE_TRANSFER("Transfer"),
    VK_QUEUE_SPARSE_BINDING("Sparse Binding"),
    VK_QUEUE_PROTECTED("Protected"),
    VK_QUEUE_PRESENTATION("Presentation")
}

expect class VkPhysicalDevice {
    fun getQueueFamilyIndices(): Map<VkQueueFamilies, Int>
    fun getDeviceExtensions(): Map<String, Int>
    fun getSwapchainFeatures(): SwapchainFeatures
    fun getMemoryProperties(): VkPhysicalDeviceMemoryProperties
}
