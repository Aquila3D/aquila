package org.aquila3d.core.math

import kotlin.jvm.JvmStatic
import kotlin.math.sqrt

/**
 * Mutable representation of a direction and magnitude in 3D space.
 *
 * Most methods in this class support chaining for continuous math operations on the same instance. The class also
 * provides utility methods for generating a new [Vector3] from the operation as well as appropriate operator overloads.
 *
 * This class is not thread safe.
 */
open class Vector3 {

    var x: Double
        private set
    var y: Double
        private set
    var z: Double
        private set

    /**
     * Constructs a zero [Vector3] where all components are initialized to 0.
     */
    constructor() {
        x = 0.0
        y = 0.0
        z = 0.0
    }

    /**
     * Constructs a new [Vector3] from the provided [Float] components.
     */
    constructor(x: Float, y: Float, z: Float): this(x.toDouble(), y.toDouble(), z.toDouble())

    /**
     * Constructs a new [Vector3] from the provided [Double] components.
     */
    constructor(x: Double, y: Double, z: Double) {
        this.x = x
        this.y = y
        this.z = z
    }

    /**
     * Constructs a new unit [Vector3] from a provided [Vector3].
     */
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

    operator fun div(scale: Float): Vector3 {
        return divide(scale)
    }

    operator fun div(scale: Double): Vector3 {
        return divide(scale)
    }

    fun magnitude(): Double {
        return sqrt(x * x + y * y + z * z)
    }

    fun normalize(): Vector3 {
        val magnitude = magnitude()
        return divide(magnitude)
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

    fun multiply(quaternion: Quaternion): Quaternion {
        val wNew = -(x * quaternion.x + y * quaternion.y + z * quaternion.z)
        val xNew = quaternion.w * x + quaternion.z * y - quaternion.y * z
        val yNew = quaternion.w * y + quaternion.x * z - quaternion.z * x
        val zNew = quaternion.w * z + quaternion.y * x - quaternion.x * y
        return Quaternion(wNew, xNew, yNew, zNew)
    }

    fun divide(scale: Float): Vector3 {
        return divide(scale.toDouble())
    }

    fun divide(scale: Double): Vector3 {
        x /= scale
        y /= scale
        z /= scale
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

    override fun toString(): String {
        return "Vector3(x=$x, y=$y, z=$z)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Vector3

        if (x != other.x) return false
        if (y != other.y) return false
        if (z != other.z) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        return result
    }

    @Suppress("FunctionName")
    companion object {

        @JvmStatic
        fun X(): Vector3 {
            return Vector3(1.0, 0.0, 0.0)
        }

        @JvmStatic
        fun Y(): Vector3 {
            return Vector3(0.0, 1.0, 0.0)
        }

        @JvmStatic
        fun Z(): Vector3 {
            return Vector3(0.0, 0.0, 1.0)
        }

        @JvmStatic
        fun NEG_X(): Vector3 {
            return X().invert()
        }

        @JvmStatic
        fun NEG_Y(): Vector3 {
            return Y().invert()
        }

        @JvmStatic
        fun NEG_Z(): Vector3 {
            return Z().invert()
        }

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