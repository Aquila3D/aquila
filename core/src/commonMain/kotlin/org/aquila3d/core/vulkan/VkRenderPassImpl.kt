package org.aquila3d.core.vulkan

import org.aquila3d.core.vulkan.device.VkDevice

expect class VkRenderPass(renderPass: VkRenderPassImpl) {

}

class VkRenderPassImpl {

    val device: VkDevice
    val flags: Int
    val attachments: List<VkAttachmentDescription>
    val references: List<VkAttachmentReference>
    val subpasses: List<VkSubpassDescription>
    val subpassDependencies: List<VkSubpassDependency>

    private constructor(
        device: VkDevice,
        flags: Int,
        attachments: List<VkAttachmentDescription>,
        references: List<VkAttachmentReference>,
        subpasses: List<VkSubpassDescription>,
        subpassDependencies: List<VkSubpassDependency>
    ) {
        this.device = device
        this.flags = flags
        this.attachments = attachments
        this.references = references
        this.subpasses = subpasses
        this.subpassDependencies = subpassDependencies
    }

    @VkRenderPassDslMarker
    companion object Builder {

        var flags: Int = 0
        var attachments: MutableList<VkAttachmentDescription> = mutableListOf()
        var references: MutableList<VkAttachmentReference> = mutableListOf()
        var subpasses: MutableList<VkSubpassDescription> = mutableListOf()
        var subpassDependencies: MutableList<VkSubpassDependency> = mutableListOf()

        inline fun flags(flags: () -> Int) {
            this.flags = flags()
        }

        inline fun attachments(attachments: () -> Pair<VkAttachmentDescription, VkAttachmentReference>) {
            val attachment = attachments()
            this.attachments.add(attachment.first)
            this.references.add(attachment.second)
        }

        inline fun inputAttachments(subpasses: () -> VkSubpassDescription) {
            this.subpasses.add(subpasses())
        }

        inline fun subpassDependencies(subpassDependencies: () -> VkSubpassDependency) {
            this.subpassDependencies.add(subpassDependencies())
        }

        fun build(device: VkDevice): VkRenderPassImpl = VkRenderPassImpl(
            device,
            flags,
            attachments,
            references,
            subpasses,
            subpassDependencies
        )

        operator fun invoke(): Builder {
            return this
        }
    }
}

fun renderPass(device: VkDevice, lambda: VkRenderPassImpl.Builder.() -> Unit) = VkRenderPassImpl.Builder()
    .apply(lambda)
    .build(device)

@DslMarker
annotation class VkRenderPassDslMarker
