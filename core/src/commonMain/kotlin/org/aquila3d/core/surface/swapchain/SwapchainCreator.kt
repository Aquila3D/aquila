package org.aquila3d.core.surface.swapchain

import org.aquila3d.core.surface.Surface
import org.aquila3d.core.surface.Window
import org.aquila3d.core.vulkan.device.VkDevice
import org.aquila3d.core.vulkan.device.VkPhysicalDevice

abstract class SwapchainCreator(val device: VkDevice, val physicalDevice: VkPhysicalDevice, val surface: Surface) {

    abstract fun createSwapchain(window: Window): Swapchain

    abstract fun recreateSwapchain(window: Window, oldSwapchain: Swapchain): Swapchain?
}
