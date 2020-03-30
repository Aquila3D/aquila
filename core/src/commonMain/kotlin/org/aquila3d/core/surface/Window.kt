package org.aquila3d.core.surface

expect class Window(width: Int, height: Int, title: String) {
    fun getWidth(): Int
    fun getHeight(): Int
    fun onResized(width: Int, height: Int)
    fun destroy()
}