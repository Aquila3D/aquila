package org.aquila3d.core.renderer

import com.toxicbakery.logging.Arbor
import kotlinx.coroutines.*
import org.aquila3d.core.device.DeviceSelector
import org.aquila3d.core.device.FirstDeviceSelector
import org.aquila3d.core.device.deviceSelectorError
import org.aquila3d.core.input.Event
import org.aquila3d.core.input.EventSource
import org.aquila3d.core.input.InputEvent
import org.aquila3d.core.input.InputEventListener
import org.aquila3d.core.surface.Surface
import org.aquila3d.core.surface.Window
import org.aquila3d.core.vulkan.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWKeyCallback
import org.lwjgl.glfw.GLFWVulkan
import org.lwjgl.glfw.GLFWVulkan.glfwCreateWindowSurface
import org.lwjgl.glfw.GLFWWindowSizeCallback
import org.lwjgl.system.MemoryUtil.*
import org.lwjgl.vulkan.KHRSwapchain.VK_KHR_SWAPCHAIN_EXTENSION_NAME
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VkDeviceCreateInfo
import org.lwjgl.vulkan.VkDeviceQueueCreateInfo
import java.nio.ByteBuffer
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors


open class JvmRendererEngine(private val isDebug: Boolean) : RendererEngine {

    private val eventListeners: MutableSet<InputEventListener> = ConcurrentHashMap.newKeySet()

    private val renderThread = Executors.newSingleThreadExecutor { Thread(it, "AquilaVK-Render-Thread") }

    private val deviceExtensions = listOf(VK_KHR_SWAPCHAIN_EXTENSION_NAME)

    private val requiredQueueFamilies = listOf(VkQueueFamilies.VK_QUEUE_GRAPHICS, VkQueueFamilies.VK_QUEUE_PRESENTATION)

    // Can't initialize this here because it must be after GLFW is initialized
    private val instanceExtensions: MutableList<String>

    @Volatile
    private var shouldRender = false

    private lateinit var renderer: IRenderer

