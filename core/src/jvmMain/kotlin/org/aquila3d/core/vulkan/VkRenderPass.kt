package org.aquila3d.core.vulkan

import org.aquila3d.core.memory.useAndGet
import org.lwjgl.system.MemoryUtil.memAllocLong
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VkRenderPassCreateInfo


actual class VkRenderPass actual constructor(renderPass: VkRenderPassImpl) {

    val handle: Long

    init {
        val attachmentCount = renderPass.attachments.size
        val nativeAttachment = org.lwjgl.vulkan.VkAttachmentDescription.calloc(attachmentCount)
        val nativeAttachmentReference = org.lwjgl.vulkan.VkAttachmentReference.calloc(attachmentCount)
        for (i in 0 until attachmentCount) {
            val attachment = renderPass.attachments[i]
            val reference = renderPass.references[i]
            nativeAttachment.position(i)
            nativeAttachmentReference.position(i)
            nativeAttachment.flags(attachment.flags).format(attachment.format.value).samples(attachment.samples.value)
                .loadOp(attachment.loadOp.value).storeOp(attachment.storeOp.value)
                .stencilLoadOp(attachment.stencilLoadOp.value).stencilStoreOp(attachment.stencilStoreOp.value)
                .initialLayout(attachment.initialLayout.value).finalLayout(attachment.finalLayout.value)
            nativeAttachmentReference.attachment(reference.attachment).layout(reference.layout.value)
        }
        val subpassCount = renderPass.subpasses.size
        val subpasses = org.lwjgl.vulkan.VkSubpassDescription.calloc(subpassCount)
        var colorReferences: org.lwjgl.vulkan.VkAttachmentReference.Buffer? = null
        for (i in 0 until subpassCount) {
            val subpass = renderPass.subpasses[i]
            subpasses.position(i)
            subpasses.pipelineBindPoint(subpass.pipelineBindPoint.value)

            // Handle color subpasses
            subpasses.colorAttachmentCount(subpass.colorAttachments.size)
            val colorAttachments = subpass.colorAttachments
            val colorReferences = org.lwjgl.vulkan.VkAttachmentReference.calloc(colorAttachments.size)
            for (i in 0 until colorAttachments.size) {
                val reference = colorAttachments[i]
                colorReferences.position(i)
                colorReferences.layout(reference.layout.value).attachment(reference.attachment)
            }
            subpasses.pColorAttachments(colorReferences)

            //TODO: Rest of the subpass struct
        }

        // TODO: Subpass dependencies
        handle = memAllocLong(1).useAndGet {
            val renderPassInfo = VkRenderPassCreateInfo.calloc()
                .sType(VK_STRUCTURE_TYPE_RENDER_PASS_CREATE_INFO)
                .pAttachments(nativeAttachment)
                .pSubpasses(subpasses)
            val err = vkCreateRenderPass(renderPass.device.handle, renderPassInfo, null, it)
            renderPassInfo.free()
            nativeAttachmentReference.free()
            colorReferences?.free()
            subpasses.free()
            nativeAttachment.free()
            if (err != VK_SUCCESS) {
                throw AssertionError("Failed to create clear render pass: ${VkResult(err)}")
            }
            it[0]
        }
    }
}
