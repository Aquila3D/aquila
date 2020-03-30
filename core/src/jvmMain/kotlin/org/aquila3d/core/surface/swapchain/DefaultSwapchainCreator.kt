package org.aquila3d.core.surface.swapchain

import com.toxicbakery.logging.Arbor
import org.aquila3d.core.surface.Surface
import org.aquila3d.core.surface.Window
import org.aquila3d.core.vulkan.*
import org.aquila3d.core.vulkan.surface_khr.VkColorSpaceKHR
import org.aquila3d.core.vulkan.surface_khr.VkPresentModeKHR
import org.aquila3d.core.vulkan.surface_khr.VkSurfaceCapabilitiesKHR
import org.aquila3d.core.vulkan.surface_khr.VkSurfaceFormatKHR
import org.lwjgl.system.MemoryUtil
import org.lwjgl.system.MemoryUtil.NULL
import org.lwjgl.system.MemoryUtil.memAllocInt
import org.lwjgl.vulkan.KHRSurface
import org.lwjgl.vulkan.KHRSurface.VK_SURFACE_TRANSFORM_IDENTITY_BIT_KHR
import org.lwjgl.vulkan.KHRSwapchain
import org.lwjgl.vulkan.KHRSwapchain.vkCreateSwapchainKHR
import org.lwjgl.vulkan.VK10
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VkSwapchainCreateInfoKHR
import java.nio.IntBuffer
import kotlin.math.max
import kotlin.math.min


