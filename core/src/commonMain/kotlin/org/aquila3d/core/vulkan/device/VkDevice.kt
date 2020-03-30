package org.aquila3d.core.vulkan.device

import org.aquila3d.core.vulkan.command.VkQueue
import org.aquila3d.core.vulkan.device.VkQueueFamilies

expect class VkDevice {
    fun getCommandQueue(family: VkQueueFamilies): VkQueue?
    fun destroy()
}
