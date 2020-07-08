package nl.jamiecraane.knowit.coroutines.samples

import kotlinx.coroutines.*
import nl.jamiecraane.knowit.shared.log
import kotlin.system.measureTimeMillis

fun main() = runBlocking<Unit> {
//    sequential()
    parallel()

}

private fun CoroutineScope.parallel() {
    println("Parallel")
    launch {
        val time = measureTimeMillis {
            val results = mutableListOf<Int>()
            val jobs = mutableListOf<Job>()
            for (i in 0..10000) {
                jobs += launch(Dispatchers.IO) {
                    results += expensiveOperation(i)
                }
            }
            jobs.joinAll()
            println(results)
        }
        println("time: $time")
    }
}

private fun CoroutineScope.sequential() {
    println("Sequential")
    launch {
        val time = measureTimeMillis {
            val results = mutableListOf<Int>()
            for (i in 0..100) {
                results += expensiveOperation(i)
            }
            println(results)
        }
        println("time: $time")
    }
}

private suspend fun expensiveOperation(input: Int): Int {
    log("Computing $input")
    delay(100)
    return input * 10
}
