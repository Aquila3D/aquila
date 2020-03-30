package org.aquila3d.core.vulkan.command

import org.aquila3d.core.vulkan.device.VkDevice

enum class VkCommandBufferLevel(val value: Int) {
    VK_COMMAND_BUFFER_LEVEL_PRIMARY(0),
    VK_COMMAND_BUFFER_LEVEL_SECONDARY(1)
}

expect class VkCommandBuffer(level: VkCommandBufferLevel, pool: VkCommandPool, device: VkDevice) {

}
