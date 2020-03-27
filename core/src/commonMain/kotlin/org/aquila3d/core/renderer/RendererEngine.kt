package org.aquila3d.core.renderer

import org.aquila3d.core.device.DeviceSelector
import org.aquila3d.core.input.InputEventListener
import org.aquila3d.core.surface.Surface
import org.aquila3d.core.surface.Window
import org.aquila3d.core.vulkan.*

interface RendererEngine {

    fun requiredInstanceExtensions(): List<String>

    fun requiredQueueFamilies(): List<VkQueueFamilies>

    fun configureDebug(requiredExtensions: MutableList<String>): VkDebugUtilsMessengerCallbackCreateInfo

    fun getDeviceSelector(): DeviceSelector

    fun createSurface(instance: VkInstance, window: Window): Surface

    fun createLogicalDevice(physicalDevice: VkPhysicalDevice, requiredExtensions: List<String>): VkDevice

    fun onAttachedToWindow(window: Window)

    fun registerInputEventListener(listener: InputEventListener)

    fun unregisterInputEventListener(listener: InputEventListener)

    fun startRenderLoop()

    fun stopRenderLoop()

    fun resumeRenderLoop()

    fun pauseRenderLoop()
}