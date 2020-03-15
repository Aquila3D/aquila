package org.aquila3d.core.math

import kotlin.math.abs
import kotlin.test.*

class Vector3Tests {

    @Test
    fun testDefaultConstructor() {
        val u = Vector3()
        assertNotNull(u)
        assertEquals(0.0, u.x, "Invalid X component")
        assertEquals(0.0, u.y, "Invalid Y component")
        assertEquals(0.0, u.z, "Invalid Z component")
    }

    @Test
    fun testFloatComponentConstructor() {
        val u = Vector3(1.0f, 2.0f, 3.0f)
        assertNotNull(u)
        assertEquals(1.0, u.x, "Invalid X component")
        assertEquals(2.0, u.y, "Invalid Y component")
        assertEquals(3.0, u.z, "Invalid Z component")
    }

    @Test
    fun testDoubleComponentConstructor() {
        val u = Vector3(1.0, 2.0, 3.0)
        assertNotNull(u)
        assertEquals(1.0, u.x, "Invalid X component")
        assertEquals(2.0, u.y, "Invalid Y component")
        assertEquals(3.0, u.z, "Invalid Z component")
    }

    @Test
    fun testVector3Constructor() {
        val from = Vector3(3.0, 4.0, 5.0)
        val output = Vector3(from)
        assertNotNull(output)
        assertNotSame(from, output, "Should not be the same instance.")
        assertEquals(from.x, output.x, "Invalid X component")
        assertEquals(from.y, output.y, "Invalid Y component")
        assertEquals(from.z, output.z, "Invalid Z component")
    }

    @Test
    fun testUnaryMinusOperator() {
        val u = Vector3(1.0, 2.0, 3.0)
        -u
        assertEquals(-1.0, u.x, "Invalid X component")
        assertEquals(-2.0, u.y, "Invalid Y component")
        assertEquals(-3.0, u.z, "Invalid Z component")
    }

    @Test
    fun testPlusOperator() {
        val u = Vector3(1.0, 3.0, 4.0)
        val v = Vector3(5.0, 6.0, 7.0)
        val output = u + v
        assertSame(u, output, "Not the same instance.")
        assertEquals(6.0, output.x, "Invalid X component")
        assertEquals(9.0, output.y, "Invalid Y component")
        assertEquals(11.0, output.z, "Invalid Z component")
    }

    @Test
    fun testMinusOperator() {
        val u = Vector3(1.0, 3.0, 4.0)
        val v = Vector3(5.0, 6.0, 7.0)
        val output = u - v
        assertSame(u, output, "Not the same instance.")
        assertEquals(-4.0, output.x, "Invalid X component")
        assertEquals(-3.0, output.y, "Invalid Y component")
        assertEquals(-3.0, output.z, "Invalid Z component")
    }

    @Test
    fun testTimesFloatOperator() {
        val u = Vector3(1.0, 3.0, 4.0)
        val output = u * 2f
        assertSame(u, output, "Not the same instance.")
        assertEquals(2.0, output.x, "Invalid X component")
        assertEquals(6.0, output.y, "Invalid Y component")
        assertEquals(8.0, output.z, "Invalid Z component")
    }

    @Test
    fun testTimesDoubleOperator() {
        val u = Vector3(1.0, 3.0, 4.0)
        val output = u * 3.0
        assertSame(u, output, "Not the same instance.")
        assertEquals(3.0, output.x, "Invalid X component")
        assertEquals(9.0, output.y, "Invalid Y component")
        assertEquals(12.0, output.z, "Invalid Z component")
    }


    @Test
    fun testTimesVector3Operator() {
        val u = Vector3(1.0, 2.0, 3.0)
        val v = Vector3(1.0, 5.0, 7.0)
        val output = u * v
        assertEquals(32.0, output, "Invalid dot product")
    }

    @Test
    fun testDivFloatOperator() {
        val u = Vector3(2.0, 4.0, 6.0)
        val output = u / 2f
        assertSame(u, output, "Not the same instance.")
        assertEquals(1.0, output.x, "Invalid X component")
        assertEquals(2.0, output.y, "Invalid Y component")
        assertEquals(3.0, output.z, "Invalid Z component")
    }

    @Test
    fun testDivDoubleOperator() {
        val u = Vector3(2.0, 4.0, 6.0)
        val output = u / 2.0
        assertSame(u, output, "Not the same instance.")
        assertEquals(1.0, output.x, "Invalid X component")
        assertEquals(2.0, output.y, "Invalid Y component")
        assertEquals(3.0, output.z, "Invalid Z component")
    }

    @Test
    fun testMagnitude() {
        val u = Vector3(1.0, 3.0, 4.0)
        val magnitude = u.magnitude()
        val error = abs(magnitude - 5.099019513592784830028)
        assertTrue(1e-12 >= error, "Invalid magnitude error: $error")
    }

    @Test
    fun testNormalize() {
        val u = Vector3(1.0, 3.0, 4.0)
        val output = u.normalize()
        val magnitude = output.magnitude()
        val error = abs(magnitude - 1.0)
        assertSame(u, output, "Not the same instance.")
        assertTrue(1e-12 >= error, "Invalid magnitude error after normalizing: $error")
    }

    @Test
    fun testInvert() {
        val u = Vector3(1.0, 2.0, 3.0)
        val output = u.invert()
        assertSame(u, output, "Not the same instance.")
        assertEquals(-1.0, output.x, "Invalid X component")
        assertEquals(-2.0, output.y, "Invalid Y component")
        assertEquals(-3.0, output.z, "Invalid Z component")
    }


    @Test
    fun testCross() {
        val u = Vector3(1.0, 3.0, 4.0)
        val v = Vector3(5.0, 6.0, 7.0)
        val output = u.cross(v)
        assertSame(u, output, "Not the same instance.")
        assertEquals(-3.0, output.x, "Invalid X component")
        assertEquals(13.0, output.y, "Invalid Y component")
        assertEquals(-9.0, output.z, "Invalid Z component")
    }

    @Test
    fun testEquals() {
        val u = Vector3(1.0, 3.0, 4.0)
        val v = Vector3(1.0, 3.0, 4.0)
        val w = Vector3(5.0, 6.0, 7.0)

        assertTrue(u.equals(v), "Equivalent vectors are not equal.")
        assertFalse(u.equals(w), "Non-equivalent vectors are equal.")
    }

    @Test
    fun testStaticCross() {
        val u = Vector3(1.0, 3.0, 4.0)
        val v = Vector3(5.0, 6.0, 7.0)
        val output = Vector3.cross(u, v)
        assertNotSame(u, output, "Should not be the same instance.")
        assertNotSame(v, output, "Should not be the same instance.")
        assertEquals(-3.0, output.x, "Invalid X component")
        assertEquals(13.0, output.y, "Invalid Y component")
        assertEquals(-9.0, output.z, "Invalid Z component")
    }

    @Test
    fun testStaticDot() {
        val u = Vector3(1.0, 2.0, 3.0)
        val v = Vector3(1.0, 5.0, 7.0)
        val output = Vector3.dot(u, v)
        assertEquals(32.0, output, "Invalid dot product")
    }

    @Test
    fun testStaticScalarTripleProduct() {
        val u = Vector3(1.0, 2.0, 3.0)
        val v = Vector3(1.0, 5.0, 7.0)
        val w = Vector3(2.0, 1.0, 4.0)
        val output = Vector3.scalarTripleProduct(u, v, w)
        assertEquals(6.0, output, "Invalid dot product")
    }
}