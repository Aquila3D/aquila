import com.toxicbakery.logging.Arbor
import com.toxicbakery.logging.Seedling
import org.aquila3d.core.input.InputEvent
import org.aquila3d.core.input.InputEventListener
import org.aquila3d.core.renderer.JvmRendererEngine
import org.aquila3d.core.renderer.Renderer
import org.aquila3d.core.surface.Surface
import org.aquila3d.core.surface.Window
import org.aquila3d.core.surface.WindowProvider
import org.aquila3d.core.surface.swapchain.DefaultSwapchainCreator
import org.aquila3d.core.surface.swapchain.SwapchainCreator
import org.aquila3d.core.surface.swapchain.SwapchainCreatorFactory
import org.aquila3d.core.vulkan.device.VkDevice
import org.aquila3d.core.vulkan.device.VkPhysicalDevice

private val windowProvider = object: WindowProvider {
    override fun createWindow(): Window {
        return Window(800, 600, "Hello, AquilaVK - JVM")
    }
}

private val swapchainCreatorFactory = object: SwapchainCreatorFactory {
    override fun creator(device: VkDevice, physicalDevice: VkPhysicalDevice, surface: Surface): SwapchainCreator {
        return DefaultSwapchainCreator(device, physicalDevice, surface)
    }
}

fun main() {
    Arbor.sow(Seedling())
    val engine = JvmRendererEngine(true)
    engine.registerInputEventListener(object: InputEventListener {
        override fun onEvent(event: InputEvent) {
            Arbor.d("Received input event: $event")
        }
    })
    try {
        val renderer = Renderer(engine, windowProvider, swapchainCreatorFactory)
        renderer.start()
        renderer.destroy()
    } catch (e: Throwable) {
        Arbor.e(e, "Caught exception from renderer.")
    }
    Arbor.d("Terminating...")
}
