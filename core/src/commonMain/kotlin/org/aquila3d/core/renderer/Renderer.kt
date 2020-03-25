package org.aquila3d.core.renderer

import com.toxicbakery.logging.Arbor
import org.aquila3d.core.surface.VkWindow
import org.aquila3d.core.vulkan.*

class Renderer(val impl: RendererImpl, val isDebug: Boolean = true) {

    companion object {
        val VK_EXT_DEBUG_UTILS_EXTENSION_NAME = "VK_EXT_debug_utils"

        val DEBUG_LAYERS_STANDARD = listOf("VK_LAYER_KHRONOS_validation")

        @Deprecated(message = "This layer was deprecated by Khronos",
            replaceWith = ReplaceWith("DEBUG_LAYERS_STANDARD", "import Renderer.DEBUG_LAYERS_STANDARD")
        )
        val DEBUG_LAYERS_LUNARG_STANDARD = listOf("VK_LAYER_LUNARG_standard_validation")
    }

    val window: VkWindow
    val instance: VkInstance

    init {
        Arbor.d("Creating window.")
        window = createWindow()

        /* Look for instance extensions */
        val requiredExtensions = window.getRequiredExtensions()

        val layers = mutableListOf<String>()
        var debugUtilsMessengerCreateInfo: VkDebugUtilsMessengerCallbackCreateInfo? = null
        if (isDebug) {
            when {
                checkLayersAvailable(DEBUG_LAYERS_STANDARD) -> {
                    layers.addAll(DEBUG_LAYERS_STANDARD)
                    debugUtilsMessengerCreateInfo = impl.configureDebug(requiredExtensions)
                }
                checkLayersAvailable(DEBUG_LAYERS_LUNARG_STANDARD) -> {
                    Arbor.w("The available Vulkan SDK appears to be outdated. Using deprecated validation layers.")
                    layers.addAll(DEBUG_LAYERS_LUNARG_STANDARD)
                    debugUtilsMessengerCreateInfo = impl.configureDebug(requiredExtensions)
                }
                else -> {
                    Arbor.e("Unable to locate necessary debug layers. The local machine likely does not have a Vulkan SDK installed.")
                }
            }
        }

        // Create the Vulkan instance
        Arbor.d("Creating Vulkan instance.")
        Arbor.d("\tRequiring Extensions: %s", requiredExtensions)
        Arbor.d("\tRequiring Layers: %s", layers)
        val applicationInfo = VkApplicationInfo(makeVulkanVersion(1,1, 0))
        instance = VkInstance(applicationInfo, requiredExtensions, layers, debugUtilsMessengerCreateInfo)
    }

    fun destroy() {
        Arbor.d("Destroying window.")
        window.destroy()
        Arbor.d("Destroying Vulkan instance.")
        instance.destroy()
    }

    private fun createWindow(): VkWindow {
        return VkWindow(800, 600, "Hello, Vulkan JVM")
    }

    interface RendererImpl {

        fun configureDebug(requiredExtensions: MutableList<String>): VkDebugUtilsMessengerCallbackCreateInfo
    }
}