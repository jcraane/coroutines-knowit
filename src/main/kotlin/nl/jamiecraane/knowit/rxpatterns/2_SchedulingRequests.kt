package nl.jamiecraane.knowit.rxpatterns

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.max
import kotlin.system.measureTimeMillis
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

@ExperimentalTime
fun main() = runBlocking<Unit> {
    var c = 0
    val job = launch {
        schedule(1.seconds) {
            val times = getDepartureTimes()
            println(times)
        }
    }
}

@ExperimentalTime
tailrec suspend fun schedule(interval: Duration, block: suspend () -> Unit) {
    // here we measure the time it takes to execute block
    val time = measureTimeMillis { block() }
    println(time)
    // We never wait longer than the interval time and at minimum 0 milliseconds (when execution of the block takes longer > interval)
    delay(interval.toLongMilliseconds() - max(time, 0))
    schedule(interval, block)
}

private suspend fun getDepartureTimes(): List<String> {
    delay(2000)
    return listOf("09:30", "09:50", "10:15")
}
