package org.aquila3d.core.device

import org.aquila3d.core.surface.Surface
import org.aquila3d.core.vulkan.VkInstance
import org.aquila3d.core.vulkan.VkPhysicalDevice
import org.aquila3d.core.vulkan.VkQueueFamilies

interface DeviceSelector {

    fun select(surface: Surface, requiredQueueFamilies: List<VkQueueFamilies>): VkPhysicalDevice?
}