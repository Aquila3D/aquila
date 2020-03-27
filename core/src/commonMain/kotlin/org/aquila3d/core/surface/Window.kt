package org.aquila3d.core.surface

internal expect fun getRequiredWindowExtensions(): List<String>

expect class Window(width: Int, height: Int, title: String) {

    fun destroy()

    fun onResized(width: Int, height: Int)
}