open class DefaultSwapchainCreator(
    device: VkDevice,
    physicalDevice: VkPhysicalDevice,
    surface: Surface
) :
    SwapchainCreator(device, physicalDevice, surface) {

    override fun createSwapchain(window: Window): Swapchain {
        return buildSwapchain(window, null)
    }

    override fun recreateSwapchain(window: Window, oldSwapchain: Swapchain): Swapchain? {
        Arbor.d("Recreating swapchain.")
        /*val cmdBufInfo = VkCommandBufferBeginInfo.calloc()
            .sType(VK10.VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO)
        var err = VK10.vkBeginCommandBuffer(setupCommandBuffer, cmdBufInfo)
        cmdBufInfo.free()
        if (err != VK10.VK_SUCCESS) {
            throw AssertionError("Failed to begin setup command buffer: ${VkResult(err)}")
        }
        // Create the swapchain (this will also add a memory barrier to initialize the framebuffer images)
        val swapchain = buildSwapchain(window, oldSwapchain)
        err = VK10.vkEndCommandBuffer(setupCommandBuffer)
        if (err != VK10.VK_SUCCESS) {
            throw AssertionError("Failed to end setup command buffer: ${VkResult(err)}")
        }
        submitCommandBuffer(queue, setupCommandBuffer)
        VK10.vkQueueWaitIdle(queue)

        if (framebuffers != null) {
            for (i in 0 until framebuffers.length) VK10.vkDestroyFramebuffer(device, framebuffers.get(i), null)
        }
        framebuffers = createFramebuffers(device, swapchain, renderPass, width, height)
        // Create render command buffers
        // Create render command buffers
        if (renderCommandBuffers != null) {
            VK10.vkResetCommandPool(device, renderCommandPool, VK_FLAGS_NONE)
        }
        renderCommandBuffers = createRenderCommandBuffers(
            device, renderCommandPool, framebuffers, renderPass, width, height, pipeline,
            vertices.verticesBuf
        )*/
        return null
    }

    protected fun buildSwapchain(window: Window, oldSwapchain: Swapchain?): Swapchain {
        val swapchainFeatures = physicalDevice.getSwapchainFeatures()
        val capabilities = swapchainFeatures.capabilities
        val surfaceFormat = chooseSurfaceFormat(swapchainFeatures.formats)
        val presentationMode = choosePresentationMode(swapchainFeatures.presentMode)
        val swapExtent = chooseSwapExtent(window, swapchainFeatures.capabilities)

        var numberOfSwapchainImages = swapchainFeatures.capabilities.minImageCount() + 1
        @Suppress("ConvertTwoComparisonsToRangeCheck")
        // Suppressed to make the nature of the check more clear. Vulkan uses 0 as a special value
        if (swapchainFeatures.capabilities.maxImageCount() > 0
            && numberOfSwapchainImages > swapchainFeatures.capabilities.maxImageCount()
        ) {
            numberOfSwapchainImages = swapchainFeatures.capabilities.maxImageCount()
        }

        val preTransform = if ((capabilities.supportedTransforms() and VK_SURFACE_TRANSFORM_IDENTITY_BIT_KHR) != 0) {
            VK_SURFACE_TRANSFORM_IDENTITY_BIT_KHR
        } else {
            capabilities.currentTransform()
        }

        val indices = physicalDevice.getQueueFamilyIndices()
        val swapchainCI = VkSwapchainCreateInfoKHR.calloc()
            .sType(KHRSwapchain.VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR)
            .surface(surface.surfaceHandle)
            .minImageCount(numberOfSwapchainImages)
            .imageFormat(surfaceFormat.getFormat().value)
            .imageColorSpace(surfaceFormat.getColorSpace().value)
            .imageArrayLayers(1)
            .imageUsage(VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT)
            .preTransform(preTransform)
            .presentMode(presentationMode.value)
            .oldSwapchain(oldSwapchain?.handle ?: NULL)
            .clipped(true)
            .compositeAlpha(KHRSurface.VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR)
        swapchainCI.imageExtent()
            .width(window.getWidth())
            .height(window.getHeight())

        if (indices[VkQueueFamilies.VK_QUEUE_GRAPHICS] != indices[VkQueueFamilies.VK_QUEUE_PRESENTATION]) {
            swapchainCI.imageSharingMode(VK_SHARING_MODE_CONCURRENT)
            val queueFamilyIndicies = memAllocInt(2)
            queueFamilyIndicies.put(0, indices[VkQueueFamilies.VK_QUEUE_GRAPHICS]!!)
            queueFamilyIndicies.put(1, indices[VkQueueFamilies.VK_QUEUE_PRESENTATION]!!)
            swapchainCI.pQueueFamilyIndices(queueFamilyIndicies)
        } else {
            swapchainCI.imageSharingMode(VK_SHARING_MODE_EXCLUSIVE)
            swapchainCI.pQueueFamilyIndices(null) // Optional
        }

        val swapchainPointer = MemoryUtil.memAllocLong(1)
        val err = vkCreateSwapchainKHR(device.handle, swapchainCI, null, swapchainPointer)
        swapchainCI.free()
        val handle = swapchainPointer[0]
        MemoryUtil.memFree(swapchainPointer)
        if (err != VK_SUCCESS) {
            throw AssertionError("Failed to create swap chain: ${VkResult(err)}")
        }
        val newSwapchain = Swapchain(handle, surfaceFormat, device)
        // If we just re-created an existing swapchain, we should destroy the old swapchain at this point.
        // Note: destroying the swapchain also cleans up all its associated presentable images once the platform is done with them.
        oldSwapchain?.destroy()
        return newSwapchain
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun chooseSurfaceFormat(formats: List<VkSurfaceFormatKHR>): VkSurfaceFormatKHR {
        var selectedFormat = formats[0]
        for (format in formats) {
            if (format.getFormat() == VkFormat.VK_FORMAT_B8G8R8A8_UNORM && format.getColorSpace() == VkColorSpaceKHR.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR) {
                selectedFormat = format
            }
        }

        Arbor.d("Selected format: %s", selectedFormat)
        return selectedFormat
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun choosePresentationMode(availableModes: List<VkPresentModeKHR>): VkPresentModeKHR {
        var selectedMode = availableModes
            .find { mode -> mode == VkPresentModeKHR.VK_PRESENT_MODE_MAILBOX_KHR }
            ?: VkPresentModeKHR.VK_PRESENT_MODE_FIFO_KHR

        Arbor.d("Selected presentation mode: %s", selectedMode)
        return selectedMode
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun chooseSwapExtent(window: Window, capabilities: VkSurfaceCapabilitiesKHR): VkExtent2D {
        var retval = capabilities.currentExtent()
        if (capabilities.currentExtent().width == Int.MAX_VALUE) {
            val actualExtent = VkExtent2D(window.getWidth(), window.getHeight())

            actualExtent.width =
                actualExtent.width.coerceIn(capabilities.minImageExtent().width, capabilities.maxImageExtent().width)
            actualExtent.height =
                actualExtent.height.coerceIn(capabilities.minImageExtent().height, capabilities.maxImageExtent().height)
            retval = actualExtent
        }
        Arbor.d("Selected extent: %s", retval)
        return retval
    }
}
