package org.aquila3d.core.vulkan

expect class VkInstance(applicationInfo: VkApplicationInfo, requiredExtensions: List<String>, debug: Boolean = false) {
    fun destroy()
    fun getCapabilities(): List<String>
}