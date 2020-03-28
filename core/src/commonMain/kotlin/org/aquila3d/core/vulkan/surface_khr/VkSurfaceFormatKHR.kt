package org.aquila3d.core.vulkan.surface_khr

import org.aquila3d.core.vulkan.VkFormat

expect class VkSurfaceFormatKHR {
    fun getColorSpace(): VkColorSpaceKHR
    fun getFormat(): VkFormat
}