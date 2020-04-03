package org.aquila3d.core.vulkan.surface_khr

enum class VkPresentModeKHR(val value: Int) {
    VK_PRESENT_MODE_IMMEDIATE_KHR(0),
    VK_PRESENT_MODE_MAILBOX_KHR(1),
    VK_PRESENT_MODE_FIFO_KHR(2),
    VK_PRESENT_MODE_FIFO_RELAXED_KHR(3),
    VK_PRESENT_MODE_SHARED_DEMAND_REFRESH_KHR(1000111000),
    VK_PRESENT_MODE_SHARED_CONTINUOUS_REFRESH_KHR(1000111001);

    companion object {
        private val map = values().associateBy(VkPresentModeKHR::value)
        fun from(type: Int): VkPresentModeKHR {
            return map[type] ?: throw IllegalArgumentException("Unrecognized presentation mode code: $type")
        }
    }
}
