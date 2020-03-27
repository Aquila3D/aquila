package org.aquila3d.core.vulkan

import com.toxicbakery.logging.Arbor
import org.aquila3d.core.surface.Surface
import org.aquila3d.core.surface.swapchain.SwapchainFeatures
import org.aquila3d.core.vulkan.surface_khr.VkSurfaceCapabilitiesKHR
import org.aquila3d.core.vulkan.surface_khr.VkSurfaceFormatKHR
import org.lwjgl.system.MemoryUtil.memAllocInt
import org.lwjgl.system.MemoryUtil.memFree
import org.lwjgl.vulkan.KHRSurface.*
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VK11
import org.lwjgl.vulkan.VkExtensionProperties
import org.lwjgl.vulkan.VkQueueFamilyProperties

actual class VkPhysicalDevice(internal val device: org.lwjgl.vulkan.VkPhysicalDevice, private val surface: Surface) {

    private val queueFamilies: Map<VkQueueFamilies, Int> by lazy {
        val queueCountPointer = memAllocInt(1)
        vkGetPhysicalDeviceQueueFamilyProperties(device, queueCountPointer, null)
        val queueCount = queueCountPointer.get(0)
        val queueProps = VkQueueFamilyProperties.calloc(queueCount)
        vkGetPhysicalDeviceQueueFamilyProperties(device, queueCountPointer, queueProps)
        memFree(queueCountPointer)
        val queueFamilies = mutableMapOf<VkQueueFamilies, Int>()
        val supportsPresent = memAllocInt(1)
        for (index in 0 until queueCount) {
            val flags = queueProps.get(index).queueFlags()
            when {
                (flags and VK_QUEUE_GRAPHICS_BIT) != 0 -> {
                    queueFamilies[VkQueueFamilies.VK_QUEUE_GRAPHICS] = index
                }
                (flags and VK_QUEUE_COMPUTE_BIT) != 0 -> {
                    queueFamilies[VkQueueFamilies.VK_QUEUE_COMPUTE] = index
                }
                (flags and VK_QUEUE_TRANSFER_BIT) != 0 -> {
                    queueFamilies[VkQueueFamilies.VK_QUEUE_TRANSFER] = index
                }
                (flags and VK_QUEUE_SPARSE_BINDING_BIT) != 0 -> {
                    queueFamilies[VkQueueFamilies.VK_QUEUE_SPARSE_BINDING] = index
                }
                (flags and VK11.VK_QUEUE_PROTECTED_BIT) != 0 -> {
                    queueFamilies[VkQueueFamilies.VK_QUEUE_PROTECTED] = index
                }
            }
            supportsPresent.position(0)
            val err = vkGetPhysicalDeviceSurfaceSupportKHR(device, index, surface.surfaceHandle, supportsPresent)
            if (err != VK_SUCCESS) {
                Arbor.e("Failed to physical device surface support: %s", VkResult(err))
            } else {
                if (supportsPresent.get(0) == VK_TRUE) {
                    queueFamilies[VkQueueFamilies.VK_QUEUE_PRESENTATION] = index
                }
            }
        }
        memFree(supportsPresent)
        queueProps.free()
        return@lazy queueFamilies
    }

    private val extensions: Map<String, Int> by lazy {
        val extensionCountPointer = memAllocInt(1)
        var err = vkEnumerateDeviceExtensionProperties(device, null as String?, extensionCountPointer, null)
        if (err != VK_SUCCESS) {
            throw AssertionError("Failed to get number of physical device extensions: " + VkResult(err))
        }
        val extensionCount = extensionCountPointer.get(0)
        val extensionsPointer = VkExtensionProperties.calloc(extensionCount)
        err = vkEnumerateDeviceExtensionProperties(device, null as String?, extensionCountPointer, extensionsPointer)
        memFree(extensionCountPointer)
        if (err != VK_SUCCESS) {
            throw AssertionError("Failed to get physical device extensions: " + VkResult(err))
        }
        val extensions = mutableMapOf<String, Int>()
        for (index in 0 until extensionCount) {
            extensionsPointer.position(index)
            extensions.put(extensionsPointer.extensionNameString(), extensionsPointer.specVersion())
        }
        return@lazy extensions
    }

    private val swapFeatures: SwapchainFeatures by lazy {
        val surfaceCapabilities = getSurfaceCapabilities()
        val formats = getSurfaceFormats()
        val presentationModes = getPresentationModes()

        return@lazy SwapchainFeatures(surfaceCapabilities, formats, presentationModes)
    }

    actual fun getQueueFamilyIndices(): Map<VkQueueFamilies, Int> {
        return queueFamilies
    }

    actual fun getDeviceExtensions(): Map<String, Int> {
        return extensions
    }

    actual fun getSwapchainFeatures(): SwapchainFeatures {
        return swapFeatures
    }

    private fun getSurfaceCapabilities(): VkSurfaceCapabilitiesKHR {
        val surfCaps = org.lwjgl.vulkan.VkSurfaceCapabilitiesKHR.calloc()
        val err = vkGetPhysicalDeviceSurfaceCapabilitiesKHR(device, surface.surfaceHandle, surfCaps)
        if (err != VK_SUCCESS) {
            throw AssertionError("Failed to get physical device surface capabilities: ${VkResult(err)}")
        }
        val surfaceCapabilities = VkSurfaceCapabilitiesKHR(surfCaps)
        surfCaps.free()
        return surfaceCapabilities
    }

    private fun getSurfaceFormats(): List<VkSurfaceFormatKHR> {
        val formatCountPointer = memAllocInt(1)
        var err = vkGetPhysicalDeviceSurfaceFormatsKHR(device, surface.surfaceHandle, formatCountPointer, null)
        val formatCount = formatCountPointer[0]
        if (err != VK_SUCCESS) {
            throw AssertionError("Failed to get number of physical device surface presentation modes: ${VkResult(err)}")
        }
        val formatsPointer = org.lwjgl.vulkan.VkSurfaceFormatKHR.calloc(formatCount)
        err = vkGetPhysicalDeviceSurfaceFormatsKHR(device, surface.surfaceHandle, formatCountPointer, formatsPointer)
        memFree(formatCountPointer)
        if (err != VK_SUCCESS) {
            throw AssertionError(
                "Failed to get physical device surface presentation modes: ${VkResult(err)}")
        }
        val formats = mutableListOf<VkSurfaceFormatKHR>()
        for (i in 0 until formatCount) {
            val format = formatsPointer.get(i)
            formats.add(VkSurfaceFormatKHR(format))
        }
        formatsPointer.free()
        return formats
    }

    private fun getPresentationModes(): List<Int> {
        val presentModeCountPointer = memAllocInt(1)
        var err = vkGetPhysicalDeviceSurfacePresentModesKHR(device, surface.surfaceHandle, presentModeCountPointer, null)
        val presentModeCount = presentModeCountPointer[0]
        if (err != VK_SUCCESS) {
            throw AssertionError("Failed to get number of physical device surface presentation modes: ${VkResult(err)}")
        }
        val pPresentModes = memAllocInt(presentModeCount)
        err = vkGetPhysicalDeviceSurfacePresentModesKHR(device, surface.surfaceHandle, presentModeCountPointer, pPresentModes)
        memFree(presentModeCountPointer)
        if (err != VK_SUCCESS) {
            throw AssertionError(
                "Failed to get physical device surface presentation modes: ${VkResult(err)}")
        }
        val modes = mutableListOf<Int>()
        for (i in 0 until presentModeCount) {
            modes.add(pPresentModes.get(i))
        }
        memFree(pPresentModes)
        return modes
    }
}