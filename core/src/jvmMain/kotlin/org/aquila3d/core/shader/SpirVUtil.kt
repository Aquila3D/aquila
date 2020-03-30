package org.aquila3d.core.shader

import org.aquila3d.core.io.ioResourceToByteBuffer
import org.aquila3d.core.vulkan.VkDevice
import org.aquila3d.core.vulkan.VkResult
import org.aquila3d.core.vulkan.VkShaderModule
import org.aquila3d.core.vulkan.VkShaderStageFlagBits
import org.lwjgl.BufferUtils.createByteBuffer
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil.*
import org.lwjgl.util.shaderc.Shaderc.*
import org.lwjgl.util.shaderc.ShadercIncludeResolve
import org.lwjgl.util.shaderc.ShadercIncludeResult
import org.lwjgl.util.shaderc.ShadercIncludeResultRelease
import org.lwjgl.vulkan.NVRayTracing.*
import org.lwjgl.vulkan.VK10.*
import org.lwjgl.vulkan.VkPipelineShaderStageCreateInfo
import org.lwjgl.vulkan.VkShaderModuleCreateInfo
import org.lwjgl.vulkan.VkSpecializationInfo
import java.io.IOException
import java.nio.ByteBuffer


private fun vulkanStageToShadercKind(stage: Int): Int {
    return when (stage) {
        VK_SHADER_STAGE_VERTEX_BIT -> shaderc_vertex_shader
        VK_SHADER_STAGE_FRAGMENT_BIT -> shaderc_fragment_shader
        VK_SHADER_STAGE_RAYGEN_BIT_NV -> shaderc_raygen_shader
        VK_SHADER_STAGE_CLOSEST_HIT_BIT_NV -> shaderc_closesthit_shader
        VK_SHADER_STAGE_MISS_BIT_NV -> shaderc_miss_shader
        VK_SHADER_STAGE_ANY_HIT_BIT_NV -> shaderc_anyhit_shader
        else -> throw IllegalArgumentException("Stage: $stage")
    }
}

@Throws(IOException::class)
fun glslToSpirv(classPath: String, stage: Int): ByteBuffer {
    val src: ByteBuffer = ioResourceToByteBuffer(classPath, 1024)
    val compiler: Long = shaderc_compiler_initialize()
    val options: Long = shaderc_compile_options_initialize()
    var resolver: ShadercIncludeResolve
    var releaser: ShadercIncludeResultRelease
    shaderc_compile_options_set_optimization_level(options, shaderc_optimization_level_performance)
    shaderc_compile_options_set_include_callbacks(options, object : ShadercIncludeResolve() {
        override fun invoke(
            user_data: Long,
            requested_source: Long,
            type: Int,
            requesting_source: Long,
            include_depth: Long
        ): Long {
            val res: ShadercIncludeResult = ShadercIncludeResult.calloc()
            return try {
                val src =
                    classPath.substring(0, classPath.lastIndexOf('/')) + "/" + memUTF8(requested_source)
                res.content(ioResourceToByteBuffer(src, 1024))
                res.source_name(memUTF8(src))
                res.address()
            } catch (e: IOException) {
                throw AssertionError("Failed to resolve include: $src")
            }
        }
    }.also { resolver = it }, object : ShadercIncludeResultRelease() {
        override fun invoke(user_data: Long, include_result: Long) {
            val result: ShadercIncludeResult = ShadercIncludeResult.create(include_result)
            memFree(result.source_name())
            result.free()
        }
    }.also { releaser = it }, 0L)
    var res = 0L
    MemoryStack.stackPush().use { stack ->
        res = shaderc_compile_into_spv(
            compiler, src, vulkanStageToShadercKind(stage),
            stack.UTF8(classPath), stack.UTF8("main"), options
        )
        if (res == 0L) throw AssertionError("Internal error during compilation!")
    }
    if (shaderc_result_get_compilation_status(res) != shaderc_compilation_status_success) {
        throw AssertionError("Shader compilation failed: ${shaderc_result_get_error_message(res)}")
    }
    val size = shaderc_result_get_length(res)
    val resultBytes: ByteBuffer = createByteBuffer(size.toInt())
    resultBytes.put(shaderc_result_get_bytes(res))
    resultBytes.flip()
    shaderc_compiler_release(res)
    shaderc_compiler_release(compiler)
    releaser.free()
    resolver.free()
    return resultBytes
}

@Throws(IOException::class)
fun loadShader(
    info: VkPipelineShaderStageCreateInfo, specInfo: VkSpecializationInfo?,
    stack: MemoryStack, device: VkDevice, classPath: String?, stage: Int
) {
    val shaderCode = glslToSpirv(classPath!!, stage)
    val pShaderModule = stack.mallocLong(1)
    val shaderCreateInfo = VkShaderModuleCreateInfo.callocStack(stack)
        .sType(VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO)
        .pCode(shaderCode)
        .flags(0)
    val err = vkCreateShaderModule(device.handle, shaderCreateInfo, null, pShaderModule)
    if (err != VK_SUCCESS) {
        throw AssertionError("Failed to create shader module: ${VkResult(err)}")
    }
    info.stage(stage)
        .pSpecializationInfo(specInfo)
        .module(pShaderModule[0]).pName(stack.UTF8("main"))
}

@Throws(IOException::class)
fun loadShader(classPath: String, device: VkDevice, stage: Int): VkShaderModule {
    val shaderCode = glslToSpirv(classPath, stage)
    return loadShader(shaderCode, device, stage)
}

@Throws(IOException::class)
fun loadShader(byteCode: ByteBuffer, device: VkDevice, stage: Int): VkShaderModule {
    val err: Int
    val moduleCreateInfo = VkShaderModuleCreateInfo.calloc()
        .sType(VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO)
        .pCode(byteCode)
    val pShaderModule = memAllocLong(1)
    err = vkCreateShaderModule(device.handle, moduleCreateInfo, null, pShaderModule)
    val shaderModule = pShaderModule[0]
    memFree(pShaderModule)
    if (err != VK_SUCCESS) {
        throw AssertionError("Failed to create shader module: ${VkResult(err)}")
    }
    return VkShaderModule(shaderModule, VkShaderStageFlagBits.from(stage))
}

