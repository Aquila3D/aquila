package org.aquila3d.core.vulkan

import org.aquila3d.core.memory.useAndGet
import org.lwjgl.system.MemoryUtil.memAllocPointer
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VkCommandBufferAllocateInfo


actual class VkCommandBuffer actual constructor(
    level: VkCommandBufferLevel,
    pool: VkCommandPool,
    device: VkDevice
) {

    val handle: Long = VkCommandBufferAllocateInfo.calloc()
        .sType(VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO)
        .commandPool(pool.handle)
        .level(level.value)
        .commandBufferCount(1).useAndGet { info ->
            memAllocPointer(1).useAndGet { buffer ->
                val err = vkAllocateCommandBuffers(device.handle, info, buffer)
                if (err != VK_SUCCESS) {
                    throw AssertionError("Failed to allocate command buffer: ${VkResult(err)}")
                }
                buffer[0]
            }
        }
}
