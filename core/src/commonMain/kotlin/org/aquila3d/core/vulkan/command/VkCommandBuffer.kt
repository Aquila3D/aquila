package org.aquila3d.core.vulkan.command

import org.aquila3d.core.vulkan.device.VkDevice

/**
 * Command buffers are objects used to record commands which can be subsequently submitted to a device queue for
 * execution. There are two levels of command buffers - primary command buffers, which can execute secondary command
 * buffers, and which are submitted to queues, and secondary command buffers, which can be executed by primary command
 * buffers, and which are not directly submitted to queues.
 */
expect class VkCommandBuffer(level: VkCommandBufferLevel, pool: VkCommandPool, device: VkDevice) {

}
