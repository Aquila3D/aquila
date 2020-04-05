package org.aquila3d.core.renderable

import org.aquila3d.core.vulkan.VkVertexInputBindingDescription
import org.aquila3d.core.vulkan.VkVertexInputRate

open class Renderable(val vertexData: FloatArray, val colorData: FloatArray? = null) {

    val interleavedData: FloatArray

    val bindingDescription: VkVertexInputBindingDescription

    init {
        val vertexCount = vertexData.size / 3
        val vertexSize = 3 + (if (colorData != null) 4 else 0)
        interleavedData = FloatArray(vertexData.size + (colorData?.size ?: 0))
        var interleaveIndex = 0
        for (i in 0 until vertexCount) {
            // Position data
            val basePosition = i * 3
            interleavedData[interleaveIndex++] = vertexData[basePosition]
            interleavedData[interleaveIndex++] = vertexData[basePosition + 1]
            interleavedData[interleaveIndex++] = vertexData[basePosition + 2]

            colorData?.apply {
                val baseColor = i * 4
                interleavedData[interleaveIndex++] = colorData[baseColor]
                interleavedData[interleaveIndex++] = colorData[baseColor + 1]
                interleavedData[interleaveIndex++] = colorData[baseColor + 2]
                interleavedData[interleaveIndex++] = colorData[baseColor + 3]
            }
        }

        // TODO: Selecting the binding
        bindingDescription = VkVertexInputBindingDescription(
            0, 4 * (if (colorData != null) 4 else 0),
            VkVertexInputRate.VK_VERTEX_INPUT_RATE_VERTEX
        )
    }


}
