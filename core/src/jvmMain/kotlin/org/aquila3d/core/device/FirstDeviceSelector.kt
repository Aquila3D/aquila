package org.aquila3d.core.device

import org.aquila3d.core.surface.Surface
import org.aquila3d.core.vulkan.VkPhysicalDevice
import org.aquila3d.core.vulkan.VkQueueFamilies
import org.aquila3d.core.vulkan.VkResult
import org.lwjgl.system.MemoryUtil.*
import org.lwjgl.vulkan.VK10.VK_SUCCESS
import org.lwjgl.vulkan.VK10.vkEnumeratePhysicalDevices

//TODO: This class could do better by prioritizing devices which are either discrete (or not) GPUs as well as favoring
// devices which support multiple queue families in the same queue index for performance
actual class FirstDeviceSelector : DeviceSelector {

    override fun select(surface: Surface, requiredQueueFamilies: List<VkQueueFamilies>): VkPhysicalDevice? {
        val instance = surface.instance
        val pPhysicalDeviceCount = memAllocInt(1)
        var err = vkEnumeratePhysicalDevices(instance.instance, pPhysicalDeviceCount, null)
        if (err != VK_SUCCESS) {
            throw AssertionError("Failed to get number of physical devices: " + VkResult(err))
        }
        val pPhysicalDevices = memAllocPointer(pPhysicalDeviceCount.get(0))
        err = vkEnumeratePhysicalDevices(instance.instance, pPhysicalDeviceCount, pPhysicalDevices)
        var returnDevice: VkPhysicalDevice? = null
        for (i in 0 until pPhysicalDeviceCount.get(0)) {
            val pPhysicalDevice = pPhysicalDevices.get(i)
            if (err != VK_SUCCESS) {
                throw AssertionError("Failed to get physical devices: " + VkResult(err))
            }
            val device = org.lwjgl.vulkan.VkPhysicalDevice(pPhysicalDevice, instance.instance)
            val physicalDevice = VkPhysicalDevice(device, surface)
            if (physicalDevice.getQueueFamilyIndices().keys.containsAll(requiredQueueFamilies)) {
                returnDevice = physicalDevice
                break
            }
        }
        memFree(pPhysicalDeviceCount)
        memFree(pPhysicalDevices)
        return returnDevice
    }
}