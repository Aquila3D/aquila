package org.aquila3d.core.vulkan

import org.aquila3d.core.memory.use
import org.lwjgl.system.MemoryUtil.memAllocPointer
import org.lwjgl.vulkan.VK10.vkDestroyDevice
import org.lwjgl.vulkan.VK10.vkGetDeviceQueue

actual class VkDevice(
    internal val handle: org.lwjgl.vulkan.VkDevice,
    val physicalDevice: VkPhysicalDevice,
    queueFamilies: List<VkQueueFamilies>
) {

    private val commandQueues = mutableMapOf<VkQueueFamilies, VkQueue>()

    init {
        for (family in queueFamilies) {
            memAllocPointer(1).use {
                val familyIndex = physicalDevice.getQueueFamilyIndices()[family]
                vkGetDeviceQueue(
                    handle,
                    familyIndex ?: error("No queue for type $family found"),
                    0,
                    it
                )
                val queue = it[0]
                val commandQueue = org.lwjgl.vulkan.VkQueue(queue, handle)
                commandQueues[family] = VkQueue(commandQueue)
            }
        }
    }

    actual fun getCommandQueue(family: VkQueueFamilies): VkQueue? {
        return commandQueues[family]
    }

    actual fun destroy() {
        vkDestroyDevice(handle, null)
    }
}
