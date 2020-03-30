package org.aquila3d.core.shader

import org.aquila3d.core.vulkan.shader.VkShaderModule

actual class PipelineShaderStage actual constructor(shaders: List<VkShaderModule>) {
    actual fun destroy() {
    }
}
