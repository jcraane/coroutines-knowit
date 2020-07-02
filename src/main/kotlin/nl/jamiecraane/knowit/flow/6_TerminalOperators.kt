package nl.jamiecraane.knowit.flow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun main() = runBlocking<Unit> {
    val flow = flow<Int> {
        println("About to emit values")
        for (i in 0..10) {
            emit(i)
        }
    }

//    these are all terminal operators
    flow.collect { println(it) }
    val numbers = flow.toList()
    println("numbers = $numbers")
    val total = flow.reduce { accumulator, value -> accumulator + value }
    println("total = $total")
}
