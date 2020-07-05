package nl.jamiecraane.knowit.rxpatterns

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.RuntimeException
import kotlin.math.max
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

@ExperimentalTime
fun main() = runBlocking<Unit> {
    launch {
        val result = exponentialBackoff { getData(it) }
        println(result)
    }
}

private suspend fun getData(invocationCount: Int = 0): String {
    println("<top>.getData($invocationCount)")
    delay(250)
    if (invocationCount < 2) {
        throw RuntimeException("Error")
    }

    return "Hello World"
}

@ExperimentalTime
private suspend fun exponentialBackoff(
    numRetries: Int = 0,
    maxRetries: Int = 3,
    delay: Duration = 1000.milliseconds,
    block: suspend (numRetries: Int) -> String
): String {
    return try {
        block(numRetries)
    } catch (e: Exception) {
        val newRetries = numRetries + 1
        val maxRetriesReached = newRetries >= maxRetries
        if (maxRetriesReached) {
            throw RuntimeException("Could not get response")
        }

        println("delay for $delay")
        delay(delay.inMilliseconds.toLong())
        val newDelay = (delay.inMilliseconds * 2).toLong()
        exponentialBackoff(newRetries, maxRetries, newDelay.milliseconds, block)
    }
}
