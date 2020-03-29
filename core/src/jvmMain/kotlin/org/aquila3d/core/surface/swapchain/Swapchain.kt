package org.aquila3d.core.surface.swapchain

import org.aquila3d.core.vulkan.VkDevice
import org.aquila3d.core.vulkan.VkResult
import org.aquila3d.core.vulkan.surface_khr.VkSurfaceFormatKHR
import org.lwjgl.system.MemoryUtil.*
import org.lwjgl.vulkan.KHRSwapchain
import org.lwjgl.vulkan.KHRSwapchain.vkGetSwapchainImagesKHR
import org.lwjgl.vulkan.VK10
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VkImageViewCreateInfo


actual class Swapchain(val handle: Long, val format: VkSurfaceFormatKHR, val device: VkDevice) {

    private var images: LongArray
    private var imageViews: LongArray

    init {
        val pImageCount = memAllocInt(1)
        var err = vkGetSwapchainImagesKHR(device.handle, handle, pImageCount, null)
        val imageCount = pImageCount[0]
        if (err != VK_SUCCESS) {
            throw AssertionError("Failed to get number of swapchain images: ${VkResult(err)}")
        }

        val pSwapchainImages = memAllocLong(imageCount)
        err = vkGetSwapchainImagesKHR(device.handle, handle, pImageCount, pSwapchainImages)
        if (err != VK_SUCCESS) {
            throw AssertionError("Failed to get swapchain images: ${VkResult(err)}")
        }
        memFree(pImageCount)

        images = LongArray(imageCount)
        imageViews = LongArray(imageCount)
        val pBufferView = memAllocLong(1)
        val colorAttachmentView = VkImageViewCreateInfo.calloc()
            .sType(VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO)
            .format(format.getFormat().value)
            .viewType(VK_IMAGE_VIEW_TYPE_2D)
        colorAttachmentView.subresourceRange()
            .aspectMask(VK_IMAGE_ASPECT_COLOR_BIT)
            .levelCount(1)
            .layerCount(1)
        for (i in 0 until imageCount) {
            images[i] = pSwapchainImages[i]
            colorAttachmentView.image(images[i])
            err = vkCreateImageView(device.handle, colorAttachmentView, null, pBufferView)
            imageViews[i] = pBufferView[0]
            if (err != VK_SUCCESS) {
                throw AssertionError("Failed to create image view: ${VkResult(err)}")
            }
        }
        colorAttachmentView.free()
        memFree(pBufferView)
        memFree(pSwapchainImages)
    }

    actual fun destroy() {
        if (handle != VK10.VK_NULL_HANDLE) {
            KHRSwapchain.vkDestroySwapchainKHR(device.handle, handle, null)
        }
    }
}
