package org.aquila3d.core

fun <T : Any> JsClass<T>.createInstance(vararg args: dynamic): T {
    @Suppress("UNUSED_VARIABLE")
    val ctor = this

    @Suppress("UNUSED_VARIABLE")
    val argsArray = (listOf(null) + args).toTypedArray()

    //language=JavaScript 1.6
    return js("new (Function.prototype.bind.apply(ctor, argsArray))").unsafeCast<T>()
}