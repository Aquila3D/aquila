import com.toxicbakery.logging.Arbor
import com.toxicbakery.logging.Seedling
import org.aquila3d.core.renderer.JvmRendererEngine
import org.aquila3d.core.renderer.Renderer

fun main() {
    Arbor.sow(Seedling())
    val renderer = Renderer(JvmRendererEngine())

    Thread.sleep(5000)
    renderer.destroy()
}