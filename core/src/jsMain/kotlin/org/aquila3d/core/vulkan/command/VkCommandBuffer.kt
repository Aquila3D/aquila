package org.aquila3d.core.vulkan.command

import org.aquila3d.core.vulkan.device.VkDevice

actual class VkCommandBuffer actual constructor(
    level: VkCommandBufferLevel,
    pool: VkCommandPool,
    device: VkDevice
) {
}
