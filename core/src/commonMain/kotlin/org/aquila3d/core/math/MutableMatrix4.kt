package org.aquila3d.core.math

class MutableMatrix4: Matrix4 {

    constructor(): super()

    constructor(translation: Vector3, rotation: Quaternion): super(translation, rotation)

    constructor(translation: Vector3, rotation: Quaternion, scale: Vector3): super(translation, rotation, scale)

    operator fun set(index: Int, value: Column) {
        values[index] = value
    }

    fun setFrom(translation: Vector3, rotation: Quaternion, scale: Vector3) {
        internalSetFrom(translation, rotation, scale)
    }

    fun leftMultiply(other: Matrix4): MutableMatrix4 {
        val tmp = Array(4) { Column(DoubleArray(4) { 0.0 }) }
        tmp[0][0] = other[0][0] * this[0][0] + other[1][0] * this[0][1] + other[2][0] * this[0][2] + other[3][0] * this[0][3]
        tmp[1][0] = other[0][0] * this[1][0] + other[1][0] * this[1][1] + other[2][0] * this[1][2] + other[3][0] * this[1][3]
        tmp[2][0] = other[0][0] * this[2][0] + other[1][0] * this[2][1] + other[2][0] * this[2][2] + other[3][0] * this[2][3]
        tmp[3][0] = other[0][0] * this[3][0] + other[1][0] * this[3][1] + other[2][0] * this[3][2] + other[3][0] * this[3][3]
        tmp[0][1] = other[0][1] * this[0][0] + other[1][1] * this[0][1] + other[2][1] * this[0][2] + other[3][1] * this[0][3]
        tmp[1][1] = other[0][1] * this[1][0] + other[1][1] * this[1][1] + other[2][1] * this[1][2] + other[3][1] * this[1][3]
        tmp[2][1] = other[0][1] * this[2][0] + other[1][1] * this[2][1] + other[2][1] * this[2][2] + other[3][1] * this[2][3]
        tmp[3][1] = other[0][1] * this[3][0] + other[1][1] * this[3][1] + other[2][1] * this[3][2] + other[3][1] * this[3][3]
        tmp[0][2] = other[0][2] * this[0][0] + other[1][2] * this[0][1] + other[2][2] * this[0][2] + other[3][2] * this[0][3]
        tmp[1][2] = other[0][2] * this[1][0] + other[1][2] * this[1][1] + other[2][2] * this[1][2] + other[3][2] * this[1][3]
        tmp[2][2] = other[0][2] * this[2][0] + other[1][2] * this[2][1] + other[2][2] * this[2][2] + other[3][2] * this[2][3]
        tmp[3][2] = other[0][2] * this[3][0] + other[1][2] * this[3][1] + other[2][2] * this[3][2] + other[3][2] * this[3][3]
        tmp[0][3] = other[0][3] * this[0][0] + other[1][3] * this[0][1] + other[2][3] * this[0][2] + other[3][3] * this[0][3]
        tmp[1][3] = other[0][3] * this[1][0] + other[1][3] * this[1][1] + other[2][3] * this[1][2] + other[3][3] * this[1][3]
        tmp[2][3] = other[0][3] * this[2][0] + other[1][3] * this[2][1] + other[2][3] * this[2][2] + other[3][3] * this[2][3]
        tmp[3][3] = other[0][3] * this[3][0] + other[1][3] * this[3][1] + other[2][3] * this[3][2] + other[3][3] * this[3][3]
        values[0] = tmp[0]
        values[1] = tmp[1]
        values[2] = tmp[2]
        values[3] = tmp[3]
        return this
    }
}