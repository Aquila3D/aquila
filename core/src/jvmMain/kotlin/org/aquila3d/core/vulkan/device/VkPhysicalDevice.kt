package org.aquila3d.core.vulkan.device

import com.toxicbakery.logging.Arbor
import org.aquila3d.core.surface.Surface
import org.aquila3d.core.surface.swapchain.SwapchainFeatures
import org.aquila3d.core.vulkan.VkResult
import org.aquila3d.core.vulkan.memory.VkMemoryHeap
import org.aquila3d.core.vulkan.memory.VkMemoryType
import org.aquila3d.core.vulkan.memory.VkPhysicalDeviceMemoryProperties
import org.aquila3d.core.vulkan.surface_khr.VkPresentModeKHR
import org.aquila3d.core.vulkan.surface_khr.VkSurfaceCapabilitiesKHR
import org.aquila3d.core.vulkan.surface_khr.VkSurfaceFormatKHR
import org.lwjgl.system.MemoryUtil.memAllocInt
import org.lwjgl.system.MemoryUtil.memFree
import org.lwjgl.vulkan.KHRSurface.*
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VK11
import org.lwjgl.vulkan.VkExtensionProperties
import org.lwjgl.vulkan.VkQueueFamilyProperties

actual class VkPhysicalDevice(internal val handle: org.lwjgl.vulkan.VkPhysicalDevice, private val surface: Surface) {

    private val queueFamilies: Map<VkQueueFamilies, Int> by lazy {
        val queueCountPointer = memAllocInt(1)
        vkGetPhysicalDeviceQueueFamilyProperties(handle, queueCountPointer, null)
        val queueCount = queueCountPointer.get(0)
        val queueProps = VkQueueFamilyProperties.calloc(queueCount)
        vkGetPhysicalDeviceQueueFamilyProperties(handle, queueCountPointer, queueProps)
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
            val err = vkGetPhysicalDeviceSurfaceSupportKHR(handle, index, surface.surfaceHandle, supportsPresent)
            if (err != VK_SUCCESS) {
                Arbor.e("Failed to physical device surface support: %s",
                    VkResult(err)
                )
            } else {
                if (supportsPresent.get(0) == VK_TRUE) {
                    queueFamilies[VkQueueFamilies.VK_QUEUE_PRESENTATION] = index
                }
            }
        }
        memFree(supportsPresent)
        queueProps.free()
        queueFamilies
    }

    private val extensions: Map<String, Int> by lazy {
        val extensionCountPointer = memAllocInt(1)
        var err = vkEnumerateDeviceExtensionProperties(handle, null as String?, extensionCountPointer, null)
        if (err != VK_SUCCESS) {
            throw AssertionError("Failed to get number of physical device extensions: " + VkResult(
                err
            )
            )
        }
        val extensionCount = extensionCountPointer.get(0)
        val extensionsPointer = VkExtensionProperties.calloc(extensionCount)
        err = vkEnumerateDeviceExtensionProperties(handle, null as String?, extensionCountPointer, extensionsPointer)
        memFree(extensionCountPointer)
        if (err != VK_SUCCESS) {
            throw AssertionError("Failed to get physical device extensions: " + VkResult(
                err
            )
            )
        }
        val extensions = mutableMapOf<String, Int>()
        for (index in 0 until extensionCount) {
            extensionsPointer.position(index)
            extensions[extensionsPointer.extensionNameString()] = extensionsPointer.specVersion()
        }
        extensions
    }

    private val swapFeatures: SwapchainFeatures by lazy {
        val surfaceCapabilities = getSurfaceCapabilities()
        val formats = getSurfaceFormats()
        val presentationModes = getPresentationModes()

        SwapchainFeatures(surfaceCapabilities, formats, presentationModes)
    }

    private val memProperties: VkPhysicalDeviceMemoryProperties by lazy {
        val memoryProperties = org.lwjgl.vulkan.VkPhysicalDeviceMemoryProperties.calloc()
        vkGetPhysicalDeviceMemoryProperties(handle, memoryProperties)
        val memoryTypes = (0..memoryProperties.memoryTypeCount())
            .map { idx ->
                val type = memoryProperties.memoryTypes(idx)
                VkMemoryType(type.propertyFlags(), type.heapIndex())
            }
        val memoryHeaps = (0..memoryProperties.memoryHeapCount())
            .map { idx ->
                val heap = memoryProperties.memoryHeaps(idx)
                VkMemoryHeap(heap.size(), heap.flags())
            }
        val properties = VkPhysicalDeviceMemoryProperties(
            memoryTypes.toList(),
            memoryHeaps.toList()
        )
        memoryProperties.free()
        properties
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

    actual fun getMemoryProperties(): VkPhysicalDeviceMemoryProperties {
        return memProperties
    }

    private fun getSurfaceCapabilities(): VkSurfaceCapabilitiesKHR {
        val surfCaps = org.lwjgl.vulkan.VkSurfaceCapabilitiesKHR.calloc()
        val err = vkGetPhysicalDeviceSurfaceCapabilitiesKHR(handle, surface.surfaceHandle, surfCaps)
        if (err != VK_SUCCESS) {
            throw AssertionError("Failed to get physical device surface capabilities: ${VkResult(
                err
            )}")
        }
        val surfaceCapabilities = VkSurfaceCapabilitiesKHR(surfCaps)
        surfCaps.free()
        return surfaceCapabilities
    }

    private fun getSurfaceFormats(): List<VkSurfaceFormatKHR> {
        val formatCountPointer = memAllocInt(1)
        var err = vkGetPhysicalDeviceSurfaceFormatsKHR(handle, surface.surfaceHandle, formatCountPointer, null)
        val formatCount = formatCountPointer[0]
        if (err != VK_SUCCESS) {
            throw AssertionError("Failed to get number of physical device surface presentation modes: ${VkResult(
                err
            )}")
        }
        val formatsPointer = org.lwjgl.vulkan.VkSurfaceFormatKHR.calloc(formatCount)
        err = vkGetPhysicalDeviceSurfaceFormatsKHR(handle, surface.surfaceHandle, formatCountPointer, formatsPointer)
        memFree(formatCountPointer)
        if (err != VK_SUCCESS) {
            throw AssertionError(
                "Failed to get physical device surface presentation modes: ${VkResult(
                    err
                )}")
        }
        val formats = formatsPointer.map { format -> VkSurfaceFormatKHR(format) }
        formatsPointer.free()
        return formats
    }

    private fun getPresentationModes(): List<VkPresentModeKHR> {
        val presentModeCountPointer = memAllocInt(1)
        var err = vkGetPhysicalDeviceSurfacePresentModesKHR(handle, surface.surfaceHandle, presentModeCountPointer, null)
        val presentModeCount = presentModeCountPointer[0]
        if (err != VK_SUCCESS) {
            throw AssertionError("Failed to get number of physical device surface presentation modes: ${VkResult(
                err
            )}")
        }
        val pPresentModes = memAllocInt(presentModeCount)
        err = vkGetPhysicalDeviceSurfacePresentModesKHR(handle, surface.surfaceHandle, presentModeCountPointer, pPresentModes)
        memFree(presentModeCountPointer)
        if (err != VK_SUCCESS) {
            throw AssertionError(
                "Failed to get physical device surface presentation modes: ${VkResult(
                    err
                )}")
        }
        val modes = mutableListOf<VkPresentModeKHR>()
        for (i in 0 until presentModeCount) {
            modes.add(VkPresentModeKHR.from(pPresentModes.get(i)))
        }
        memFree(pPresentModes)
        return modes
    }
}
