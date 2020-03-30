package org.aquila3d.core.vulkan.device

import org.aquila3d.core.vulkan.command.VkQueue
import org.aquila3d.core.vulkan.device.VkQueueFamilies

actual class VkDevice {

    actual fun getCommandQueue(family: VkQueueFamilies): VkQueue? {
        TODO("Not yet implemented")
    }

    actual fun destroy() {
    }
}
