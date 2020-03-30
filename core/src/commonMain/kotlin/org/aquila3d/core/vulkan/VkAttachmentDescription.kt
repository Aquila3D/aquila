package org.aquila3d.core.vulkan

class VkAttachmentDescription {

    var format: VkFormat = VkFormat.VK_FORMAT_B8G8R8A8_UNORM
    var samples: VkSampleCountFlagBits = VkSampleCountFlagBits.VK_SAMPLE_COUNT_1_BIT
    var loadOp: VkAttachmentLoadOp = VkAttachmentLoadOp.VK_ATTACHMENT_LOAD_OP_CLEAR
    var storeOp: VkAttachmentStoreOp = VkAttachmentStoreOp.VK_ATTACHMENT_STORE_OP_STORE
    var stencilLoadOp: VkAttachmentLoadOp = VkAttachmentLoadOp.VK_ATTACHMENT_LOAD_OP_DONT_CARE
    var stencilStoreOp: VkAttachmentStoreOp = VkAttachmentStoreOp.VK_ATTACHMENT_STORE_OP_DONT_CARE
    var initialLayout: VkImageLayout = VkImageLayout.VK_IMAGE_LAYOUT_UNDEFINED
    var finalLayout: VkImageLayout = VkImageLayout.VK_IMAGE_LAYOUT_PRESENT_SRC_KHR

    inline fun format(format: () -> VkFormat) {
        this.format = format()
    }

    inline fun samples(samples: () -> VkSampleCountFlagBits) {
        this.samples = samples()
    }

    inline fun loadOp(loadOp: () -> VkAttachmentLoadOp) {
        this.loadOp = loadOp()
    }

    inline fun storeOp(storeOp: () -> VkAttachmentStoreOp) {
        this.storeOp = storeOp()
    }

    inline fun stencilLoadOp(loadOp: () -> VkAttachmentLoadOp) {
        this.stencilLoadOp = loadOp()
    }

    inline fun stencilStoreOp(storeOp: () -> VkAttachmentStoreOp) {
        this.stencilStoreOp = storeOp()
    }

    inline fun initialLayout(layout: () -> VkImageLayout) {
        this.initialLayout = layout()
    }

    inline fun finalLayout(layout: () -> VkImageLayout) {
        this.finalLayout = layout()
    }
}
