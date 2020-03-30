package org.aquila3d.core.vulkan

/**
 * Bitmask specifying sample counts supported for an image used for storage operations.
 */
enum class VkSampleCountFlagBits(val value: Int) {

    /**
     * 1 sample per pixel.
     */
    VK_SAMPLE_COUNT_1_BIT(0x00000001),

    /**
     * 2 samples per pixel.
     */
    VK_SAMPLE_COUNT_2_BIT(0x00000002),

    /**
     * 4 samples per pixel.
     */
    VK_SAMPLE_COUNT_4_BIT(0x00000004),

    /**
     * 8 samples per pixel.
     */
    VK_SAMPLE_COUNT_8_BIT(0x00000008),

    /**
     * 16 samples per pixel.
     */
    VK_SAMPLE_COUNT_16_BIT(0x00000010),

    /**
     * 32 samples per pixel.
     */
    VK_SAMPLE_COUNT_32_BIT(0x00000020),

    /**
     * 64 samples per pixel.
     */
    VK_SAMPLE_COUNT_64_BIT(0x00000040)
}
