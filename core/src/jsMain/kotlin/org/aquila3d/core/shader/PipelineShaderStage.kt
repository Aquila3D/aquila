package org.aquila3d.core.shader

import org.aquila3d.core.vulkan.VkShaderModule
import org.aquila3d.core.vulkan.VkShaderStageFlagBits

actual class PipelineShaderStage actual constructor(
    stage: VkShaderStageFlagBits,
    shader: VkShaderModule
) {
    actual fun destroy() {
    }
}
