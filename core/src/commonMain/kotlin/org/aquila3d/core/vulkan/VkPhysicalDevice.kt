package org.aquila3d.core.vulkan

enum class VkQueueFamilies(val queueFamily: String) {
    VK_QUEUE_GRAPHICS("VK_QUEUE_GRAPHICS_BIT"),
    VK_QUEUE_COMPUTE("VK_QUEUE_COMPUTE_BIT"),
    VK_QUEUE_TRANSFER("VK_QUEUE_TRANSFER_BIT"),
    VK_QUEUE_SPARSE_BINDING("VK_QUEUE_SPARSE_BINDING_BIT"),
    VK_QUEUE_PROTECTED("VK_QUEUE_PROTECTED_BIT")
}

expect class VkPhysicalDevice {
    fun getQueueFamilyIndices(): Map<VkQueueFamilies, Int>
}