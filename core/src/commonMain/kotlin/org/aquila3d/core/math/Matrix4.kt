package org.aquila3d.core.math

/**
 * 4x4 column major matrix.
 *
 * | 0 4  8 12 |
 * | 1 5  9 13 |
 * | 2 6 10 14 |
 * | 3 7 11 15 |
 */
open class Matrix4 {

    /**
     * Storing data this way allows us to index easier. values[0][3] represents index 3 shown above while values[3][0]
     * represents index 12.
     */
    protected val values = Array(4) { Column(DoubleArray(4) { 0.0 }) }

    /**
     * Creates a new [Matrix4] initialized to the Identity matrix.
     */
    constructor() {
        values[0][0] = 1.0
        values[1][1] = 1.0
        values[2][2] = 1.0
        values[3][3] = 1.0
    }

    constructor(other: Matrix4) {
        other.values[0].array.copyInto(values[0].array, 0, 0, 4)
        other.values[1].array.copyInto(values[1].array, 0, 0, 4)
        other.values[2].array.copyInto(values[2].array, 0, 0, 4)
        other.values[3].array.copyInto(values[3].array, 0, 0, 4)
    }

    /**
     * Creates a new [Matrix4] representing the translation of [translation], rotation of [rotation] and no scaling.
     */
    constructor(translation: Vector3, rotation: Quaternion): this(translation, rotation, noScale)

    /**
     * Creates a new [Matrix4] representing the translation of [translation], rotation of [rotation]
     * and scaling of [scale].
     */
    constructor(translation: Vector3, rotation: Quaternion, scale: Vector3): this() {
        internalSetFrom(translation, rotation, scale)
    }

    open fun toMatrix4(): Matrix4 = Matrix4(this)

    operator fun times(vector3: Vector3): Vector3 {
        val x = vector3.x * values[0][0] + vector3.y * values[1][0] + vector3.z * values[2][0] + values[3][0]
        val y = vector3.x * values[0][1] + vector3.y * values[1][1] + vector3.z * values[2][1] + values[3][1]
        val z = vector3.x * values[0][2] + vector3.y * values[1][2] + vector3.z * values[2][2] + values[3][2]
        return Vector3(x, y, z)
    }

    operator fun get(index: Int): Column = values[index]

    internal fun internalSetFrom(translation: Vector3, rotation: Quaternion, scale: Vector3): Matrix4 {
        val qxx = rotation.x * rotation.x
        val qyy = rotation.y * rotation.y
        val qzz = rotation.z * rotation.z
        val qxz = rotation.x * rotation.z
        val qxy = rotation.x * rotation.y
        val qyz = rotation.y * rotation.z
        val qwx = rotation.w * rotation.x
        val qwy = rotation.w * rotation.y
        val qwz = rotation.w * rotation.z

        values[0][0] = scale.x * (1.0 - 2.0 * (qyy + qzz))
        values[0][1] = scale.x * (2.0 * (qxy + qwz))
        values[0][2] = scale.x * (2.0 * (qxz - qwy))

        values[1][0] = scale.y * (2.0 * (qxy - qwz))
        values[1][1] = scale.y * (1.0 - 2.0 * (qxx + qzz))
        values[1][2] = scale.y * (2.0 * (qyz + qwx))

        values[2][0] = scale.z * (2.0 * (qxz + qwy))
        values[2][1] = scale.z * (2.0 * (qyz - qwx))
        values[2][2] = scale.z * (1.0 - 2.0 * (qxx +  qyy))

        values[3][0] = translation.x
        values[3][1] = translation.y
        values[3][2] = translation.z
        values[3][3] = 1.0
        return this
    }

    companion object {

        /**
         * An internal [Vector3] instance representing no scaling in all dimensions.
         */
        private val noScale = Vector3(1.0, 1.0, 1.0)
    }

}

inline class Column(val array: DoubleArray) {
    operator fun get(index: Int): Double = array[index]

    internal operator fun set(index: Int, value: Double) {
        array[index] = value
    }
}