package org.aquila3d.core.scene

import org.aquila3d.core.math.Matrix4
import org.aquila3d.core.math.MutableMatrix4
import org.aquila3d.core.math.Quaternion
import org.aquila3d.core.math.Vector3
import org.aquila3d.core.renderable.Renderable

class Node(val parent: Node? = null, val renderable: Renderable? = null) {

    /**
     * The translation of this [Node] relative to its parent (if it has one)
     */
    private val position: Vector3 = Vector3()

    /**
     * The scale of this [Node] relative to its parent. Defaults to Unity.
     */
    private val scale: Vector3 = Vector3.ONES()

    /**
     * The orientation of this [Node] about its centroid.
     */
    private val orientation: Quaternion = Quaternion()

    /**
     * [Set] of child [Node]s.
     */
    private val children: MutableSet<Node> = mutableSetOf()

    private val scratchModelMatrix: MutableMatrix4 = MutableMatrix4()

    internal fun updateModelMatrix(parentMatrix4: Matrix4? = null) {
        scratchModelMatrix.internalSetFrom(position, orientation, scale)
        parentMatrix4?.also { scratchModelMatrix.leftMultiply(parentMatrix4) }
    }

    internal fun getModelMatrix(): Matrix4 {
        return scratchModelMatrix
    }
}
