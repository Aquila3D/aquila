package org.aquila3d.core.surface.swapchain

import org.aquila3d.core.vulkan.surface_khr.VkPresentModeKHR
import org.aquila3d.core.vulkan.surface_khr.VkSurfaceCapabilitiesKHR
import org.aquila3d.core.vulkan.surface_khr.VkSurfaceFormatKHR

data class SwapchainFeatures(
    val capabilities: VkSurfaceCapabilitiesKHR,
    val formats: List<VkSurfaceFormatKHR>,
    val presentMode: List<VkPresentModeKHR>
) {
}