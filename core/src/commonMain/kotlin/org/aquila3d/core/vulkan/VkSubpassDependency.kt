package org.aquila3d.core.vulkan

class VkSubpassDependency {

    val source: Int
    val destination: Int
    val sourceStageFlags: Int
    val destinationStageFlags: Int
    val sourceAccessMask: Int
    val destinationAccessMask: Int
    val dependencyFlags: Int

    private constructor(
        source: Int,
        destination: Int,
        sourceStageFlags: Int,
        destinationStageFlags: Int,
        sourceAccessMask: Int,
        destinationAccessMask: Int,
        dependencyFlags: Int
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
        var sourceStageFlags: Int = 0
        var destinationStageFlags: Int = 0
        var sourceAccessMask: Int = 0
        var destinationAccessMask: Int = 0
        var dependencyFlags: Int = 0

        inline fun source(source: () -> Int) {
            this.source = source()
        }

        inline fun destination(destination: () -> Int) {
            this.destination = destination()
        }

        inline fun sourceStageFlags(sourceStageFlags: () -> Int) {
            this.sourceStageFlags = sourceStageFlags()
        }

        inline fun destinationStageFlags(destinationStageFlags: () -> Int) {
            this.destinationStageFlags = destinationStageFlags()
        }

        inline fun sourceAccessMask(sourceAccessMask: () -> Int) {
            this.sourceAccessMask = sourceAccessMask()
        }

        inline fun destinationAccessMask(destinationAccessMask: () -> Int) {
            this.destinationAccessMask = destinationAccessMask()
        }

        inline fun dependencyFlags(dependencyFlags: () -> Int) {
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
