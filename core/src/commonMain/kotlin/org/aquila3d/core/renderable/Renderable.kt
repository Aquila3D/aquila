package org.aquila3d.core.renderable

open class Renderable {

    val vertexData: FloatArray

    init {
        vertexData = buildVertices()
    }

    open fun buildVertices(): FloatArray {
        return FloatArray(0)
    }
}
