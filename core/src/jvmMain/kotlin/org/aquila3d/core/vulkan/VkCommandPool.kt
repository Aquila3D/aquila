package org.aquila3d.core.vulkan

import org.aquila3d.core.memory.useAndGet
import org.lwjgl.system.MemoryUtil.memAllocLong
import org.lwjgl.system.MemoryUtil.memFree
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VkCommandPoolCreateInfo

/**
 * Command pools are opaque objects that command buffer memory is allocated from, and which allow the implementation to
 * amortize the cost of resource creation across multiple command buffers. Command pools are externally synchronized,
 * meaning that a command pool must not be used concurrently in multiple threads. That includes use via recording
 * commands on any command buffers allocated from the pool, as well as operations that allocate, free, and reset command
 * buffers or the pool itself.
 */
actual class VkCommandPool actual constructor(val device: VkDevice, val queueFamilyIndex: Int) {

    val handle: Long

    init {
        handle = memAllocLong(1).useAndGet { buffer ->
            VkCommandPoolCreateInfo.calloc()
                .sType(VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO)
                .queueFamilyIndex(queueFamilyIndex)
                .flags(VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT).use { info ->
                    val err = vkCreateCommandPool(device.handle, info, null, buffer)
                    if (err != VK_SUCCESS) {
                        throw AssertionError("Failed to create command pool: ${VkResult(err)}")
                    }
                    buffer.get(0)
                }
        }
    }

    actual fun destroy() {
        vkDestroyCommandPool(device.handle, handle, null)
    }
}
