package org.aquila3d.core.renderer

import com.toxicbakery.logging.Arbor
import org.aquila3d.core.surface.Surface
import org.aquila3d.core.surface.Window
import org.aquila3d.core.vulkan.*
import kotlin.jvm.JvmField

private typealias DebugConfig = Pair<MutableList<String>, VkDebugUtilsMessengerCallbackCreateInfo?>

class Renderer(private val engine: RendererEngine, private val isDebug: Boolean = true) {

    companion object {
        const val VK_EXT_DEBUG_UTILS_EXTENSION_NAME = "VK_EXT_debug_utils"

        @JvmField
        val DEBUG_LAYERS_STANDARD = listOf("VK_LAYER_KHRONOS_validation")

        @JvmField
        @Deprecated(
            message = "This layer was deprecated by Khronos",
            replaceWith = ReplaceWith(
                "DEBUG_LAYERS_STANDARD",
                "import org.aquila3d.core.renderer.Renderer.DEBUG_LAYERS_STANDARD"
            )
        )
        val DEBUG_LAYERS_LUNARG_STANDARD = listOf("VK_LAYER_LUNARG_standard_validation")
    }

    private val window: Window by lazy {
        Arbor.d("Creating window.")
        val window = Window(800, 600, "Hello, Vulkan JVM")
        engine.onAttachedToWindow(window)
        return@lazy window
    }

    private val requiredInstanceExtensions = mutableListOf<String>()

    private val instance: VkInstance
    private val surface: Surface
    private val physicalDevice: VkPhysicalDevice
    private val logicalDevice: VkDevice

    init {
        requiredInstanceExtensions.addAll(engine.requiredInstanceExtensions())
        val (layers, debugUtilsMessengerCreateInfo) = getDebugConfig()

        // Create the Vulkan instance
        Arbor.d("Creating Vulkan instance.")
        Arbor.d("\tRequiring Layers: %s", layers)
        // TODO: Eventually make this configurable for different versions
        val applicationInfo = VkApplicationInfo(makeVulkanVersion(1, 1, 0))
        instance = VkInstance(applicationInfo, requiredInstanceExtensions, layers, debugUtilsMessengerCreateInfo)

        surface = engine.createSurface(instance, window)

        Arbor.d("Selecting physical device.")
        physicalDevice = engine.getDeviceSelector().select(surface, engine.requiredQueueFamilies())
            ?: throw IllegalStateException("Failed to find an appropriate physical device.")
        Arbor.d("Creating logical device.")
        logicalDevice = engine.createLogicalDevice(physicalDevice, listOf())
        Arbor.d("Graphics command queue: %s", logicalDevice.getCommandQueue(VkQueueFamilies.VK_QUEUE_GRAPHICS))
    }

    private fun getDebugConfig(): DebugConfig = if (isDebug) {
        @Suppress("DEPRECATION")
        when {
            checkLayersAvailable(DEBUG_LAYERS_STANDARD) ->
                DEBUG_LAYERS_STANDARD.toMutableList() to engine.configureDebug(requiredInstanceExtensions)
            checkLayersAvailable(DEBUG_LAYERS_LUNARG_STANDARD) -> {
                Arbor.w("The available Vulkan SDK appears to be outdated. Using deprecated validation layers.")
                DEBUG_LAYERS_LUNARG_STANDARD.toMutableList() to engine.configureDebug(requiredInstanceExtensions)
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

    fun start() {
        engine.startRenderLoop()
    }

    fun stop() {
        engine.stopRenderLoop()
    }

    fun resume() {
        engine.resumeRenderLoop()
    }

    fun pause() {
        engine.pauseRenderLoop()
    }

    fun destroy() {
        Arbor.d("Stopping render loop.")
        engine.stopRenderLoop()
        Arbor.d("Destroying window.")
        window.destroy()
        Arbor.d("Destroying logical device.")
        logicalDevice.destroy()
        Arbor.d("Destroying surface.")
        surface.destroy()
        Arbor.d("Destroying Vulkan instance.")
        instance.destroy()
    }

}