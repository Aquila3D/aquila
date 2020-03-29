package org.aquila3d.core.vulkan

import org.lwjgl.system.MemoryUtil.memAllocPointer
import org.lwjgl.system.MemoryUtil.memFree
import org.lwjgl.vulkan.VK10.vkDestroyDevice
import org.lwjgl.vulkan.VK10.vkGetDeviceQueue

actual class VkDevice(
    internal val handle: org.lwjgl.vulkan.VkDevice,
    physicalDevice: VkPhysicalDevice,
    queueFamilies: List<VkQueueFamilies>
) {

    private val commandQueues = mutableMapOf<VkQueueFamilies, VkQueue>()

    init {
        for (family in queueFamilies) {
            val pQueue = memAllocPointer(1)
            val familyIndex = physicalDevice.getQueueFamilyIndices()[family]
            vkGetDeviceQueue(
                handle,
                familyIndex ?: error("No queue for type $family found"),
                0,
                pQueue
            )
            val queue = pQueue[0]
            memFree(pQueue)
            val commandQueue = org.lwjgl.vulkan.VkQueue(queue, handle)
            commandQueues[family] = VkQueue(commandQueue)
        }
    }

    actual fun getCommandQueue(family: VkQueueFamilies): VkQueue? {
        return commandQueues[family]
    }

    actual fun destroy() {
        vkDestroyDevice(handle, null)
    }
}
