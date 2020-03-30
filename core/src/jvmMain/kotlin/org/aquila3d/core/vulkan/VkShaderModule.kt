package org.aquila3d.core.vulkan

actual class VkShaderModule(val handle: Long, val stage: VkShaderStageFlagBits) {
    actual fun destroy() {
    }
}
