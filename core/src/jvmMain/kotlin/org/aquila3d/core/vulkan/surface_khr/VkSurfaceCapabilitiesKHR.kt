package org.aquila3d.core.vulkan.surface_khr

import org.aquila3d.core.vulkan.VkExtent2D

internal fun makeVkExtent2D(extent: org.lwjgl.vulkan.VkExtent2D): VkExtent2D {
    return VkExtent2D(extent.width(), extent.height())
}

actual class VkSurfaceCapabilitiesKHR(capabilities: org.lwjgl.vulkan.VkSurfaceCapabilitiesKHR) {

    private val minImageCount = capabilities.minImageCount()
    private val maxImageCount = capabilities.maxImageCount()
    private val currentExtent = makeVkExtent2D(capabilities.currentExtent())
    private val minImageExtent = makeVkExtent2D(capabilities.minImageExtent())
    private val maxImageExtent = makeVkExtent2D(capabilities.maxImageExtent())
    private val maxImageArrayLayers = capabilities.maxImageArrayLayers()
    private val supportedTransforms = capabilities.supportedTransforms()
    private val currentTransform = capabilities.currentTransform()
    private val supportedCompositeAlpha = capabilities.supportedCompositeAlpha()
    private val supportedUsageFlags = capabilities.supportedUsageFlags()

    actual fun minImageCount(): Int {
        return minImageCount
    }

    actual fun maxImageCount(): Int {
        return maxImageCount
    }

    actual fun currentExtent(): VkExtent2D {
        return currentExtent
    }

    actual fun minImageExtent(): VkExtent2D {
        return minImageExtent
    }

    actual fun maxImageExtent(): VkExtent2D {
        return maxImageExtent
    }

    actual fun maxImageArrayLayers(): Int {
        return maxImageArrayLayers
    }

    actual fun supportedTransforms(): Int {
        return supportedTransforms
    }

    actual fun currentTransform(): Int {
        return currentTransform
    }

    actual fun supportedCompositeAlpha(): Int {
        return supportedCompositeAlpha
    }

    actual fun supportedUsageFlags(): Int {
        return supportedUsageFlags
    }

    override fun toString(): String {
        return "VkSurfaceCapabilitiesKHR(" +
                "\nminImageCount=$minImageCount," +
                "\nmaxImageCount=$maxImageCount," +
                "\ncurrentExtent=$currentExtent," +
                "\nminImageExtent=$minImageExtent," +
                "\nmaxImageExtent=$maxImageExtent," +
                "\nmaxImageArrayLayers=$maxImageArrayLayers," +
                "\nsupportedTransforms=$supportedTransforms," +
                "\ncurrentTransform=$currentTransform," +
                "\nsupportedCompositeAlpha=$supportedCompositeAlpha," +
                "\nsupportedUsageFlags=${Integer.toHexString(supportedUsageFlags)})"
    }


}