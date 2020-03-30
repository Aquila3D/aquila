package org.aquila3d.core.vulkan

import org.aquila3d.core.memory.use
import org.lwjgl.system.MemoryUtil.memAllocPointer
import org.lwjgl.system.MemoryUtil.memFree
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VkCommandBufferAllocateInfo


actual class VkCommandBuffer actual constructor(
    level: VkCommandBufferLevel,
    pool: VkCommandPool,
    device: VkDevice
) {

    val handle = VkCommandBufferAllocateInfo.calloc()
        .sType(VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO)
        .commandPool(pool.handle)
        .level(level.value)
        .commandBufferCount(1).use { info ->
            memAllocPointer(1).use { buffer ->
                val err = vkAllocateCommandBuffers(device.handle, info, buffer)
                if (err != VK_SUCCESS) {
                    throw AssertionError("Failed to allocate command buffer: ${VkResult(err)}")
                }
                buffer[0]
            }
        }
}
