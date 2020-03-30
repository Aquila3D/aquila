package org.aquila3d.core.shader

import org.aquila3d.core.vulkan.VkShaderModule

expect class PipelineShaderStage(shaders: List<VkShaderModule>) {
    fun destroy()
}
