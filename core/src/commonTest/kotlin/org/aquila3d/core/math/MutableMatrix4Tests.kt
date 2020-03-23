@file:Suppress("LocalVariableName")

package org.aquila3d.core.math

import assertEqualsEpsilon
import kotlin.test.Test

class MutableMatrix4Tests {

    @Test
    fun testSetFrom() {
        val translation = Vector3(1.0, 2.0, 3.0)
        val rotation = Quaternion.fromEulerAnglesDeg(Degrees(30.0), Degrees(30.0), Degrees(30.0))
        val scale = Vector3(1.0, 2.0, 3.0)
        val m = MutableMatrix4()
        m.setFrom(translation, rotation, scale)
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

    @Test
    fun testLeftMultiply() {
        val A = MutableMatrix4()
        val B = MutableMatrix4()

        A[0][0] = 0.88610; A[1][0] = 0.36168; A[2][0] = 0.61301; A[3][0] = 0.49806
        A[0][1] = 0.26466; A[1][1] = 0.78486; A[2][1] = 0.49674; A[3][1] = 0.49443
        A[0][2] = 0.64951; A[1][2] = 0.96838; A[2][2] = 0.98280; A[3][2] = 0.69466
        A[0][3] = 0.72035; A[1][3] = 0.57587; A[2][3] = 0.39520; A[3][3] = 0.97343

        B[0][0] = 0.157278; B[1][0] = 0.584293; B[2][0] = 0.216831; B[3][0] = 0.109770
        B[0][1] = 0.324174; B[1][1] = 0.312580; B[2][1] = 0.251901; B[3][1] = 0.302213
        B[0][2] = 0.386402; B[1][2] = 0.034158; B[2][2] = 0.824342; B[3][2] = 0.481572
        B[0][3] = 0.602885; B[1][3] = 0.227031; B[2][3] = 0.224532; B[3][3] = 0.088438

        A.leftMultiply(B)

        assertEqualsEpsilon(0.51391, A[0][0], 1e-4)
        assertEqualsEpsilon(0.78866, A[1][0], 1e-4)
        assertEqualsEpsilon(0.64314, A[2][0], 1e-4)
        assertEqualsEpsilon(0.62470, A[3][0], 1e-4)

        assertEqualsEpsilon(0.75129, A[0][1], 1e-4)
        assertEqualsEpsilon(0.78055, A[1][1], 1e-4)
        assertEqualsEpsilon(0.72100, A[2][1], 1e-4)
        assertEqualsEpsilon(0.78517, A[3][1], 1e-4)

        assertEqualsEpsilon(1.23375, A[0][2], 1e-4)
        assertEqualsEpsilon(1.24216, A[1][2], 1e-4)
        assertEqualsEpsilon(1.25431, A[2][2], 1e-4)
        assertEqualsEpsilon(1.25075, A[3][2], 1e-4)

        assertEqualsEpsilon(0.80385, A[0][3], 1e-4)
        assertEqualsEpsilon(0.66460, A[1][3], 1e-4)
        assertEqualsEpsilon(0.73797, A[2][3], 1e-4)
        assertEqualsEpsilon(0.65458, A[3][3], 1e-4)
    }
}