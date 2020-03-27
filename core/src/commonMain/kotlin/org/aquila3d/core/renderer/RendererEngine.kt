package org.aquila3d.core.renderer

import org.aquila3d.core.device.DeviceSelector
import org.aquila3d.core.input.InputEventListener
import org.aquila3d.core.surface.Window
import org.aquila3d.core.vulkan.VkDebugUtilsMessengerCallbackCreateInfo
import org.aquila3d.core.vulkan.VkDevice
import org.aquila3d.core.vulkan.VkPhysicalDevice
import org.aquila3d.core.vulkan.VkQueueFamilies

interface RendererEngine {

    fun configureDebug(requiredExtensions: MutableList<String>): VkDebugUtilsMessengerCallbackCreateInfo

    fun getDeviceSelector(): DeviceSelector

    fun getRequiredQueueFamilies(): List<VkQueueFamilies>

    fun createLogicalDevice(physicalDevice: VkPhysicalDevice, requiredExtensions: List<String>): VkDevice

    fun onAttachedToWindow(window: Window)

    fun registerInputEventListener(listener: InputEventListener)

    fun unregisterInputEventListener(listener: InputEventListener)

    fun startRenderLoop()

    fun stopRenderLoop()

    fun resumeRenderLoop()

    fun pauseRenderLoop()
}