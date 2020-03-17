package org.aquila3d.core.scene

import org.aquila3d.core.math.Quaternion
import org.aquila3d.core.math.Vector3

class Node {

    /**
     * The translation of this [Node] relative to its parent (if it has one)
     */
    private val position: Vector3 = Vector3()

    /**
     * The orientation of this [Node] about its centroid.
     */
    private val orientation: Quaternion = Quaternion()

    /**
     * [Set] of child [Node]s.
     */
    private val children: MutableSet<Node> = mutableSetOf()

    /**
     * Parent [Node]
     */
    private var parent: Node? = null
}