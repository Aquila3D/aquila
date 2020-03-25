package org.aquila3d.core.surface

expect class VkWindow(width: Int, height: Int, title: String) {

    fun destroy()

    fun onResized(width: Int, height: Int)

    fun getRequiredExtensions(): List<String>
}