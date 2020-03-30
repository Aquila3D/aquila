package org.aquila3d.core.io

import org.lwjgl.BufferUtils
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.net.URL
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import kotlin.math.max

private fun resizeBuffer(buffer: ByteBuffer, newCapacity: Int): ByteBuffer {
    val newBuffer: ByteBuffer = BufferUtils.createByteBuffer(newCapacity)
    buffer.flip()
    newBuffer.put(buffer)
    return newBuffer
}

private fun fileResourceToByteBuffer(file: File, bufferSize: Int): ByteBuffer {
    FileInputStream(file).use { inputStream ->
        inputStream.channel.use { channel ->
            return channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size())
        }
    }
}

private fun streamResourceToByteBuffer(url: URL, bufferSize: Int): ByteBuffer {
    var buffer = BufferUtils.createByteBuffer(bufferSize)
    val sourceStream = url.openStream() ?: throw FileNotFoundException(url.toString())
    sourceStream.use { source ->
        val buf = ByteArray(8192)
        while (true) {
            val bytes = source.read(buf, 0, buf.size)
            if (bytes == -1) break
            if (buffer.remaining() < bytes) buffer = resizeBuffer(
                buffer,
                max(buffer.capacity() * 2, buffer.capacity() - buffer.remaining() + bytes)
            )
            buffer.put(buf, 0, bytes)
        }
        buffer.flip()
    }
    return buffer
}

@Throws(IOException::class)
fun ioResourceToByteBuffer(resource: String, bufferSize: Int): ByteBuffer {
    val url = Thread.currentThread().contextClassLoader.getResource(resource)
        ?: throw IOException("Classpath resource not found: $resource")
    val file = File(url.file)
    return if (file.isFile) {
        fileResourceToByteBuffer(file, bufferSize)
    } else {
        streamResourceToByteBuffer(url, bufferSize)
    }
}
