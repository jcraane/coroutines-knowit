package nl.jamiecraane.knowit.flow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun main() = runBlocking<Unit> {
    val flow = flow<Int> {
        for (i in 0..10) {
            emit(i)
        }
    }

    flow.take(3)
        .collect {
            println(it)
        }
}
