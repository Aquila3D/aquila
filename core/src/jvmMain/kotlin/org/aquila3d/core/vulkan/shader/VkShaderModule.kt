package org.aquila3d.core.vulkan.shader

import org.aquila3d.core.vulkan.shader.VkShaderStageFlagBits

actual class VkShaderModule(val handle: Long, val stage: VkShaderStageFlagBits) {
    actual fun destroy() {
    }
}
