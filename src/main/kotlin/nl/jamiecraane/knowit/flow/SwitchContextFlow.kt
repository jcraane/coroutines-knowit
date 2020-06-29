package nl.jamiecraane.knowit.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import nl.jamiecraane.knowit.shared.log

@ExperimentalCoroutinesApi
fun main() = runBlocking<Unit> {
//    flows are cold by default (channels are hot)
//    flows can be declared and only trigger when their collect method is called
//    thread communication and back pressure is implemented using coroutines

    val stringFlow = flow<String> {
        for (i in 0..10) {
            emit("Emit ${i.toString()}")
        }
    }

    // Nothing is in the output if values are not collected

    launch {
//        collect is a suspending function an must be called from a coroutine
        stringFlow
            .map { it.split(" ") }
            .map {
                log("map")
                it.last()
            }
            .flowOn(Dispatchers.IO) // Affects preceding operators
            .onEach {
                log("delaying")
                delay(100)
            }
            .collect { // Executed on launch dispatcher (mains safety)
                log("Printing")
                println(it)
            }
    }
}
