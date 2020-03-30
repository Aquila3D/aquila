package org.aquila3d.core.vulkan

import org.aquila3d.core.vulkan.device.VkDevice
import org.lwjgl.vulkan.VK10.vkDestroyImageView

actual class VkImageView(val handle: Long) {

    actual fun destroy(device: VkDevice) {
        vkDestroyImageView(device.handle, handle, null)
    }
}
