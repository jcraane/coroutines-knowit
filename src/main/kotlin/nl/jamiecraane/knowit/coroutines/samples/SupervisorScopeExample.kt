package nl.jamiecraane.knowit.coroutines.samples

import kotlinx.coroutines.*
import java.lang.RuntimeException

fun main() = runBlocking<Unit> {
    /*println("Parent is cancelled")
    coroutineIsCancelledOnException().join()*/
    println("Parent is not cancelled")
    withSupervisorScope().join()
}

private fun CoroutineScope.withSupervisorScope(): Job {
    return launch {
        //    to prevent parent cancellation use a supervisor job
        supervisorScope {
            launch {
                println(executeRequest("request 1"))
            }
            launch {
                println(executeRequest("request 2", true))
            }
//        we never see the output of this coroutine since the parent is cancelled when a coroutine throws an exception
            launch {
                println(executeRequest("request 3"))
            }
            println("Done")
        }
    }
}

private fun CoroutineScope.coroutineIsCancelledOnException(): Job {
    return launch {
        launch {
            println(executeRequest("request 1"))
        }
        launch {
            println(executeRequest("request 2", true))
        }
//        we never see the output of this coroutine since the parent is cancelled when a coroutine throws an exception
        launch {
            println(executeRequest("request 3"))
        }
        println("Done")
    }
}

private suspend fun executeRequest(result: String, throwException: Boolean = false): String {
    delay(100)
    if (throwException) {
        throw RuntimeException("Something bad happened!")
    }
    return result
}
