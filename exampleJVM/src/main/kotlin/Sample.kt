import com.toxicbakery.logging.Arbor
import com.toxicbakery.logging.Seedling
import org.aquila3d.core.renderer.JvmRendererImpl
import org.aquila3d.core.renderer.Renderer

fun main() {
    Arbor.sow(Seedling())
    val renderer = Renderer(JvmRendererImpl())

    Thread.sleep(5000)
    renderer.destroy()
}