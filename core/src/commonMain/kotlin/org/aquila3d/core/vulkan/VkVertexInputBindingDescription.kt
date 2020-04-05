package org.aquila3d.core.vulkan

/**
 * Structure specifying vertex input binding description.
 *
 * @param [binding] is the binding number that this structure describes.
 * @param [stride] is the distance in bytes between two consecutive elements within the buffer.
 * @param [inputRate] is a [VkVertexInputRate] value specifying whether vertex attribute addressing is a function of the
 * vertex index or of the instance index.
 */
class VkVertexInputBindingDescription(val binding: Int, val stride: Int, val inputRate: VkVertexInputRate) {
}
