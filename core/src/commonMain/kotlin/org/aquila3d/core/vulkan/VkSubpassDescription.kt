package org.aquila3d.core.vulkan

class VkSubpassDescription {

    val flags: Int
    val pipelineBindPoint: VkPipelineBindpoint
    val inputAttachments: List<VkAttachmentReference>
    val colorAttachments: List<VkAttachmentReference>
    val resolveAttachments: List<VkAttachmentReference>
    val depthStencilAttachments: List<VkAttachmentReference>
    val preserveAttachments: List<VkAttachmentReference>

    private constructor(
        flags: Int,
        pipelineBindPoint: VkPipelineBindpoint,
        inputAttachments: List<VkAttachmentReference>,
        colorAttachments: List<VkAttachmentReference>,
        resolveAttachments: List<VkAttachmentReference>,
        depthStencilAttachments: List<VkAttachmentReference>,
        preserveAttachments: List<VkAttachmentReference>
    ) {
        this.flags = flags
        this.pipelineBindPoint = pipelineBindPoint
        this.inputAttachments = inputAttachments
        this.colorAttachments = colorAttachments
        this.resolveAttachments = resolveAttachments
        this.depthStencilAttachments = depthStencilAttachments
        this.preserveAttachments = preserveAttachments
    }

    @VkSubpassDescriptionDslMarker
    companion object Builder {

        var flags: Int = 0
        var pipelineBindPoint: VkPipelineBindpoint = VkPipelineBindpoint.VK_PIPELINE_BIND_POINT_GRAPHICS
        var inputAttachments: MutableList<VkAttachmentReference> = mutableListOf()
        var colorAttachments: MutableList<VkAttachmentReference> = mutableListOf()
        var resolveAttachments: MutableList<VkAttachmentReference> = mutableListOf()
        var depthStencilAttachments: MutableList<VkAttachmentReference> = mutableListOf()
        var preserveAttachments: MutableList<VkAttachmentReference> = mutableListOf()

        inline fun flags(flags: () -> Int) {
            this.flags = flags()
        }

        inline fun pipelineBindPoint(pipelineBindPoint: () -> VkPipelineBindpoint) {
            this.pipelineBindPoint = pipelineBindPoint()
        }

        inline fun inputAttachments(inputAttachments: () -> VkAttachmentReference) {
            this.inputAttachments.add(inputAttachments())
        }

        inline fun colorAttachments(colorAttachments: () -> VkAttachmentReference) {
            this.colorAttachments.add(colorAttachments())
        }

        inline fun resolveAttachments(resolveAttachments: () -> VkAttachmentReference) {
            this.resolveAttachments.add(resolveAttachments())
        }

        inline fun depthStencilAttachments(depthStencilAttachments: () -> VkAttachmentReference) {
            this.depthStencilAttachments.add(depthStencilAttachments())
        }

        inline fun preserveAttachments(preserveAttachments: () -> VkAttachmentReference) {
            this.preserveAttachments.add(preserveAttachments())
        }

        fun build(): VkSubpassDescription = VkSubpassDescription(
            flags,
            pipelineBindPoint,
            inputAttachments,
            colorAttachments,
            resolveAttachments,
            depthStencilAttachments,
            preserveAttachments
        )

        operator fun invoke(): Builder {
            return this
        }
    }
}

fun subpassDescription(lambda: VkSubpassDescription.Builder.() -> Unit) = VkSubpassDescription.Builder()
    .apply(lambda)
    .build()

@DslMarker
annotation class VkSubpassDescriptionDslMarker
