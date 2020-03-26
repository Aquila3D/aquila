package org.aquila3d.core.renderer

import com.toxicbakery.logging.Arbor
import org.aquila3d.core.surface.VkWindow
import org.aquila3d.core.vulkan.*
import kotlin.jvm.JvmField

private typealias DebugConfig = Pair<MutableList<String>, VkDebugUtilsMessengerCallbackCreateInfo?>

class Renderer(private val engine: RendererEngine, private val isDebug: Boolean = true) {

    companion object {
        const val VK_EXT_DEBUG_UTILS_EXTENSION_NAME = "VK_EXT_debug_utils"

        @JvmField
        val DEBUG_LAYERS_STANDARD = listOf("VK_LAYER_KHRONOS_validation")

        @JvmField
        @Deprecated(message = "This layer was deprecated by Khronos",
            replaceWith = ReplaceWith("DEBUG_LAYERS_STANDARD", "import Renderer.DEBUG_LAYERS_STANDARD")
        )
        val DEBUG_LAYERS_LUNARG_STANDARD = listOf("VK_LAYER_LUNARG_standard_validation")
    }

    /**
     * Get instance extensions
     */
    private val requiredExtensions = mutableListOf<String>()

    private val window: VkWindow by lazy {
        Arbor.d("Creating window.")
        VkWindow(800, 600, "Hello, Vulkan JVM")
    }

    private val instance: VkInstance

    init {
        requiredExtensions.addAll(window.getRequiredExtensions())
        val (layers, debugUtilsMessengerCreateInfo) = getDebugConfig()

        // Create the Vulkan instance
        Arbor.d("Creating Vulkan instance.")
        Arbor.d("\tRequiring Extensions: %s", requiredExtensions)
        Arbor.d("\tRequiring Layers: %s", layers)
        val applicationInfo = VkApplicationInfo(makeVulkanVersion(1,1, 0))
        instance = VkInstance(applicationInfo, requiredExtensions, layers, debugUtilsMessengerCreateInfo)
    }

    private fun getDebugConfig(): DebugConfig = if (isDebug) {
        @Suppress("DEPRECATION")
        when {
            checkLayersAvailable(DEBUG_LAYERS_STANDARD) ->
                DEBUG_LAYERS_STANDARD.toMutableList() to engine.configureDebug(requiredExtensions)
            checkLayersAvailable(DEBUG_LAYERS_LUNARG_STANDARD) -> {
                Arbor.w("The available Vulkan SDK appears to be outdated. Using deprecated validation layers.")
                DEBUG_LAYERS_LUNARG_STANDARD.toMutableList() to engine.configureDebug(requiredExtensions)
            }
            else -> {
                Arbor.e(
                    "Unable to locate necessary debug layers. " +
                            "The local machine likely does not have a Vulkan SDK installed."
                )
                mutableListOf<String>() to null
            }
        }
    } else mutableListOf<String>() to null

    fun destroy() {
        Arbor.d("Destroying window.")
        window.destroy()
        Arbor.d("Destroying Vulkan instance.")
        instance.destroy()
    }

    interface RendererEngine {

        fun configureDebug(requiredExtensions: MutableList<String>): VkDebugUtilsMessengerCallbackCreateInfo
    }
}