package org.aquila3d.core.math

import assertEqualsEpsilon
import kotlin.test.Test
import kotlin.test.assertNotNull

class Matrix4Tests {

    @Test
    fun testDefaultConstructor() {
        val m = Matrix4()
        assertNotNull(m)
        // Column 0
        assertEqualsEpsilon(1.0, m[0][0], "Invalid value at index: [0][0]")
        assertEqualsEpsilon(0.0, m[0][1], "Invalid value at index: [0][1]")
        assertEqualsEpsilon(0.0, m[0][2], "Invalid value at index: [0][2]")
        assertEqualsEpsilon(0.0, m[0][3], "Invalid value at index: [0][3]")

        // Column 1
        assertEqualsEpsilon(0.0, m[1][0], "Invalid value at index: [1][0]")
        assertEqualsEpsilon(1.0, m[1][1], "Invalid value at index: [1][1]")
        assertEqualsEpsilon(0.0, m[1][2], "Invalid value at index: [1][2]")
        assertEqualsEpsilon(0.0, m[1][3], "Invalid value at index: [1][3]")

        // Column 2
        assertEqualsEpsilon(0.0, m[2][0], "Invalid value at index: [2][0]")
        assertEqualsEpsilon(0.0, m[2][1], "Invalid value at index: [2][1]")
        assertEqualsEpsilon(1.0, m[2][2], "Invalid value at index: [2][2]")
        assertEqualsEpsilon(0.0, m[2][3], "Invalid value at index: [2][3]")

        // Column 3
        assertEqualsEpsilon(0.0, m[3][0], "Invalid value at index: [3][0]")
        assertEqualsEpsilon(0.0, m[3][1], "Invalid value at index: [3][1]")
        assertEqualsEpsilon(0.0, m[3][2], "Invalid value at index: [3][2]")
        assertEqualsEpsilon(1.0, m[3][3], "Invalid value at index: [3][3]")
    }

    @Test
    fun testTranslationRotationConstructorYaw() {
        val translation = Vector3(1.0, 2.0, 3.0)
        val rotation = Quaternion.fromEulerAnglesDeg(Degrees(30.0), Degrees(0.0), Degrees(0.0))
        val m = Matrix4(translation, rotation)
        assertNotNull(m)
        // Column 0
        assertEqualsEpsilon(0.8660254037844387, m[0][0], "Invalid value at index: [0][0].")
        assertEqualsEpsilon(0.0, m[0][1], "Invalid value at index: [0][1]")
        assertEqualsEpsilon(-0.5, m[0][2], "Invalid value at index: [0][2]")
        assertEqualsEpsilon(0.0, m[0][3], "Invalid value at index: [0][3]")

        // Column 1
        assertEqualsEpsilon(0.0, m[1][0], "Invalid value at index: [1][0]")
        assertEqualsEpsilon(1.0, m[1][1], "Invalid value at index: [1][1]")
        assertEqualsEpsilon(0.0, m[1][2], "Invalid value at index: [1][2]")
        assertEqualsEpsilon(0.0, m[1][3], "Invalid value at index: [1][3]")

        // Column 2
        assertEqualsEpsilon(0.5, m[2][0], "Invalid value at index: [2][0]")
        assertEqualsEpsilon(0.0, m[2][1], "Invalid value at index: [2][1]")
        assertEqualsEpsilon(0.8660254037844387, m[2][2], "Invalid value at index: [2][2]")
        assertEqualsEpsilon(0.0, m[2][3], "Invalid value at index: [2][3]")

        // Column 3
        assertEqualsEpsilon(1.0, m[3][0], "Invalid value at index: [3][0]")
        assertEqualsEpsilon(2.0, m[3][1], "Invalid value at index: [3][1]")
        assertEqualsEpsilon(3.0, m[3][2], "Invalid value at index: [3][2]")
        assertEqualsEpsilon(1.0, m[3][3], "Invalid value at index: [3][3]")
    }

