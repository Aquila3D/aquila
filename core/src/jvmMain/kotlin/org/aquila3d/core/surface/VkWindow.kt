package org.aquila3d.core.surface

import org.aquila3d.core.vulkan.VkApplicationInfo
import org.aquila3d.core.vulkan.VkInstance
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWVulkan.glfwGetRequiredInstanceExtensions
import org.lwjgl.glfw.GLFWVulkan.glfwVulkanSupported
import org.lwjgl.vulkan.VK10.VK_API_VERSION_1_0


actual class VkWindow actual constructor(width: Int, height: Int, title: String) {

    private val window: Long

    init {
        if (!glfwInit()) {
            throw RuntimeException("Failed to initialize GLFW")
        }
        if (!glfwVulkanSupported()) {
            throw AssertionError("GLFW failed to find the Vulkan loader")
        }

        /* Look for instance extensions */
        val requiredExtensions = glfwGetRequiredInstanceExtensions()
            ?: throw AssertionError("Failed to find list of required Vulkan extensions")

        println("Required extensions: ${requiredExtensions.stringASCII}")

        val extensions = mutableListOf<String>()

        // Create the Vulkan instance
        val applicationInfo = VkApplicationInfo(VK_API_VERSION_1_0)
        val instance = VkInstance(applicationInfo, listOf(), true)

        // Configure GLFW
        glfwDefaultWindowHints() // optional, the current window hints are already the default
        glfwWindowHint(GLFW_CLIENT_API, GLFW_NO_API)
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE) // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE) // the window will be resizable

        // Create the window
        window = glfwCreateWindow(300, 300, title, 0, 0)
        if (window == 0L) {
            throw RuntimeException("Failed to create the GLFW window")
        }

        glfwShowWindow(window)
    }
}