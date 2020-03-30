package org.aquila3d.core.vulkan.surface_khr

import org.aquila3d.core.vulkan.VkFormat

actual class VkSurfaceFormatKHR(format: org.lwjgl.vulkan.VkSurfaceFormatKHR) {

    private val colorSpace = VkColorSpaceKHR.from(format.colorSpace())
    private val format = VkFormat.from(format.format())

    actual fun getColorSpace(): VkColorSpaceKHR {
        return colorSpace
    }

    actual fun getFormat(): VkFormat {
        return format
    }

    override fun toString(): String {
        return "VkSurfaceFormatKHR(colorSpace=$colorSpace, format=$format)"
    }
}