    @Test
    fun testTranslationRotationConstructorPitch() {
        val translation = Vector3(1.0, 2.0, 3.0)
        val rotation = Quaternion.fromEulerAnglesDeg(Degrees(0.0), Degrees(30.0), Degrees(0.0))
        val m = Matrix4(translation, rotation)
        assertNotNull(m)
        // Column 0
        assertEqualsEpsilon(1.0, m[0][0], "Invalid value at index: [0][0].")
        assertEqualsEpsilon(0.0, m[0][1], "Invalid value at index: [0][1]")
        assertEqualsEpsilon(0.0, m[0][2], "Invalid value at index: [0][2]")
        assertEqualsEpsilon(0.0, m[0][3], "Invalid value at index: [0][3]")

        // Column 1
        assertEqualsEpsilon(0.0, m[1][0], "Invalid value at index: [1][0]")
        assertEqualsEpsilon(0.8660254037844387, m[1][1], "Invalid value at index: [1][1]")
        assertEqualsEpsilon(0.5, m[1][2], "Invalid value at index: [1][2]")
        assertEqualsEpsilon(0.0, m[1][3], "Invalid value at index: [1][3]")

        // Column 2
        assertEqualsEpsilon(0.0, m[2][0], "Invalid value at index: [2][0]")
        assertEqualsEpsilon(-0.5, m[2][1], "Invalid value at index: [2][1]")
        assertEqualsEpsilon(0.8660254037844387, m[2][2], "Invalid value at index: [2][2]")
        assertEqualsEpsilon(0.0, m[2][3], "Invalid value at index: [2][3]")

        // Column 3
        assertEqualsEpsilon(1.0, m[3][0], "Invalid value at index: [3][0]")
        assertEqualsEpsilon(2.0, m[3][1], "Invalid value at index: [3][1]")
        assertEqualsEpsilon(3.0, m[3][2], "Invalid value at index: [3][2]")
        assertEqualsEpsilon(1.0, m[3][3], "Invalid value at index: [3][3]")
    }

    @Test
    fun testTranslationRotationConstructorRoll() {
        val translation = Vector3(1.0, 2.0, 3.0)
        val rotation = Quaternion.fromEulerAnglesDeg(Degrees(0.0), Degrees(0.0), Degrees(30.0))
        val m = Matrix4(translation, rotation)
        assertNotNull(m)
        // Column 0
        assertEqualsEpsilon(0.8660254037844387, m[0][0], "Invalid value at index: [0][0].")
        assertEqualsEpsilon(0.5, m[0][1], "Invalid value at index: [0][1]")
        assertEqualsEpsilon(0.0, m[0][2], "Invalid value at index: [0][2]")
        assertEqualsEpsilon(0.0, m[0][3], "Invalid value at index: [0][3]")

        // Column 1
        assertEqualsEpsilon(-0.5, m[1][0], "Invalid value at index: [1][0]")
        assertEqualsEpsilon(0.8660254037844387, m[1][1], "Invalid value at index: [1][1]")
        assertEqualsEpsilon(0.0, m[1][2], "Invalid value at index: [1][2]")
        assertEqualsEpsilon(0.0, m[1][3], "Invalid value at index: [1][3]")

        // Column 2
        assertEqualsEpsilon(0.0, m[2][0], "Invalid value at index: [2][0]")
        assertEqualsEpsilon(0.0, m[2][1], "Invalid value at index: [2][1]")
        assertEqualsEpsilon(1.0, m[2][2], "Invalid value at index: [2][2]")
        assertEqualsEpsilon(0.0, m[2][3], "Invalid value at index: [2][3]")

        // Column 3
        assertEqualsEpsilon(1.0, m[3][0], "Invalid value at index: [3][0]")
        assertEqualsEpsilon(2.0, m[3][1], "Invalid value at index: [3][1]")
        assertEqualsEpsilon(3.0, m[3][2], "Invalid value at index: [3][2]")
        assertEqualsEpsilon(1.0, m[3][3], "Invalid value at index: [3][3]")
    }

    @Test
    fun testTranslationRotationConstructorYawPitch() {
        val translation = Vector3(1.0, 2.0, 3.0)
        val rotation = Quaternion.fromEulerAnglesDeg(Degrees(30.0), Degrees(30.0), Degrees(0.0))
        val m = Matrix4(translation, rotation)
        assertNotNull(m)
        // Column 0
        assertEqualsEpsilon(0.8660254037844387, m[0][0], "Invalid value at index: [0][0].")
        assertEqualsEpsilon(0.0, m[0][1], "Invalid value at index: [0][1]")
        assertEqualsEpsilon(-0.5, m[0][2], "Invalid value at index: [0][2]")
        assertEqualsEpsilon(0.0, m[0][3], "Invalid value at index: [0][3]")

        // Column 1
        assertEqualsEpsilon(0.25, m[1][0], "Invalid value at index: [1][0]")
        assertEqualsEpsilon(0.8660254037844387, m[1][1], "Invalid value at index: [1][1]")
        assertEqualsEpsilon(0.43301270189221924, m[1][2], "Invalid value at index: [1][2]")
        assertEqualsEpsilon(0.0, m[1][3], "Invalid value at index: [1][3]")

        // Column 2
        assertEqualsEpsilon(0.43301270189221924, m[2][0], "Invalid value at index: [2][0]")
        assertEqualsEpsilon(-0.5, m[2][1], "Invalid value at index: [2][1]")
        assertEqualsEpsilon(0.75, m[2][2], "Invalid value at index: [2][2]")
        assertEqualsEpsilon(0.0, m[2][3], "Invalid value at index: [2][3]")

        // Column 3
        assertEqualsEpsilon(1.0, m[3][0], "Invalid value at index: [3][0]")
        assertEqualsEpsilon(2.0, m[3][1], "Invalid value at index: [3][1]")
        assertEqualsEpsilon(3.0, m[3][2], "Invalid value at index: [3][2]")
        assertEqualsEpsilon(1.0, m[3][3], "Invalid value at index: [3][3]")
    }

