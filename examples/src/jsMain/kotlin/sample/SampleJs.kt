package sample

actual class Sample {
    actual fun checkMe() = 12
}

actual object Platform {
    actual val name: String = "JS"
}

fun main(args: Array<String>) {
    println("Hello JavaScript!")
}