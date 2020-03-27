package org.aquila3d.core.vulkan.surface_khr

actual class VkSurfaceFormatKHR(format: org.lwjgl.vulkan.VkSurfaceFormatKHR) {

    private val colorSpace = format.colorSpace()
    private val format = format.format()

    actual fun getColorSpace(): Int {
        return colorSpace
    }

    actual fun getFormat(): Int {
        return format
    }
}