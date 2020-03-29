package org.aquila3d.core.renderer

import org.aquila3d.core.surface.Window

interface IRenderer {

    fun onWindowResized(width: Int, height: Int)

    fun getCurrentWindow(): Window
}