    private val glfwKeyCallback: GLFWKeyCallback by lazy {
        object : GLFWKeyCallback() {
            override fun invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
                val eventAction = when (action) {
                    GLFW_RELEASE -> Event.UP
                    GLFW_KEY_DOWN -> Event.DOWN
                    GLFW_REPEAT -> Event.REPEAT
                    else -> Event.OTHER
                }
                Arbor.d("Key press detected from GLFW: %d", key)
                eventListeners.forEach { it.onEvent(InputEvent(EventSource.KEY, eventAction, key)) }
            }
        }
    }

    // Handle canvas resize
    private val glfwWindowSizeCallback: GLFWWindowSizeCallback by lazy {
        return@lazy object : GLFWWindowSizeCallback() {
            override fun invoke(window: Long, width: Int, height: Int) {
                Arbor.d("Window Resize: [WxH] = [%dx%d]", width, height)
                if (width <= 0 || height <= 0) {
                    return
                }
                renderer.onWindowResized(width, height)
            }
        }
    }

    init {
        if (!glfwInit()) {
            throw RuntimeException("Failed to initialize GLFW")
        }
        if (!GLFWVulkan.glfwVulkanSupported()) {
            throw AssertionError("GLFW failed to find the Vulkan loader")
        }
        Arbor.d("GLFW Initialized.")
        instanceExtensions = getRequiredInstanceExtensions()
    }

    override fun setRenderer(renderer: IRenderer) {
        this.renderer = renderer
    }

    override fun isDebugMode(): Boolean {
        return isDebug
    }

    override fun requiredInstanceExtensions(): List<String> {
        return instanceExtensions
    }

    override fun requiredDeviceExtensions(): List<String> {
        return deviceExtensions
    }

    override fun requiredQueueFamilies(): List<VkQueueFamilies> {
        return requiredQueueFamilies
    }

    override fun configureDebug(requiredExtensions: MutableList<String>): VkDebugUtilsMessengerCallbackCreateInfo {
        requiredExtensions.add(Renderer.VK_EXT_DEBUG_UTILS_EXTENSION_NAME)
        return VkDebugUtilsMessengerCallbackCreateInfo(createDebugCallback())
    }

    override fun getDeviceSelector(): DeviceSelector {
        return FirstDeviceSelector()
    }

    override fun createSurface(instance: VkInstance): Surface {
        val pSurface = memAllocLong(1)
        val err = glfwCreateWindowSurface(instance.instance, renderer.getCurrentWindow().window, null, pSurface)
        val surface = pSurface[0]
        memFree(pSurface) // Cleanup the native memory
        if (err != VK_SUCCESS) {
            throw AssertionError("Failed to create surface: ${VkResult(err)}")
        }
        return Surface(instance, surface)
    }

    override fun createLogicalDevice(physicalDevice: VkPhysicalDevice, requiredExtensions: List<String>): VkDevice {
        // Prepare the command queue creation information
        val pQueuePriorities = memAllocFloat(2).put(1.0f)
        pQueuePriorities.flip()
        val queueCreateInfo = VkDeviceQueueCreateInfo.calloc(2)

        physicalDevice.getQueueFamilyIndices()[VkQueueFamilies.VK_QUEUE_GRAPHICS]?.let {
            queueCreateInfo[0].sType(VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO)
                .queueFamilyIndex(it)
                .pQueuePriorities(pQueuePriorities)
        } ?: throw deviceSelectorError(
            "Unable to create logical device.",
            getDeviceSelector()::class.qualifiedName ?: "Unknown",
            VkQueueFamilies.VK_QUEUE_GRAPHICS
        )
        physicalDevice.getQueueFamilyIndices()[VkQueueFamilies.VK_QUEUE_PRESENTATION]?.let {
            queueCreateInfo[1].sType(VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO)
                .queueFamilyIndex(it)
                .pQueuePriorities(pQueuePriorities)
        } ?: throw deviceSelectorError(
            "Unable to create logical device.",
            getDeviceSelector()::class.qualifiedName ?: "Unknown",
            VkQueueFamilies.VK_QUEUE_PRESENTATION
        )

        // Prepare te extension loading information
        val extensions = memAllocPointer(requiredExtensions.size)
        val extensionBuffers = mutableListOf<ByteBuffer>()
        for (extension in requiredExtensions) {
            val extensionBuffer = memUTF8(extension)
            extensionBuffers.add(extensionBuffer)
            extensions.put(extensionBuffer)
        }
        extensions.flip()

        val deviceCreateInfo = VkDeviceCreateInfo.calloc()
            .sType(VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO)
            .pQueueCreateInfos(queueCreateInfo)
            .ppEnabledExtensionNames(extensions)
            .ppEnabledLayerNames(null)

        val pDevice = memAllocPointer(1)
        val err = vkCreateDevice(physicalDevice.handle, deviceCreateInfo, null, pDevice)
        val device = pDevice[0]
        memFree(pDevice)
        if (err != VK_SUCCESS) {
            throw AssertionError("Failed to create device: " + VkResult(err))
        }
        val logicalDevice = org.lwjgl.vulkan.VkDevice(device, physicalDevice.handle, deviceCreateInfo)
        val retval = VkDevice(logicalDevice, physicalDevice, requiredQueueFamilies())

        // Cleanup the native memory
        deviceCreateInfo.free()
        for (buffer in extensionBuffers) {
            memFree(buffer)
        }
        memFree(extensions)
        memFree(pQueuePriorities)
        return retval
    }

    override fun onAttachedToWindow(window: Window) {
        glfwSetKeyCallback(window.window, glfwKeyCallback)
        glfwSetWindowSizeCallback(window.window, glfwWindowSizeCallback)
    }

    override fun registerInputEventListener(listener: InputEventListener) {
        eventListeners.add(listener)
    }

    override fun unregisterInputEventListener(listener: InputEventListener) {
        eventListeners.remove(listener)
    }

    override fun startRenderLoop() {
        runBlocking {
            val deferred = async { render() }
            while (!glfwWindowShouldClose(renderer.getCurrentWindow().window)) {
                // Handle window messages. Resize events happen exactly here.
                // So it is safe to use the new swapchain images and framebuffers afterwards.
                glfwPollEvents()
                delay(16) // 60 Hz, this is temporary for now
            }
            stopRenderLoop()
            deferred.await()
        }
    }

    override fun stopRenderLoop() {
        shouldRender = false
        renderThread.shutdownNow()
    }

    override fun resumeRenderLoop() {
        shouldRender = true
    }

    override fun pauseRenderLoop() {
        shouldRender = false
    }

    override fun destroy() {
        stopRenderLoop()
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun createDebugCallback(): VkDebugUtilsMessengerCallback {
        return VkDebugUtilsMessengerCallback()
    }

    private suspend fun render() = withContext(renderThread.asCoroutineDispatcher()) {
        shouldRender = true
        while (shouldRender) {
            delay(16) // 60 Hz, this is temporary for now
            // TODO: Real frame rate determination, including render when dirty
        }
    }
}
