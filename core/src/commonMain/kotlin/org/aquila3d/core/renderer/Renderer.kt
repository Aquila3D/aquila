package org.aquila3d.core.renderer

import com.toxicbakery.logging.Arbor
import org.aquila3d.core.surface.VkWindow
import org.aquila3d.core.vulkan.VkApplicationInfo
import org.aquila3d.core.vulkan.VkInstance
import org.aquila3d.core.vulkan.makeVulkanVersion

class Renderer() {

    val window: VkWindow
    val instance: VkInstance

    init {
        Arbor.d("Creating window.")
        window = createWindow()

        /* Look for instance extensions */
        val requiredExtensions = window.getRequiredExtensions()

        // Create the Vulkan instance
        Arbor.d("Creating Vulkan instance.")
        val applicationInfo = VkApplicationInfo(makeVulkanVersion(1,1, 0))
        instance = VkInstance(applicationInfo, requiredExtensions, true)
    }

    fun destroy() {
        Arbor.d("Destroying window.")
        window.destroy()
        Arbor.d("Destroying Vulkan instance.")
        instance.destroy()
    }

    private fun createWindow(): VkWindow {
        return VkWindow(800, 600, "Hello, Vulkan JVM")
    }
}