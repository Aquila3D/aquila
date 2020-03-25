package org.aquila3d.core.vulkan

fun makeVulkanVersion(major: Int, minor: Int, patch: Int): Int {
    return ((major shl 22) or (minor shl 12) or patch)
}