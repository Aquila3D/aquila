package org.aquila3d.core.device

import org.aquila3d.core.vulkan.VkInstance
import org.aquila3d.core.vulkan.VkPhysicalDevice
import org.aquila3d.core.vulkan.VkQueueFamilies

actual class FirstDeviceSelector : DeviceSelector {

    override fun select(instance: VkInstance, requiredQueueFamilies: List<VkQueueFamilies>): VkPhysicalDevice {
        TODO("Not yet implemented")
    }
}