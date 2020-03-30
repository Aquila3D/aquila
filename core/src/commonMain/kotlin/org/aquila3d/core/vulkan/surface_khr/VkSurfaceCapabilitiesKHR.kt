package org.aquila3d.core.vulkan.surface_khr

import org.aquila3d.core.vulkan.VkExtent2D

expect class VkSurfaceCapabilitiesKHR {
    fun minImageCount(): Int

    fun maxImageCount(): Int

    fun currentExtent(): VkExtent2D

    fun minImageExtent(): VkExtent2D

    fun maxImageExtent(): VkExtent2D

    fun maxImageArrayLayers(): Int

    fun supportedTransforms(): Int

    fun currentTransform(): Int

    fun supportedCompositeAlpha(): Int

    fun supportedUsageFlags(): Int
}