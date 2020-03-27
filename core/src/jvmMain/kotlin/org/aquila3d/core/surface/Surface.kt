package org.aquila3d.core.surface

import org.aquila3d.core.vulkan.VkInstance
import org.lwjgl.vulkan.KHRSurface

actual class Surface(internal val instance: VkInstance, internal val surfaceHandle: Long) {

    actual fun destroy() {
        KHRSurface.vkDestroySurfaceKHR(instance.instance, surfaceHandle, null)
    }
}