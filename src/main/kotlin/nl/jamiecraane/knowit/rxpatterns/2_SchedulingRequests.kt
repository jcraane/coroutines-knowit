package nl.jamiecraane.knowit.rxpatterns

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

@ExperimentalTime
fun main() = runBlocking<Unit> {
    launch {
        schedule(1.seconds) {
            val times = getDepartureTimes()
            println(times)
        }
    }
}

@ExperimentalTime
private tailrec suspend fun schedule(interval: Duration, block: suspend () -> Unit) {
    block()
    delay(interval.toLongMilliseconds())
    schedule(interval, block)
}

private suspend fun getDepartureTimes(): List<String> {
    delay(250)
    return listOf("09:30", "09:50", "10:15")
}
