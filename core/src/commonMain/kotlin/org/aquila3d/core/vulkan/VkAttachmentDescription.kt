package org.aquila3d.core.vulkan

class VkAttachmentDescription {

    val format: VkFormat
    val samples: VkSampleCountFlagBits
    val loadOp: VkAttachmentLoadOp
    val storeOp: VkAttachmentStoreOp
    val stencilLoadOp: VkAttachmentLoadOp
    val stencilStoreOp: VkAttachmentStoreOp
    val initialLayout: VkImageLayout
    val finalLayout: VkImageLayout

    private constructor(
        format: VkFormat,
        samples: VkSampleCountFlagBits,
        loadOp: VkAttachmentLoadOp,
        storeOp: VkAttachmentStoreOp,
        stencilLoadOp: VkAttachmentLoadOp,
        stencilStoreOp: VkAttachmentStoreOp,
        initialLayout: VkImageLayout,
        finalLayout: VkImageLayout
    ) {
        this.format = format
        this.samples = samples
        this.loadOp = loadOp
        this.storeOp = storeOp
        this.stencilLoadOp = stencilLoadOp
        this.stencilStoreOp = stencilStoreOp
        this.initialLayout = initialLayout
        this.finalLayout = finalLayout
    }

    @VkAttachmentDescriptionDslMarker
    companion object Builder {

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

        fun build(): VkAttachmentDescription = VkAttachmentDescription(
            format,
            samples,
            loadOp,
            storeOp,
            stencilLoadOp,
            stencilStoreOp,
            initialLayout,
            finalLayout
        )

        operator fun invoke(): Builder {
            return this
        }
    }
}

fun attachmentDescription(lambda: VkAttachmentDescription.Builder.() -> Unit)
        = VkAttachmentDescription.Builder()
        .apply(lambda)
        .build()

@DslMarker
annotation class VkAttachmentDescriptionDslMarker
