package org.aquila3d.core.vulkan

import com.toxicbakery.logging.Arbor
import org.lwjgl.glfw.GLFWVulkan.glfwGetRequiredInstanceExtensions
import org.lwjgl.system.MemoryUtil.*
import org.lwjgl.vulkan.EXTDebugUtils.vkCreateDebugUtilsMessengerEXT
import org.lwjgl.vulkan.EXTDebugUtils.vkDestroyDebugUtilsMessengerEXT
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VkInstanceCreateInfo


//TODO: vulkan allocator
actual class VkInstance actual constructor(
    applicationInfo: VkApplicationInfo,
    requiredExtensions: List<String>,
    layers: List<String>,
    debugUtilsMessengerCallbackCreateInfo: VkDebugUtilsMessengerCallbackCreateInfo?
) {

    internal val instance: org.lwjgl.vulkan.VkInstance

    private val debugUtilsCallbackInfo = debugUtilsMessengerCallbackCreateInfo
    private val debugMessageCallback: Long

    init {
        Arbor.d("Creating instance with extensions: %s", requiredExtensions)
        val ppEnabledExtensionNames = memAllocPointer(requiredExtensions.size)
        for (extension in requiredExtensions) {
            ppEnabledExtensionNames.put(memUTF8(extension))
        }
        ppEnabledExtensionNames.flip()
        val ppEnabledLayerNames = memAllocPointer(layers.size)
        for (element in layers) {
            ppEnabledLayerNames.put(memUTF8(element))
        }
        ppEnabledLayerNames.flip()
        val pCreateInfo = VkInstanceCreateInfo.calloc()
            .sType(VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO)
            .pApplicationInfo(applicationInfo.info)
            .ppEnabledExtensionNames(ppEnabledExtensionNames)
            .ppEnabledLayerNames(ppEnabledLayerNames)

        debugUtilsMessengerCallbackCreateInfo?.also { pCreateInfo.pNext(debugUtilsMessengerCallbackCreateInfo.dbgCreateInfo.address()) }
        val pInstance = memAllocPointer(1)
        var err = vkCreateInstance(pCreateInfo, null, pInstance)
        val instancePointer = pInstance.get(0)
        memFree(pInstance)
        if (err != VK_SUCCESS) {
            throw AssertionError("Failed to create VkInstance: " + VkResult(err))
        }
        instance = org.lwjgl.vulkan.VkInstance(instancePointer, pCreateInfo)

        // Cleanup the native structures
        pCreateInfo.free()
        memFree(ppEnabledLayerNames)
        memFree(ppEnabledExtensionNames)

        if (debugUtilsMessengerCallbackCreateInfo != null) {
            val lp = memAllocLong(1)
            err = vkCreateDebugUtilsMessengerEXT(instance, debugUtilsMessengerCallbackCreateInfo.dbgCreateInfo, null, lp)
            when (err) {
                VK_SUCCESS -> debugMessageCallback = lp.get(0)
                VK_ERROR_OUT_OF_HOST_MEMORY -> throw IllegalStateException("CreateDebugReportCallback: out of host memory")
                else -> throw IllegalStateException("CreateDebugReportCallback: unknown failure")
            }
            memFree(lp)
            debugUtilsMessengerCallbackCreateInfo.dbgCreateInfo.free() // We dont need the create info anymore, so clean it up
        } else {
            debugMessageCallback = NULL
        }
    }

    actual fun destroy() {
        if (debugMessageCallback != NULL) {
            Arbor.d("Destroying debug messenger callback.")
            vkDestroyDebugUtilsMessengerEXT(instance, debugMessageCallback, null)
        }
        debugUtilsCallbackInfo?.callback?.function?.free()
        Arbor.d("Destroying native instance.")
        vkDestroyInstance(instance, null)
    }

    actual fun getCapabilities(): List<String> {
        val localCapabilities = instance.capabilities
        TODO("Not yet implemented")
    }

}

actual fun getRequiredInstanceExtensions(): List<String> {
    val requiredExtensions = glfwGetRequiredInstanceExtensions()
        ?: throw AssertionError("Failed to find list of required Vulkan extensions")
    return mutableListOf(requiredExtensions.stringUTF8)
}