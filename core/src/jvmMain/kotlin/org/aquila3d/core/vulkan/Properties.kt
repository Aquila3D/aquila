package org.aquila3d.core.vulkan

import com.toxicbakery.logging.Arbor
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.MemoryUtil.memAllocInt
import org.lwjgl.system.MemoryUtil.memFree
import org.lwjgl.vulkan.VK10.VK_SUCCESS
import org.lwjgl.vulkan.VK10.vkEnumerateInstanceLayerProperties
import org.lwjgl.vulkan.VkLayerProperties

/**
 * Return true if all layer names specified in [layers] can be found in available layer properties.
 */
actual fun checkLayersAvailable(layers: List<String>): Boolean {
    val pointer = memAllocInt(1)
    var retval = false
    try {
        stackPush().use { stack ->
            var err = vkEnumerateInstanceLayerProperties(pointer, null)
            if (err != VK_SUCCESS) {
                Arbor.e("Failed to get available layer count: %s", VkResult(err).toString())
                return@use
            }
            val available = VkLayerProperties.mallocStack(pointer.get(0), stack)
            err = vkEnumerateInstanceLayerProperties(pointer, available)
            if (err != VK_SUCCESS) {
                Arbor.e("Failed to get available layer list: %s", VkResult(err).toString())
                return@use
            }
            for (layer in layers) {
                for (j in 0 until available.capacity()) {
                    available.position(j)
                    if (layer == available.layerNameString()) {
                        retval = true
                        break
                    }
                }
                if (!retval) {
                    Arbor.e("Cannot find layer: %s\n", layer)
                    return@use
                }
            }
        }
    } finally {
        memFree(pointer)
        return retval
    }
}