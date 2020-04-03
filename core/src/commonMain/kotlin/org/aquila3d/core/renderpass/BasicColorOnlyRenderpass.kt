package org.aquila3d.core.renderpass

import org.aquila3d.core.vulkan.*
import org.aquila3d.core.vulkan.device.VkDevice

fun basicColorOnlyRenderPass(device: VkDevice, format: VkFormat): VkRenderPass = VkRenderPass(
    renderPass(device) {
        attachments {
            Pair(attachmentDescription {
                format { format }
                samples { VkSampleCountFlagBits.VK_SAMPLE_COUNT_1_BIT }
                loadOp { VkAttachmentLoadOp.VK_ATTACHMENT_LOAD_OP_CLEAR }
                storeOp { VkAttachmentStoreOp.VK_ATTACHMENT_STORE_OP_STORE }
                stencilLoadOp { VkAttachmentLoadOp.VK_ATTACHMENT_LOAD_OP_DONT_CARE }
                stencilStoreOp { VkAttachmentStoreOp.VK_ATTACHMENT_STORE_OP_DONT_CARE }
                initialLayout { VkImageLayout.VK_IMAGE_LAYOUT_UNDEFINED }
                finalLayout { VkImageLayout.VK_IMAGE_LAYOUT_PRESENT_SRC_KHR }
            },
                attachmentReference {
                    attachment { 0 }
                    layout { VkImageLayout.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL }
                })
        }
        subpassDescription {
            pipelineBindPoint { VkPipelineBindpoint.VK_PIPELINE_BIND_POINT_GRAPHICS }
            colorAttachments { references[0] }
        }
    })
