package org.aquila3d.core.vulkan

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
}