package org.aquila3d.core.surface

import nvk.VulkanWindow
import kotlin.test.Test
import kotlin.test.assertEquals

class WindowTest {

    @Test
    fun createWindow() {
        val window: VulkanWindow = Window(8, 16, "Hello World!").window
        assertEquals(8, window.width)
        assertEquals(16, window.height)
        assertEquals("Hello World!", window.title)
    }

}
