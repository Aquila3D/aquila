package org.aquila3d.core.vulkan.surface_khr

expect class VkSurfaceFormatKHR {
    fun getColorSpace(): Int
    fun getFormat(): Int
}