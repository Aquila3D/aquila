package org.aquila3d.core.vulkan.memory

import org.aquila3d.core.vulkan.memory.VkMemoryHeap
import org.aquila3d.core.vulkan.memory.VkMemoryType

data class VkPhysicalDeviceMemoryProperties(
    private val types: List<VkMemoryType>,
    private val heaps: List<VkMemoryHeap>
) {

    fun memoryTypeCount() = types.size

    fun memoryTypes(index: Int) = types[index]

    fun memoryHeapCount() = heaps.size

    fun memoryHeaps(index: Int) = heaps[index]
}
