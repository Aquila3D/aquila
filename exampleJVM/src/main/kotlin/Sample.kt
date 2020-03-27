import com.toxicbakery.logging.Arbor
import com.toxicbakery.logging.Seedling
import org.aquila3d.core.input.InputEvent
import org.aquila3d.core.input.InputEventListener
import org.aquila3d.core.renderer.JvmRendererEngine
import org.aquila3d.core.renderer.Renderer

fun main() {
    Arbor.sow(Seedling())
    val engine = JvmRendererEngine()
    engine.registerInputEventListener(object: InputEventListener {
        override fun onEvent(event: InputEvent) {
            Arbor.d("Received input event: $event")
        }
    })
    try {
        val renderer = Renderer(engine)
        renderer.start()
        renderer.destroy()
    } catch (e: Throwable) {
        Arbor.e(e, "Caught exception from renderer.")
    }
    Arbor.d("Terminating...")
}