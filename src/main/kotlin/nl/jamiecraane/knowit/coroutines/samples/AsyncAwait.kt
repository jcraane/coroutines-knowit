package nl.jamiecraane.knowit.coroutines.samples

import kotlinx.coroutines.*
import nl.jamiecraane.knowit.shared.Person
import nl.jamiecraane.knowit.shared.Task
import nl.jamiecraane.knowit.shared.log
import kotlin.system.measureTimeMillis

// wait for result on two parallel requests
// how to handle exceptions with async await
// how to handle cancellation of children with async await

fun main() = runBlocking<Unit> {
    launch {
        val sequentialTime = measureTimeMillis {
            executeSequential()
        }
        println("SEQUENTIAL $sequentialTime")

        val parallelTime = measureTimeMillis {
            executeInParallel()
        }
        println("PARALLEL $parallelTime")
    }
}

private suspend fun executeSequential(): Pair<List<Person>, List<Task>> = coroutineScope {
    // withContext is same as async{}.await()
    val persons = withContext(Dispatchers.IO) { retrievePersons() }
    val tasks = withContext(Dispatchers.IO) { retrieveTasks() }
    persons to tasks
}

private suspend fun executeInParallel(): Pair<List<Person>, List<Task>> = coroutineScope {
    val personsCall = async(Dispatchers.IO) { retrievePersons() }
    val tasksCall = async(Dispatchers.IO) { retrieveTasks() }
    personsCall.await() to tasksCall.await()
}

private suspend fun retrievePersons(): List<Person> {
    log("in retrievePersons")
    delay(1000)
    return listOf(Person("Jan", "Janssen"))
}

private suspend fun retrieveTasks(): List<Task> {
    log("in retrieveTasks")
    delay(1000)
    return listOf(Task(1, "Task1"))
}
