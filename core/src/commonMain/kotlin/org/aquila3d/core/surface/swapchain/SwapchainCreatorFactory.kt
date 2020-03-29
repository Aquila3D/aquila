package org.aquila3d.core.surface.swapchain

import org.aquila3d.core.surface.Surface
import org.aquila3d.core.vulkan.VkDevice
import org.aquila3d.core.vulkan.VkPhysicalDevice

interface SwapchainCreatorFactory {

    fun creator(device: VkDevice, physicalDevice: VkPhysicalDevice, surface: Surface): SwapchainCreator
}