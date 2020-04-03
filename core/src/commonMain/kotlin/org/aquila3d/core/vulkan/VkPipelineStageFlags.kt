package org.aquila3d.core.vulkan

val VK_PIPELINE_STAGE_UNSPECIFIED = VkPipelineStageFlags(0)
val VK_PIPELINE_STAGE_TOP_OF_PIPE_BIT = VkPipelineStageFlags(0x00000001)
val VK_PIPELINE_STAGE_DRAW_INDIRECT_BIT = VkPipelineStageFlags(0x00000002)
val VK_PIPELINE_STAGE_VERTEX_INPUT_BIT = VkPipelineStageFlags(0x00000004)
val VK_PIPELINE_STAGE_VERTEX_SHADER_BIT = VkPipelineStageFlags(0x00000008)
val VK_PIPELINE_STAGE_TESSELLATION_CONTROL_SHADER_BIT = VkPipelineStageFlags(0x00000010)
val VK_PIPELINE_STAGE_TESSELLATION_EVALUATION_SHADER_BIT = VkPipelineStageFlags(0x00000020)
val VK_PIPELINE_STAGE_GEOMETRY_SHADER_BIT = VkPipelineStageFlags(0x00000040)
val VK_PIPELINE_STAGE_FRAGMENT_SHADER_BIT = VkPipelineStageFlags(0x00000080)
val VK_PIPELINE_STAGE_EARLY_FRAGMENT_TESTS_BIT = VkPipelineStageFlags(0x00000100)
val VK_PIPELINE_STAGE_LATE_FRAGMENT_TESTS_BIT = VkPipelineStageFlags(0x00000200)
val VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT = VkPipelineStageFlags(0x00000400)
val VK_PIPELINE_STAGE_COMPUTE_SHADER_BIT = VkPipelineStageFlags(0x00000800)
val VK_PIPELINE_STAGE_TRANSFER_BIT = VkPipelineStageFlags(0x00001000)
val VK_PIPELINE_STAGE_BOTTOM_OF_PIPE_BIT = VkPipelineStageFlags(0x00002000)
val VK_PIPELINE_STAGE_HOST_BIT = VkPipelineStageFlags(0x00004000)
val VK_PIPELINE_STAGE_ALL_GRAPHICS_BIT = VkPipelineStageFlags(0x00008000)
val VK_PIPELINE_STAGE_ALL_COMMANDS_BIT = VkPipelineStageFlags(0x00010000)
val VK_PIPELINE_STAGE_TRANSFORM_FEEDBACK_BIT_EXT = VkPipelineStageFlags(0x01000000)
val VK_PIPELINE_STAGE_CONDITIONAL_RENDERING_BIT_EXT = VkPipelineStageFlags(0x00040000)
val VK_PIPELINE_STAGE_RAY_TRACING_SHADER_BIT_KHR = VkPipelineStageFlags(0x00200000)
val VK_PIPELINE_STAGE_ACCELERATION_STRUCTURE_BUILD_BIT_KHR = VkPipelineStageFlags(0x02000000)
val VK_PIPELINE_STAGE_SHADING_RATE_IMAGE_BIT_NV = VkPipelineStageFlags(0x00400000)
val VK_PIPELINE_STAGE_TASK_SHADER_BIT_NV = VkPipelineStageFlags(0x00080000)
val VK_PIPELINE_STAGE_MESH_SHADER_BIT_NV = VkPipelineStageFlags(0x00100000)
val VK_PIPELINE_STAGE_FRAGMENT_DENSITY_PROCESS_BIT_EXT = VkPipelineStageFlags(0x00800000)
val VK_PIPELINE_STAGE_COMMAND_PREPROCESS_BIT_NV = VkPipelineStageFlags(0x00020000)
val VK_PIPELINE_STAGE_RAY_TRACING_SHADER_BIT_NV =
    VkPipelineStageFlags(VK_PIPELINE_STAGE_RAY_TRACING_SHADER_BIT_KHR.flags)
val VK_PIPELINE_STAGE_ACCELERATION_STRUCTURE_BUILD_BIT_NV =
    VkPipelineStageFlags(VK_PIPELINE_STAGE_ACCELERATION_STRUCTURE_BUILD_BIT_KHR.flags)

inline class VkPipelineStageFlags(internal val flags: Int) {
    fun toInt(): Int = flags

    infix fun or(other: VkPipelineStageFlags): VkPipelineStageFlags {
        return VkPipelineStageFlags(flags or other.flags)
    }
}