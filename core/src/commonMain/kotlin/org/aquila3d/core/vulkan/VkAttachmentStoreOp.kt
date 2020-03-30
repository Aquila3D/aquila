package org.aquila3d.core.vulkan

/**
 * Specify how contents of an attachment are treated at the end of a subpass.
 */
enum class VkAttachmentStoreOp(val value: Int) {

    /**
     * Specifies the contents generated during the render pass and within the render area are written to memory.
     * For attachments with a depth/stencil format, this uses the access type
     * [VK_ACCESS_DEPTH_STENCIL_ATTACHMENT_WRITE_BIT]. For attachments with a color format, this uses the access type
     * [VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT].
     */
    VK_ATTACHMENT_STORE_OP_STORE(0),

    /**
     * Specifies the contents within the render area are not needed after rendering, and may be discarded; the contents
     * of the attachment will be undefined inside the render area. For attachments with a depth/stencil format, this
     * uses the access type [VK_ACCESS_DEPTH_STENCIL_ATTACHMENT_WRITE_BIT]. For attachments with a color format, this
     * uses the access type [VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT].
     */
    VK_ATTACHMENT_STORE_OP_DONT_CARE(1)
}
