package org.aquila3d.core.math

import kotlin.jvm.JvmStatic
import kotlin.math.sqrt

open class Vector3 {

    var x: Double
        private set
    var y: Double
        private set
    var z: Double
        private set

    constructor() {
        x = 0.0
        y = 0.0
        z = 0.0
    }

    constructor(x: Float, y: Float, z: Float) {
        this.x = x.toDouble()
        this.y = y.toDouble()
        this.z = z.toDouble()
    }

    constructor(x: Double, y: Double, z: Double) {
        this.x = x
        this.y = y
        this.z = z
    }

    constructor(other: Vector3) {
        x = other.x
        y = other.y
        z = other.z
    }

    operator fun unaryMinus() {
        invert()
    }

    operator fun plus(other: Vector3): Vector3 {
        return add(other)
    }

    operator fun minus(other: Vector3): Vector3 {
        return subtract(other)
    }

    operator fun times(scale: Float): Vector3 {
        return multiply(scale)
    }

    operator fun times(scale: Double): Vector3 {
        return multiply(scale)
    }

    operator fun times(other: Vector3): Double {
        return dot(other)
    }

    fun magnitude(): Double {
        return sqrt(x * x + y * y + z * z)
    }

    fun normalize(): Vector3 {
        val magnitude = magnitude()
        x /= magnitude
        y /= magnitude
        z /= magnitude
        return this
    }

    fun invert(): Vector3 {
        x = -x
        y = -y
        z = -z
        return this
    }

    fun add(other: Vector3): Vector3 {
        x += other.x
        y += other.y
        z += other.z
        return this
    }

    fun subtract(other: Vector3): Vector3 {
        x -= other.x
        y -= other.y
        z -= other.z
        return this
    }

    fun multiply(scale: Float): Vector3 {
        return multiply(scale.toDouble())
    }

    fun multiply(scale: Double): Vector3 {
        x *= scale
        y *= scale
        z *= scale
        return this
    }

    fun cross(other: Vector3): Vector3 {
        val xNew = y * other.z - z * other.y
        val yNew = -x * other.z + z * other.x
        val zNew = x * other.y - y * other.x
        x = xNew
        y = yNew
        z = zNew
        return this
    }

    fun dot(other: Vector3): Double {
        return (x * other.x + y * other.y + z * other.z)
    }

    companion object {
        @JvmStatic
        fun cross(u: Vector3, v: Vector3): Vector3 {
            return Vector3(u).cross(v)
        }

        @JvmStatic
        fun dot(u: Vector3, v: Vector3): Double {
            return Vector3(u).dot(v)
        }

        @JvmStatic
        fun scalarTripleProduct(u: Vector3, v: Vector3, w: Vector3): Double {
            return cross(v, w).dot(u)
        }
    }
}