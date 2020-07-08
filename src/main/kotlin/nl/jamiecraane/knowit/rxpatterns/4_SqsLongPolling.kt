package nl.jamiecraane.knowit.rxpatterns

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException

fun main() = runBlocking<Unit> {
    val sqsMessages = flow {
        while (true) {
            emit(receiveFromSqsQueue())
        }
    }

    launch {
        sqsMessages.collect {
            processMessages(it)
        }
    }
}

private suspend fun processMessages(messages: List<String>) = supervisorScope {
    println("<top>.receiveAndProcess")
    messages.map {
        async(Dispatchers.Default) { processMessage(it) }
    }.map {
        try {
            it.await()
        } catch (e: Exception) {
            println("🔴 e.message")
        }
    }
}

private suspend fun processMessage(message: String) {
    println("<top>.processMessage($message")
    delay(1000)
    if (message == "4") {
        throw RuntimeException("Message $message failed")
    }
    println("Message processed")
    deleteMessage(message)
}

private suspend fun deleteMessage(message: String) {
    println("<top>.deleteMessage($message)")
    delay(1000)
    println("Message deleted")
}

private suspend fun receiveFromSqsQueue(): List<String> {
    delay(1500)
    return (0..8).toList().map { it.toString() }
}
