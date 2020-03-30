package org.aquila3d.core.memory

import org.lwjgl.PointerBuffer
import org.lwjgl.system.MemoryUtil.memFree
import org.lwjgl.system.NativeResource
import java.nio.Buffer
import java.nio.LongBuffer

fun <T : NativeResource> T.use(func: (T) -> Unit) {
    try {
        func(this)
    } finally {
        free()
    }
}

fun <T : Buffer> T.use(func: (T) -> Unit) {
    try {
        func(this)
    } finally {
        memFree(this)
    }
}

fun PointerBuffer.use(func: (PointerBuffer) -> Long): Long {
    try {
        return func(this)
    } finally {
        memFree(this)
    }
}

fun LongBuffer.useAndGet(func: (LongBuffer) -> Long): Long {
    try {
        return func(this)
    } finally {
        memFree(this)
    }
}
