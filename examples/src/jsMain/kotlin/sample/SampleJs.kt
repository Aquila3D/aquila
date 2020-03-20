package sample

import kotlinx.html.ObjectName
import nvk.VkBuffer
import nvk.VulkanWindow
import org.aquila3d.core.math.Vector3

fun main() {
    println("Hello JavaScript!")
    println(Vector3(1.0, 2.0, 3.0).add(Vector3(1.0, 2.0, 3.0)))

    val win = VulkanWindow()
}
