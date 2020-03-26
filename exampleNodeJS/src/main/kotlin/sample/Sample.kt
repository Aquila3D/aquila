package sample

import org.aquila3d.core.math.Vector3
import org.aquila3d.core.surface.VkWindow

fun main() {
    println("Hello JavaScript!")
    println(Vector3(1.0, 2.0, 3.0).plus(Vector3(1.0, 2.0, 3.0)))

    val win = VkWindow(640, 480, "Hello Javascript")
    println("Window: $win")
}
