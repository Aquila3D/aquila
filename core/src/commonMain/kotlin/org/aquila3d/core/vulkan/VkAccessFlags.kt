package org.aquila3d.core.vulkan

val VK_ACCESS_UNSPECIFIED = VkAccessFlags(0)
val VK_ACCESS_INDIRECT_COMMAND_READ_BIT = VkAccessFlags(0x00000001)
val VK_ACCESS_INDEX_READ_BIT = VkAccessFlags(0x00000002)
val VK_ACCESS_VERTEX_ATTRIBUTE_READ_BIT = VkAccessFlags(0x00000004)
val VK_ACCESS_UNIFORM_READ_BIT = VkAccessFlags(0x00000008)
val VK_ACCESS_INPUT_ATTACHMENT_READ_BIT = VkAccessFlags(0x00000010)
val VK_ACCESS_SHADER_READ_BIT = VkAccessFlags(0x00000020)
val VK_ACCESS_SHADER_WRITE_BIT = VkAccessFlags(0x00000040)
val VK_ACCESS_COLOR_ATTACHMENT_READ_BIT = VkAccessFlags(0x00000080)
val VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT = VkAccessFlags(0x00000100)
val VK_ACCESS_DEPTH_STENCIL_ATTACHMENT_READ_BIT = VkAccessFlags(0x00000200)
val VK_ACCESS_DEPTH_STENCIL_ATTACHMENT_WRITE_BIT = VkAccessFlags(0x00000400)
val VK_ACCESS_TRANSFER_READ_BIT = VkAccessFlags(0x00000800)
val VK_ACCESS_TRANSFER_WRITE_BIT = VkAccessFlags(0x00001000)
val VK_ACCESS_HOST_READ_BIT = VkAccessFlags(0x00002000)
val VK_ACCESS_HOST_WRITE_BIT = VkAccessFlags(0x00004000)
val VK_ACCESS_MEMORY_READ_BIT = VkAccessFlags(0x00008000)
val VK_ACCESS_MEMORY_WRITE_BIT = VkAccessFlags(0x00010000)
val VK_ACCESS_TRANSFORM_FEEDBACK_WRITE_BIT_EXT = VkAccessFlags(0x02000000)
val VK_ACCESS_TRANSFORM_FEEDBACK_COUNTER_READ_BIT_EXT = VkAccessFlags(0x04000000)
val VK_ACCESS_TRANSFORM_FEEDBACK_COUNTER_WRITE_BIT_EXT = VkAccessFlags(0x08000000)
val VK_ACCESS_CONDITIONAL_RENDERING_READ_BIT_EXT = VkAccessFlags(0x00100000)
val VK_ACCESS_COLOR_ATTACHMENT_READ_NONCOHERENT_BIT_EXT = VkAccessFlags(0x00080000)
val VK_ACCESS_ACCELERATION_STRUCTURE_READ_BIT_KHR = VkAccessFlags(0x00200000)
val VK_ACCESS_ACCELERATION_STRUCTURE_WRITE_BIT_KHR = VkAccessFlags(0x00400000)
val VK_ACCESS_SHADING_RATE_IMAGE_READ_BIT_NV = VkAccessFlags(0x00800000)
val VK_ACCESS_FRAGMENT_DENSITY_MAP_READ_BIT_EXT = VkAccessFlags(0x01000000)
val VK_ACCESS_COMMAND_PREPROCESS_READ_BIT_NV = VkAccessFlags(0x00020000)
val VK_ACCESS_COMMAND_PREPROCESS_WRITE_BIT_NV = VkAccessFlags(0x00040000)
val VK_ACCESS_ACCELERATION_STRUCTURE_READ_BIT_NV = VkAccessFlags(VK_ACCESS_ACCELERATION_STRUCTURE_READ_BIT_KHR.flags)
val VK_ACCESS_ACCELERATION_STRUCTURE_WRITE_BIT_NV = VkAccessFlags(VK_ACCESS_ACCELERATION_STRUCTURE_WRITE_BIT_KHR.flags)

inline class VkAccessFlags(internal val flags: Int) {
    fun toInt(): Int = flags

    infix fun or(other: VkAccessFlags): VkAccessFlags {
        return VkAccessFlags(flags or other.flags)
    }
}
