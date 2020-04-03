package org.aquila3d.core.surface.swapchain

import org.aquila3d.core.vulkan.device.VkDevice
import org.aquila3d.core.vulkan.VkImage
import org.aquila3d.core.vulkan.VkImageView
import org.aquila3d.core.vulkan.VkResult
import org.aquila3d.core.vulkan.surface_khr.VkSurfaceFormatKHR
import org.lwjgl.system.MemoryUtil.*
import org.lwjgl.vulkan.KHRSwapchain
import org.lwjgl.vulkan.KHRSwapchain.vkGetSwapchainImagesKHR
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VkImageViewCreateInfo

actual class Swapchain(val handle: Long, val format: VkSurfaceFormatKHR, val device: VkDevice) {

    private var images: Array<VkImage>
    private var imageViews: Array<VkImageView>

    init {
        val imageCountPointer = memAllocInt(1)
        var err = vkGetSwapchainImagesKHR(device.handle, handle, imageCountPointer, null)
        val imageCount = imageCountPointer[0]
        if (err != VK_SUCCESS) {
            throw AssertionError("Failed to get number of swapchain images: ${VkResult(err)}")
        }

        val imagesPointer = memAllocLong(imageCount)
        err = vkGetSwapchainImagesKHR(device.handle, handle, imageCountPointer, imagesPointer)
        if (err != VK_SUCCESS) {
            throw AssertionError("Failed to get swapchain images: ${VkResult(err)}")
        }
        memFree(imageCountPointer)

        //TODO: Make the create info struct configurable by library users
        val bufferViewPointer = memAllocLong(1)
        val colorAttachmentView = VkImageViewCreateInfo.calloc()
            .sType(VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO)
            .format(format.getFormat().value)
            .viewType(VK_IMAGE_VIEW_TYPE_2D)
        colorAttachmentView.subresourceRange()
            .aspectMask(VK_IMAGE_ASPECT_COLOR_BIT)
            .levelCount(1)
            .layerCount(1)
        images = Array(imageCount) { VkImage(imagesPointer[it]) }
        imageViews = Array(imageCount) {
            colorAttachmentView.image(images[it].handle)
            err = vkCreateImageView(device.handle, colorAttachmentView, null, bufferViewPointer)
            if (err != VK_SUCCESS) {
                throw AssertionError("Failed to create image view: ${VkResult(err)}")
            }
            VkImageView(bufferViewPointer[0])
        }
        colorAttachmentView.free()
        memFree(bufferViewPointer)
        memFree(imagesPointer)
    }

    actual fun destroy() {
        for (view in imageViews) {
            view.destroy(device)
        }
        if (handle != VK_NULL_HANDLE) {
            KHRSwapchain.vkDestroySwapchainKHR(device.handle, handle, null)
        }
    }
}
