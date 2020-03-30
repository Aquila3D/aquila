package org.aquila3d.core.vulkan

/**
 * Specify how contents of an attachment are treated at the beginning of a subpass.
 */
enum class VkAttachmentLoadOp(val value: Int) {

    /**
     * Specifies that the previous contents of the image within the render area will be preserved. For attachments with
     * a depth/stencil format, this uses the access type [VK_ACCESS_DEPTH_STENCIL_ATTACHMENT_READ_BIT]. For attachments
     * with a color format, this uses the access type [VK_ACCESS_COLOR_ATTACHMENT_READ_BIT].
     */
    VK_ATTACHMENT_LOAD_OP_LOAD(0),

    /**
     * Specifies that the contents within the render area will be cleared to a uniform value, which is specified when a
     * render pass instance is begun. For attachments with a depth/stencil format, this uses the access type
     * [VK_ACCESS_DEPTH_STENCIL_ATTACHMENT_WRITE_BIT]. For attachments with a color format, this uses the access type
     * [VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT].
     */
    VK_ATTACHMENT_LOAD_OP_CLEAR(1),

    /**
     * Specifies that the previous contents within the area need not be preserved; the contents of the attachment will
     * be undefined inside the render area. For attachments with a depth/stencil format, this uses the access type
     * [VK_ACCESS_DEPTH_STENCIL_ATTACHMENT_WRITE_BIT]. For attachments with a color format, this uses the access type
     * [VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT].
     */
    VK_ATTACHMENT_LOAD_OP_DONT_CARE(2)
}
