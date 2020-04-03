package org.aquila3d.core.renderer

import com.toxicbakery.logging.Arbor
import org.aquila3d.core.device.deviceSelectorError
import org.aquila3d.core.surface.Surface
import org.aquila3d.core.surface.Window
import org.aquila3d.core.surface.WindowProvider
import org.aquila3d.core.surface.swapchain.Swapchain
import org.aquila3d.core.surface.swapchain.SwapchainCreator
import org.aquila3d.core.surface.swapchain.SwapchainCreatorFactory
import org.aquila3d.core.vulkan.*
import org.aquila3d.core.vulkan.command.VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT
import org.aquila3d.core.vulkan.command.VkCommandPool
import org.aquila3d.core.vulkan.debug.VkDebugUtilsMessengerCallbackCreateInfo
import org.aquila3d.core.vulkan.device.VkDevice
import org.aquila3d.core.vulkan.device.VkPhysicalDevice
import org.aquila3d.core.vulkan.device.VkQueueFamilies
import kotlin.jvm.JvmField

private typealias DebugConfig = Pair<MutableList<String>, VkDebugUtilsMessengerCallbackCreateInfo?>

class Renderer(
    private val engine: RendererEngine,
    private val windowProvider: WindowProvider,
    private val swapchainCreatorFactory: SwapchainCreatorFactory
) : IRenderer {
    private val window: Window by lazy {
        Arbor.d("Creating window.")
        val window = windowProvider.createWindow()
        engine.onAttachedToWindow(window)
        return@lazy window
    }

    private val requiredInstanceExtensions = mutableListOf<String>()

    private val instance: VkInstance
    private val surface: Surface
    private val physicalDevice: VkPhysicalDevice
    private val logicalDevice: VkDevice
    private val swapchainCreator: SwapchainCreator

    private var swapchain: Swapchain?

    private val graphicsCommandPool: VkCommandPool

    init {
        requiredInstanceExtensions.addAll(engine.requiredInstanceExtensions())
        val (layers, debugUtilsMessengerCreateInfo) = getDebugConfig()

        //TODO: This feels like a bad early publication
        engine.setRenderer(this)

        // Create the Vulkan instance
        Arbor.d("Creating Vulkan instance.")
        Arbor.d("\tRequiring Layers: %s", layers)
        // TODO: Eventually make this configurable for different versions
        val applicationInfo = VkApplicationInfo(makeVulkanVersion(1, 1, 0))
        instance = VkInstance(applicationInfo, requiredInstanceExtensions, layers, debugUtilsMessengerCreateInfo)
        Arbor.d("Creating surface.")
        surface = engine.createSurface(instance)
        Arbor.d("Selecting physical device.")
        val deviceSelector = engine.getDeviceSelector()
        physicalDevice =
            deviceSelector.select(surface, engine.requiredQueueFamilies(), engine.requiredDeviceExtensions())
                ?: throw IllegalStateException("Failed to find an appropriate physical device.")
        Arbor.d("Creating logical device with extensions: %s", engine.requiredDeviceExtensions())
        logicalDevice = engine.createLogicalDevice(physicalDevice, engine.requiredDeviceExtensions())
        Arbor.d("Creating swapchain creator")
        swapchainCreator = swapchainCreatorFactory.creator(logicalDevice, physicalDevice, surface)

        swapchain = swapchainCreator.createSwapchain(window)

        Arbor.d("Creating command pool.")
        val queueFamilyIndex = physicalDevice.getQueueFamilyIndices()[VkQueueFamilies.VK_QUEUE_GRAPHICS]
            ?: throw deviceSelectorError(
                "Unable to create command pool.",
                deviceSelector::class.toString(),
                VkQueueFamilies.VK_QUEUE_GRAPHICS
            )
        graphicsCommandPool =
            VkCommandPool(
                logicalDevice,
                queueFamilyIndex,
                VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT
            )

    }

    override fun onWindowResized(width: Int, height: Int) {
        window.onResized(width, height)
        swapchain = swapchainCreator.recreateSwapchain(window, swapchain!!)
    }

    override fun getCurrentWindow(): Window = window

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
        Arbor.d("Destroying render engine.")
        engine.destroy()
        Arbor.d("Destroying window.")
        window.destroy()
        Arbor.d("Destroying swapchain")
        swapchain?.destroy()
        Arbor.d("Destroying graphics command pool")
        graphicsCommandPool.destroy()
        Arbor.d("Destroying logical device.")
        logicalDevice.destroy()
        Arbor.d("Destroying surface.")
        surface.destroy()
        Arbor.d("Destroying Vulkan instance.")
        instance.destroy()
    }

    private fun getDebugConfig(): DebugConfig = if (engine.isDebugMode()) {
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
}
