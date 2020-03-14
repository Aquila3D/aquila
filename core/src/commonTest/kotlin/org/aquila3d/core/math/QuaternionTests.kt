package org.aquila3d.core.math

import kotlin.math.abs
import kotlin.test.*

class QuaternionTests {

    @Test
    fun testDefaultConstructor() {
        val q = Quaternion()
        assertNotNull(q)
        assertEquals(0.0, q.w, "Invalid W component")
        assertEquals(0.0, q.x, "Invalid X component")
        assertEquals(1.0, q.y, "Invalid Y component")
        assertEquals(0.0, q.z, "Invalid Z component")
    }

    @Test
    fun testFloatComponentConstructor() {
        val q = Quaternion(1.0f, 2.0f, 3.0f, 4.0f)
        assertNotNull(q)
        assertEquals(1.0, q.w, "Invalid W component")
        assertEquals(2.0, q.x, "Invalid X component")
        assertEquals(3.0, q.y, "Invalid Y component")
        assertEquals(4.0, q.z, "Invalid Z component")
    }

    @Test
    fun testDoubleComponentConstructor() {
        val q = Quaternion(1.0, 2.0, 3.0, 4.0)
        assertNotNull(q)
        assertEquals(1.0, q.w, "Invalid W component")
        assertEquals(2.0, q.x, "Invalid X component")
        assertEquals(3.0, q.y, "Invalid Y component")
        assertEquals(4.0, q.z, "Invalid Z component")
    }

    @Test
    fun testQuaternionConstructor() {
        val from = Quaternion(1.0, 2.0, 3.0, 4.0)
        val output = Quaternion(from)
        assertNotNull(output)
        assertNotSame(from, output, "Should not be the same instance.")
        assertEquals(1.0, output.w, "Invalid W component")
        assertEquals(2.0, output.x, "Invalid X component")
        assertEquals(3.0, output.y, "Invalid Y component")
        assertEquals(4.0, output.z, "Invalid Z component")
    }

    @Test
    fun testAxisAngleConstructor() {
        val sqrt2o2 = 0.70710678118654752440084436210485

        // Test 90 degrees about X
        val x90 = Quaternion(Degrees(90.0).toRadians(), Vector3.X())
        assertNotNull(x90)
        assertTrue(abs(sqrt2o2 - x90.w) <= 1e-12, "Invalid W component: " + x90.w)
        assertTrue(abs(sqrt2o2 - x90.x) <= 1e-12, "Invalid X component: " + x90.x)
        assertEquals(0.0, x90.y, "Invalid Y component")
        assertEquals(0.0, x90.z, "Invalid Z component")

        // Test 90 degrees about Y
        val y90 = Quaternion(Degrees(90.0).toRadians(), Vector3.Y())
        assertNotNull(y90)
        assertTrue(abs(sqrt2o2 - y90.w) <= 1e-12, "Invalid W component: " + y90.w)
        assertEquals(0.0, y90.x, "Invalid X component")
        assertTrue(abs(sqrt2o2 - y90.y) <= 1e-12, "Invalid Y component: " + y90.y)
        assertEquals(0.0, y90.z, "Invalid Z component")

        // Test 90 degrees about Z
        val z90 = Quaternion(Degrees(90.0).toRadians(), Vector3.Z())
        assertNotNull(z90)
        assertTrue(abs(sqrt2o2 - z90.w) <= 1e-12, "Invalid W component: " + z90.w)
        assertEquals(0.0, z90.x, "Invalid X component")
        assertEquals(0.0, z90.y, "Invalid Y component")
        assertTrue(abs(sqrt2o2 - z90.z) <= 1e-12, "Invalid Z component: " + z90.z)

        // Test 30 degrees about an <1, 1, 1> vector.
        val q = Quaternion(Degrees(30.0).toRadians(), Vector3(1.0, 1.0, 1.0))
        assertNotNull(q)
        assertTrue(abs(0.9659258262890683 - q.w) <= 1e-12, "Invalid W component: " + q.w)
        assertTrue(abs(0.14942924536134225 - q.x) <= 1e-12, "Invalid X component: " + q.x)
        assertTrue(abs(0.14942924536134225 - q.y) < 1e-12, "Invalid Y component: " + q.y)
        assertTrue(abs(0.14942924536134225 - q.z) <= 1e-12, "Invalid Z component: " + q.z)

        // Test 30 degrees about an even <-1, -1, -1> vector.
        val p = Quaternion(Degrees(30.0).toRadians(), Vector3(-1.0, -1.0, -1.0))
        assertNotNull(p)
        assertTrue(abs(0.9659258262890683 - p.w) <= 1e-12, "Invalid W component: " + q.w)
        assertTrue(abs(-0.14942924536134225 - p.x) <= 1e-12, "Invalid X component: " + q.x)
        assertTrue(abs(-0.14942924536134225 - p.y) < 1e-12, "Invalid Y component: " + q.y)
        assertTrue(abs(-0.14942924536134225 - p.z) <= 1e-12, "Invalid Z component: " + q.z)
    }

    @Test
    fun testEulerAngleConstructor() {
        val sqrt2o2 = 0.70710678118654752440084436210485

        // Test 90 degrees about X
        val x90 = Quaternion(Radians(0.0), Degrees(90.0).toRadians(), Radians(0.0))
        assertNotNull(x90)
        assertTrue(abs(sqrt2o2 - x90.w) <= 1e-12, "x90 Invalid W component: " + x90.w)
        assertEquals(0.0, x90.x, "x90 Invalid X component")
        assertTrue(abs(sqrt2o2 - x90.y) <= 1e-12, "x90 Invalid Y component: " + x90.y)
        assertEquals(0.0, x90.z, "x90 Invalid Z component")

        // Test 90 degrees about Y
        val y90 = Quaternion(Degrees(90.0).toRadians(), Radians(0.0), Radians(0.0))
        assertNotNull(y90)
        assertTrue(abs(sqrt2o2 - y90.w) <= 1e-12, "y90 Invalid W component: " + y90.w)
        assertEquals(0.0, y90.x, "y90 Invalid X component")
        assertEquals(0.0, y90.y, "y90 Invalid Y component")
        assertTrue(abs(sqrt2o2 - y90.z) <= 1e-12, "y90 Invalid Z component: " + y90.z)

        // Test 90 degrees about Z
        val z90 = Quaternion(Radians(0.0), Radians(0.0), Degrees(90.0).toRadians())
        assertNotNull(z90)
        assertTrue(abs(sqrt2o2 - z90.w) <= 1e-12, "z90 Invalid W component: " + z90.w)
        assertTrue(abs(sqrt2o2 - z90.x) <= 1e-12, "z90 Invalid X component: " + z90.x)
        assertEquals(0.0, z90.y, "z90 Invalid Y component")
        assertEquals(0.0, z90.z, "z90 Invalid Z component")
    }
}