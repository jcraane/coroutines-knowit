package nl.jamiecraane.knowit.flow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun foo(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100) // pretend we are asynchronously waiting 100 ms
        emit(i) // emit next value
    }
}

@ExperimentalCoroutinesApi
fun main() = runBlocking<Unit> {
    val time = measureTimeMillis {
        foo()
//            .buffer() // runs emitting code on separate coroutine
//            .conflate() // When collector is slow and only most recent ones are needed
            .collect { value ->
                delay(300) // pretend we are processing it for 300 ms
                println(value)
            }
            /*.collectLatest { value ->
                println("Collecting $value")
                delay(300) // pretend we are processing it for 300 ms
                println(value)
                println("Done")
            }*/
    }
    println("Collected in $time ms")
}
