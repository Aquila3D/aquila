package org.aquila3d.core.vulkan

class VkSubpassDependency {

    val source: Int
    val destination: Int
    val sourceStageFlags: VkPipelineStageFlags
    val destinationStageFlags: VkPipelineStageFlags
    val sourceAccessMask: VkAccessFlags
    val destinationAccessMask: VkAccessFlags
    val dependencyFlags: VkDependencyFlags

    private constructor(
        source: Int,
        destination: Int,
        sourceStageFlags: VkPipelineStageFlags,
        destinationStageFlags: VkPipelineStageFlags,
        sourceAccessMask: VkAccessFlags,
        destinationAccessMask: VkAccessFlags,
        dependencyFlags: VkDependencyFlags
    ) {
        this.source = source
        this.destination = destination
        this.sourceStageFlags = sourceStageFlags
        this.destinationStageFlags = destinationStageFlags
        this.sourceAccessMask = sourceAccessMask
        this.destinationAccessMask = destinationAccessMask
        this.dependencyFlags = dependencyFlags
    }

    @VkSubpassDependencyDslMarker
    companion object Builder {

        var source: Int = 0
        var destination: Int = 0
        var sourceStageFlags: VkPipelineStageFlags = VK_PIPELINE_STAGE_UNSPECIFIED
        var destinationStageFlags: VkPipelineStageFlags = VK_PIPELINE_STAGE_UNSPECIFIED
        var sourceAccessMask: VkAccessFlags = VK_ACCESS_UNSPECIFIED
        var destinationAccessMask: VkAccessFlags = VK_ACCESS_UNSPECIFIED
        var dependencyFlags: VkDependencyFlags = VK_DEPENDENCY_UNSPECIFIED

        inline fun source(source: () -> Int) {
            this.source = source()
        }

        inline fun destination(destination: () -> Int) {
            this.destination = destination()
        }

        inline fun sourceStageFlags(sourceStageFlags: () -> VkPipelineStageFlags) {
            this.sourceStageFlags = sourceStageFlags()
        }

        inline fun destinationStageFlags(destinationStageFlags: () -> VkPipelineStageFlags) {
            this.destinationStageFlags = destinationStageFlags()
        }

        inline fun sourceAccessMask(sourceAccessMask: () -> VkAccessFlags) {
            this.sourceAccessMask = sourceAccessMask()
        }

        inline fun destinationAccessMask(destinationAccessMask: () -> VkAccessFlags) {
            this.destinationAccessMask = destinationAccessMask()
        }

        inline fun dependencyFlags(dependencyFlags: () -> VkDependencyFlags) {
            this.dependencyFlags = dependencyFlags()
        }

        fun build(): VkSubpassDependency = VkSubpassDependency(
            source,
            destination,
            sourceStageFlags,
            destinationStageFlags,
            sourceAccessMask,
            destinationAccessMask,
            dependencyFlags
        )

        operator fun invoke(): Builder {
            return this
        }
    }
}

fun subpassDependency(lambda: VkSubpassDependency.Builder.() -> Unit) = VkSubpassDependency.Builder()
    .apply(lambda)
    .build()

@DslMarker
annotation class VkSubpassDependencyDslMarker
