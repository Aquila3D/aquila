package org.aquila3d.core.vulkan

import com.toxicbakery.logging.Arbor
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWVulkan
import org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_APPLICATION_INFO

actual class VkApplicationInfo actual constructor(version: Int) {

    // TODO: Cleanup memFree(applicationInfo.info.pApplicationName())
    //        memFree(applicationInfo.info.pEngineName())

    internal val info: org.lwjgl.vulkan.VkApplicationInfo = org.lwjgl.vulkan.VkApplicationInfo.calloc()
        .sType(VK_STRUCTURE_TYPE_APPLICATION_INFO)
        .apiVersion(version)

    actual fun apiVersion(): Int {
        return info.apiVersion()
    }
}

