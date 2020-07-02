package nl.jamiecraane.knowit.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import nl.jamiecraane.knowit.shared.log

fun main() = runBlocking<Unit> {
//    flows are cold by default (channels are hot)
//    flows can be declared and only trigger when their collect method is called
//    thread communication and back pressure is implemented using coroutines

    val stringFlow = flow<String> {
        for (i in 0..50) {
            emit("Emit ${i.toString()}")
        }
    }

    // Nothing is in the output if values are not collected

    launch {
//        collect is a suspending function an must be called from a coroutine
//        each operator is a suspending function
        stringFlow
            .map { it.split(" ") }
            .map { it.last() }
            .onEach {
                log("delaying")
                delay(100)
            }
            .collect {
            println(it)
        }
    }
}
