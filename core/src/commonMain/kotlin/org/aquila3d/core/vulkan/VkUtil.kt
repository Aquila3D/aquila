package org.aquila3d.core.vulkan

/**
 * Translates a Vulkan `VkResult` value to a String describing the result.
 *
 * @param result
 * the `VkResult` value
 *
 * @return the result description
 */
expect fun translateVulkanResult(result: Int): String