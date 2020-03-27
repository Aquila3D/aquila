package org.aquila3d.core.device

import org.aquila3d.core.surface.Surface
import org.aquila3d.core.vulkan.VkPhysicalDevice
import org.aquila3d.core.vulkan.VkQueueFamilies

actual class FirstDeviceSelector : DeviceSelector {

    override fun select(
        surface: Surface,
        requiredQueueFamilies: List<VkQueueFamilies>,
        requiredDeviceExtensions: List<String>
    ): VkPhysicalDevice? {
        TODO("Not yet implemented")
    }
}