package nl.jamiecraane.knowit.coroutines.samples

import kotlinx.coroutines.*
import java.lang.Exception

val handler = CoroutineExceptionHandler { _, exception ->
    println("Caught $exception")
}

fun main() = runBlocking<Unit> {
    launch { //    with launch exceptions are thrown when they happen
        try {
            throw RuntimeException("Something bad happened")
        } catch (e: Exception) {
            println("Exception happened in launch: ${e.message}")
        }
    }

    supervisorScope {
        val deferred = async {
            throw RuntimeException("Something bad happened")
        }

        try {
            deferred.await() //            Exception is not thrown until await is called because supervisor scope is used.
        } catch (e: Exception) {
            println("Exception happened in async: ${e.message}")
        }
    }

    val scopeWithExceptionHandler = CoroutineScope(handler)
    scopeWithExceptionHandler.launch {
        throw RuntimeException("Something bad happened")
    }.join()
}
