package org.aquila3d.core.surface

import nvk.VulkanWindow
import org.aquila3d.core.createInstance

actual class VkWindow {

    val window: VulkanWindow

    actual constructor(width: Int, height: Int, title: String) {
        window = VulkanWindow::class.js.createInstance()
        window.asDynamic()["width"] = width
        window.asDynamic()["height"] = height
        window.asDynamic()["title"] = title
    }
}