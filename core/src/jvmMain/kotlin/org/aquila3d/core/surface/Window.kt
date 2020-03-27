package org.aquila3d.core.surface

import org.lwjgl.glfw.GLFW.*


actual class Window actual constructor(width: Int, height: Int, title: String) {

    internal val window: Long

    init {
        // Configure GLFW
        glfwDefaultWindowHints() // optional, the current window hints are already the default
        glfwWindowHint(GLFW_CLIENT_API, GLFW_NO_API)
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE) // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE) // the window will be resizable

        // Create the window
        window = glfwCreateWindow(width, height, title, 0, 0)
        if (window == 0L) {
            throw RuntimeException("Failed to create the GLFW window")
        }

        glfwShowWindow(window)
    }

    actual fun destroy() {
        glfwDestroyWindow(window)
        glfwTerminate()
    }

    actual fun onResized(width: Int, height: Int) {
        /*TriangleDemo.width = width
        TriangleDemo.height = height
        swapchainRecreator.mustRecreate = true*/
    }
}