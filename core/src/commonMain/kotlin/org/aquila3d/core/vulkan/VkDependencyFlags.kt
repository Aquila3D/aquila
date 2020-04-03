package org.aquila3d.core.vulkan

val VK_DEPENDENCY_UNSPECIFIED = VkDependencyFlags(0)
val VK_DEPENDENCY_BY_REGION_BIT = VkDependencyFlags(0x00000001)
val VK_DEPENDENCY_DEVICE_GROUP_BIT = VkDependencyFlags(0x00000004)
val VK_DEPENDENCY_VIEW_LOCAL_BIT = VkDependencyFlags(0x00000002)
val VK_DEPENDENCY_VIEW_LOCAL_BIT_KHR = VkDependencyFlags(VK_DEPENDENCY_VIEW_LOCAL_BIT.flags)
val VK_DEPENDENCY_DEVICE_GROUP_BIT_KHR = VkDependencyFlags(VK_DEPENDENCY_DEVICE_GROUP_BIT.flags)

inline class VkDependencyFlags(internal val flags: Int) {
    fun toInt(): Int = flags

    infix fun or(other: VkDependencyFlags): VkDependencyFlags {
        return VkDependencyFlags(flags or other.flags)
    }
}
