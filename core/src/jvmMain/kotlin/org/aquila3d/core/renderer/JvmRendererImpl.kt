package org.aquila3d.core.renderer

import org.aquila3d.core.vulkan.VkDebugUtilsMessengerCallback
import org.aquila3d.core.vulkan.VkDebugUtilsMessengerCallbackCreateInfo

open class JvmRendererImpl: Renderer.RendererImpl {

    override fun configureDebug(requiredExtensions: MutableList<String>): VkDebugUtilsMessengerCallbackCreateInfo {
        requiredExtensions.add(Renderer.VK_EXT_DEBUG_UTILS_EXTENSION_NAME)
        return VkDebugUtilsMessengerCallbackCreateInfo(createDebugCallback())
    }

    fun createDebugCallback(): VkDebugUtilsMessengerCallback {
        return VkDebugUtilsMessengerCallback()
    }
}