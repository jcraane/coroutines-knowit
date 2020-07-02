package nl.jamiecraane.knowit.coroutines.samples

import kotlinx.serialization.stringFromUtf8Bytes

fun main(args: Array<String>) {
    val value = 10
    println(value)
    doSomething(value, object : Continuation<Int> {
        override fun resume(result: Int) {
            println(result)
        }
    })
}

private fun doSomething(value: Int, continuation: Continuation<Int>) {
    continuation.resume(value + 10)
}

private interface Continuation<T> {
    fun resume(result: T)
}
