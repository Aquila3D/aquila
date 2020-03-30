package org.aquila3d.core.vulkan.command

/**
 * Specifies a command buffer level.
 */
enum class VkCommandBufferLevel(val value: Int) {

    /**
     * A primary command buffer.
     */
    VK_COMMAND_BUFFER_LEVEL_PRIMARY(0),

    /**
     * A secondary command buffer.
     */
    VK_COMMAND_BUFFER_LEVEL_SECONDARY(1)
}
