package nl.jamiecraane.knowit.coroutines.samples

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    println("Hello")
    GlobalScope.launch {
        // Execution is paused here
        delay(100)
//        and resumed here (delay is a suspending function)
        println("World")
    }

    Thread.sleep(110)
}
