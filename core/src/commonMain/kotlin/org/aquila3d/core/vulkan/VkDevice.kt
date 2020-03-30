package org.aquila3d.core.vulkan

expect class VkDevice {
    fun getCommandQueue(family: VkQueueFamilies): VkQueue?
    fun destroy()
}