    @Test
    fun testTranslationRotationConstructorPitchRoll() {
        val translation = Vector3(1.0, 2.0, 3.0)
        val rotation = Quaternion.fromEulerAnglesDeg(Degrees(0.0), Degrees(30.0), Degrees(30.0))
        val m = Matrix4(translation, rotation)
        assertNotNull(m)
        // Column 0
        assertEqualsEpsilon(0.8660254037844387, m[0][0], "Invalid value at index: [0][0].")
        assertEqualsEpsilon(0.43301270189221924, m[0][1], "Invalid value at index: [0][1]")
        assertEqualsEpsilon(0.25, m[0][2], "Invalid value at index: [0][2]")
        assertEqualsEpsilon(0.0, m[0][3], "Invalid value at index: [0][3]")

        // Column 1
        assertEqualsEpsilon(-0.5, m[1][0], "Invalid value at index: [1][0]")
        assertEqualsEpsilon(0.75, m[1][1], "Invalid value at index: [1][1]")
        assertEqualsEpsilon(0.43301270189221924, m[1][2], "Invalid value at index: [1][2]")
        assertEqualsEpsilon(0.0, m[1][3], "Invalid value at index: [1][3]")

        // Column 2
        assertEqualsEpsilon(0.0, m[2][0], "Invalid value at index: [2][0]")
        assertEqualsEpsilon(-0.5, m[2][1], "Invalid value at index: [2][1]")
        assertEqualsEpsilon(0.8660254037844387, m[2][2], "Invalid value at index: [2][2]")
        assertEqualsEpsilon(0.0, m[2][3], "Invalid value at index: [2][3]")

        // Column 3
        assertEqualsEpsilon(1.0, m[3][0], "Invalid value at index: [3][0]")
        assertEqualsEpsilon(2.0, m[3][1], "Invalid value at index: [3][1]")
        assertEqualsEpsilon(3.0, m[3][2], "Invalid value at index: [3][2]")
        assertEqualsEpsilon(1.0, m[3][3], "Invalid value at index: [3][3]")
    }

    @Test
    fun testTranslationRotationConstructorYawRoll() {
        val translation = Vector3(1.0, 2.0, 3.0)
        val rotation = Quaternion.fromEulerAnglesDeg(Degrees(30.0), Degrees(0.0), Degrees(30.0))
        val m = Matrix4(translation, rotation)
        assertNotNull(m)
        // Column 0
        assertEqualsEpsilon(0.75, m[0][0], "Invalid value at index: [0][0].")
        assertEqualsEpsilon(0.5, m[0][1], "Invalid value at index: [0][1]")
        assertEqualsEpsilon(-0.43301270189221924, m[0][2], "Invalid value at index: [0][2]")
        assertEqualsEpsilon(0.0, m[0][3], "Invalid value at index: [0][3]")

        // Column 1
        assertEqualsEpsilon(-0.43301270189221924, m[1][0], "Invalid value at index: [1][0]")
        assertEqualsEpsilon(0.8660254037844387, m[1][1], "Invalid value at index: [1][1]")
        assertEqualsEpsilon(0.25, m[1][2], "Invalid value at index: [1][2]")
        assertEqualsEpsilon(0.0, m[1][3], "Invalid value at index: [1][3]")

        // Column 2
        assertEqualsEpsilon(0.5, m[2][0], "Invalid value at index: [2][0]")
        assertEqualsEpsilon(0.0, m[2][1], "Invalid value at index: [2][1]")
        assertEqualsEpsilon(0.8660254037844387, m[2][2], "Invalid value at index: [2][2]")
        assertEqualsEpsilon(0.0, m[2][3], "Invalid value at index: [2][3]")

        // Column 3
        assertEqualsEpsilon(1.0, m[3][0], "Invalid value at index: [3][0]")
        assertEqualsEpsilon(2.0, m[3][1], "Invalid value at index: [3][1]")
        assertEqualsEpsilon(3.0, m[3][2], "Invalid value at index: [3][2]")
        assertEqualsEpsilon(1.0, m[3][3], "Invalid value at index: [3][3]")
    }

