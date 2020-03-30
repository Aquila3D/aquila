package org.aquila3d.core.vulkan.command

import org.aquila3d.core.vulkan.device.VkDevice

/**
 * Command pools are opaque objects that command buffer memory is allocated from, and which allow the implementation to
 * amortize the cost of resource creation across multiple command buffers. Command pools are externally synchronized,
 * meaning that a command pool must not be used concurrently in multiple threads. That includes use via recording
 * commands on any command buffers allocated from the pool, as well as operations that allocate, free, and reset command
 * buffers or the pool itself.
 */
expect class VkCommandPool(device: VkDevice, queueFamilyIndex: Int, flags: Int) {
    fun destroy()
}
