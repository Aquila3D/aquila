package org.aquila3d.core.vulkan

import org.lwjgl.system.MemoryUtil.memAllocInt
import org.lwjgl.system.MemoryUtil.memFree
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VK11
import org.lwjgl.vulkan.VkQueueFamilyProperties

actual class VkPhysicalDevice(internal val device: org.lwjgl.vulkan.VkPhysicalDevice) {
    actual fun getQueueFamilyIndices(): Map<VkQueueFamilies, Int> {
        val pQueueFamilyPropertyCount = memAllocInt(1)
        vkGetPhysicalDeviceQueueFamilyProperties(device, pQueueFamilyPropertyCount, null)
        val queueCount = pQueueFamilyPropertyCount.get(0)
        val queueProps = VkQueueFamilyProperties.calloc(queueCount)
        vkGetPhysicalDeviceQueueFamilyProperties(device, pQueueFamilyPropertyCount, queueProps)
        memFree(pQueueFamilyPropertyCount)
        val queueFamilies = mutableMapOf<VkQueueFamilies, Int>()
        for (index in 0 until queueCount) {
            val flags = queueProps.get(index).queueFlags()
            when {
                (flags and VK_QUEUE_GRAPHICS_BIT) != 0 -> {
                    queueFamilies[VkQueueFamilies.VK_QUEUE_GRAPHICS] = index
                }
                (flags and VK_QUEUE_COMPUTE_BIT) != 0 -> {
                    queueFamilies[VkQueueFamilies.VK_QUEUE_COMPUTE] = index
                }
                (flags and VK_QUEUE_TRANSFER_BIT) != 0 -> {
                    queueFamilies[VkQueueFamilies.VK_QUEUE_TRANSFER] = index
                }
                (flags and VK_QUEUE_SPARSE_BINDING_BIT) != 0 -> {
                    queueFamilies[VkQueueFamilies.VK_QUEUE_SPARSE_BINDING] = index
                }
                (flags and VK11.VK_QUEUE_PROTECTED_BIT) != 0 -> {
                    queueFamilies[VkQueueFamilies.VK_QUEUE_PROTECTED] = index
                }
            }
        }
        queueProps.free()
        return queueFamilies
    }
}