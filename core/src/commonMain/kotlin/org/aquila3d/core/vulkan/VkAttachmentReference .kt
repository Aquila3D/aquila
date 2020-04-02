package org.aquila3d.core.vulkan

class VkAttachmentReference {

    val attachment: Int
    val layout: VkImageLayout

    private constructor(
        attachment: Int,
        layout: VkImageLayout
    ) {
        this.attachment = attachment
        this.layout = layout
    }

    @VkAttachmentReferenceDslMarker
    companion object Builder {

        var attachment: Int = 0
        var layout: VkImageLayout = VkImageLayout.VK_IMAGE_LAYOUT_UNDEFINED

        inline fun attachment(attachment: () -> Int) {
            this.attachment = attachment()
        }

        inline fun layout(layout: () -> VkImageLayout) {
            this.layout = layout()
        }

        fun build(): VkAttachmentReference = VkAttachmentReference (
            attachment,
            layout
        )

        operator fun invoke(): Builder {
            return this
        }
    }
}

fun attachmentReference(lambda: VkAttachmentReference.Builder.() -> Unit)
        = VkAttachmentReference.Builder()
        .apply(lambda)
        .build()

@DslMarker
annotation class VkAttachmentReferenceDslMarker
