package org.aquila3d.core.vulkan

import com.toxicbakery.logging.Arbor
import org.aquila3d.core.surface.Surface
import org.lwjgl.system.MemoryUtil.memAllocInt
import org.lwjgl.system.MemoryUtil.memFree
import org.lwjgl.vulkan.KHRSurface.vkGetPhysicalDeviceSurfaceSupportKHR
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VK11
import org.lwjgl.vulkan.VkQueueFamilyProperties


actual class VkPhysicalDevice(internal val device: org.lwjgl.vulkan.VkPhysicalDevice, private val surface: Surface) {

    private val queueFamilies: Map<VkQueueFamilies, Int> by lazy {
        val queueCountPointer = memAllocInt(1)
        vkGetPhysicalDeviceQueueFamilyProperties(device, queueCountPointer, null)
        val queueCount = queueCountPointer.get(0)
        val queueProps = VkQueueFamilyProperties.calloc(queueCount)
        vkGetPhysicalDeviceQueueFamilyProperties(device, queueCountPointer, queueProps)
        memFree(queueCountPointer)
        val queueFamilies = mutableMapOf<VkQueueFamilies, Int>()
        val supportsPresent = memAllocInt(1)
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
            supportsPresent.position(0)
            val err = vkGetPhysicalDeviceSurfaceSupportKHR(device, index, surface.surfaceHandle, supportsPresent)
            if (err != VK_SUCCESS) {
                Arbor.e("Failed to physical device surface support: %s", VkResult(err))
            } else {
                if (supportsPresent.get(0) == VK_TRUE) {
                    queueFamilies[VkQueueFamilies.VK_QUEUE_PRESENTATION] = index
                }
            }
        }
        memFree(supportsPresent)
        queueProps.free()
        return@lazy queueFamilies
    }

    actual fun getQueueFamilyIndices(): Map<VkQueueFamilies, Int> {
        return queueFamilies
    }
}