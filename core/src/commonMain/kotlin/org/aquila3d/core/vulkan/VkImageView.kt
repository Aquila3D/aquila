package org.aquila3d.core.vulkan

import org.aquila3d.core.vulkan.device.VkDevice

expect class VkImageView {
    fun destroy(device: VkDevice)
}
