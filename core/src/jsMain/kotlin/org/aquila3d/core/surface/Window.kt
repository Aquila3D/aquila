package org.aquila3d.core.surface

import nvk.VulkanWindow
import kotlin.js.json

actual class Window actual constructor(
    width: Int,
    height: Int,
    title: String
) {

    val window: VulkanWindow = VulkanWindow(
        json(
            "width" to width,
            "height" to height,
            "title" to title
        )
    )

    actual fun getWidth(): Int {
        TODO("Not yet implemented")
    }

    actual fun getHeight(): Int {
        TODO("Not yet implemented")
    }

    actual fun destroy() {
    }

    actual fun onResized(width: Int, height: Int) {
    }
}
