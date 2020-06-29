package nl.jamiecraane.knowit.flow

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
//    flows are cold by default (channels are hot)
//    flows can be declared and only trigger when their collect method is called
//    thread communication and back pressure is implemented using coroutines

    val stringFlow = flow<String> {
        for (i in 0..100) {
            println("Emit value")
            emit("Emit ${i.toString()}")
        }
    }

    // Nothing is in the output if values are not collected

    launch {
//        collect is a suspending function an must be called from a coroutine
        stringFlow.collect {
            println(it)
        }
    }
}
