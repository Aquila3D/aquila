package org.aquila3d.core.vulkan

import org.lwjgl.system.MemoryUtil.*
import org.lwjgl.vulkan.EXTDebugReport.VK_EXT_DEBUG_REPORT_EXTENSION_NAME
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VkInstanceCreateInfo
import java.nio.ByteBuffer

actual class VkInstance actual constructor(applicationInfo: VkApplicationInfo, requiredExtensions: List<String>, debug: Boolean) {

    val instance: org.lwjgl.vulkan.VkInstance

    companion object {
        val layers = listOf<ByteBuffer>(memUTF8("VK_LAYER_LUNARG_standard_validation"))
    }

    init {
        val ppEnabledExtensionNames = memAllocPointer(requiredExtensions.size + 1)
        for (extension in requiredExtensions) {
            ppEnabledExtensionNames.put(memUTF8(extension))
        }
        val vkExtDebugReportExtension = memUTF8(VK_EXT_DEBUG_REPORT_EXTENSION_NAME)
        ppEnabledExtensionNames.put(vkExtDebugReportExtension)
        ppEnabledExtensionNames.flip()
        val ppEnabledLayerNames = memAllocPointer(layers.size)
        if (debug) {
            for (element in layers) {
                ppEnabledLayerNames.put(element)
            }
        }
        ppEnabledLayerNames.flip()
        val pCreateInfo = VkInstanceCreateInfo.calloc()
            .sType(VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO)
            .pApplicationInfo(applicationInfo.info)
            .ppEnabledExtensionNames(ppEnabledExtensionNames)
            .ppEnabledLayerNames(ppEnabledLayerNames)
        val pInstance = memAllocPointer(1)
        val err = vkCreateInstance(pCreateInfo, null, pInstance)
        val instancePointer = pInstance.get(0)
        memFree(pInstance)
        if (err != VK_SUCCESS) {
            throw AssertionError("Failed to create VkInstance: " + translateVulkanResult(err))
        }
        instance = org.lwjgl.vulkan.VkInstance(instancePointer, pCreateInfo)
        pCreateInfo.free();
        memFree(ppEnabledLayerNames)
        memFree(vkExtDebugReportExtension)
        memFree(ppEnabledExtensionNames)
        memFree(applicationInfo.info.pApplicationName())
        memFree(applicationInfo.info.pEngineName())
    }
}