package org.aquila3d.core.vulkan

/**
 * Specify rate at which vertex attributes are pulled from buffers.
 */
enum class VkVertexInputRate(val value: Int) {

    /**
     * Specifies that vertex attribute addressing is a function of the vertex index.
     */
    VK_VERTEX_INPUT_RATE_VERTEX(0),

    /**
     * Specifies that vertex attribute addressing is a function of the instance index.
     */
    VK_VERTEX_INPUT_RATE_INSTANCE(1)
}