    @Test
    fun testTranslationRotationConstructorYawPitchRoll() {
        val translation = Vector3(1.0, 2.0, 3.0)
        val rotation = Quaternion.fromEulerAnglesDeg(Degrees(30.0), Degrees(30.0), Degrees(30.0))
        val m = Matrix4(translation, rotation)
        assertNotNull(m)
        // Column 0
        assertEqualsEpsilon(0.875, m[0][0], "Invalid value at index: [0][0].")
        assertEqualsEpsilon(0.43301270189221924, m[0][1], "Invalid value at index: [0][1]")
        assertEqualsEpsilon(-0.21650635094610965, m[0][2], "Invalid value at index: [0][2]")
        assertEqualsEpsilon(0.0, m[0][3], "Invalid value at index: [0][3]")

        // Column 1
        assertEqualsEpsilon(-0.21650635094610965, m[1][0], "Invalid value at index: [1][0]")
        assertEqualsEpsilon(0.75, m[1][1], "Invalid value at index: [1][1]")
        assertEqualsEpsilon(0.625, m[1][2], "Invalid value at index: [1][2]")
        assertEqualsEpsilon(0.0, m[1][3], "Invalid value at index: [1][3]")

        // Column 2
        assertEqualsEpsilon(0.43301270189221924, m[2][0], "Invalid value at index: [2][0]")
        assertEqualsEpsilon(-0.5, m[2][1], "Invalid value at index: [2][1]")
        assertEqualsEpsilon(0.75, m[2][2], "Invalid value at index: [2][2]")
        assertEqualsEpsilon(0.0, m[2][3], "Invalid value at index: [2][3]")

        // Column 3
        assertEqualsEpsilon(1.0, m[3][0], "Invalid value at index: [3][0]")
        assertEqualsEpsilon(2.0, m[3][1], "Invalid value at index: [3][1]")
        assertEqualsEpsilon(3.0, m[3][2], "Invalid value at index: [3][2]")
        assertEqualsEpsilon(1.0, m[3][3], "Invalid value at index: [3][3]")
    }

    @Test
    fun testTranslationRotationScaleConstructor() {
        val translation = Vector3(1.0, 2.0, 3.0)
        val rotation = Quaternion.fromEulerAnglesDeg(Degrees(30.0), Degrees(30.0), Degrees(30.0))
        val scale = Vector3(1.0, 2.0, 3.0)
        val m = Matrix4(translation, rotation, scale)
        assertNotNull(m)
        // Column 0
        assertEqualsEpsilon(0.875, m[0][0], "Invalid value at index: [0][0].")
        assertEqualsEpsilon(0.43301270189221924, m[0][1], "Invalid value at index: [0][1]")
        assertEqualsEpsilon(-0.21650635094610965, m[0][2], "Invalid value at index: [0][2]")
        assertEqualsEpsilon(0.0, m[0][3], "Invalid value at index: [0][3]")

        // Column 1
        assertEqualsEpsilon(-0.43301270189221963, m[1][0], "Invalid value at index: [1][0]")
        assertEqualsEpsilon(1.5, m[1][1], "Invalid value at index: [1][1]")
        assertEqualsEpsilon(1.25, m[1][2], "Invalid value at index: [1][2]")
        assertEqualsEpsilon(0.0, m[1][3], "Invalid value at index: [1][3]")

        // Column 2
        assertEqualsEpsilon(1.299038105676658, m[2][0], "Invalid value at index: [2][0]")
        assertEqualsEpsilon(-1.5, m[2][1], "Invalid value at index: [2][1]")
        assertEqualsEpsilon(2.25, m[2][2], "Invalid value at index: [2][2]")
        assertEqualsEpsilon(0.0, m[2][3], "Invalid value at index: [2][3]")

        // Column 3
        assertEqualsEpsilon(1.0, m[3][0], "Invalid value at index: [3][0]")
        assertEqualsEpsilon(2.0, m[3][1], "Invalid value at index: [3][1]")
        assertEqualsEpsilon(3.0, m[3][2], "Invalid value at index: [3][2]")
        assertEqualsEpsilon(1.0, m[3][3], "Invalid value at index: [3][3]")
    }
}