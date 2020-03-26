package org.aquila3d.core.vulkan

expect class VkApplicationInfo(version: Int) {
    fun apiVersion(): Int
}