package org.aquila3d.core.shader

import org.aquila3d.core.vulkan.shader.VkShaderModule
import org.lwjgl.system.MemoryUtil.memUTF8
import org.lwjgl.vulkan.VK10.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO
import org.lwjgl.vulkan.VkPipelineShaderStageCreateInfo

actual class PipelineShaderStage actual constructor(
    val shaders: List<VkShaderModule>
) {
    val handles = shaders.stream().forEach {
        VkPipelineShaderStageCreateInfo.calloc()
            .sType(VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO)
            .stage(it.stage.value)
            .module(it.handle)
            .pName(memUTF8("main"))
    }

    actual fun destroy() {
    }
}
