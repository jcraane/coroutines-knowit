package nl.jamiecraane.knowit.coroutines

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readText
import kotlinx.coroutines.*
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

fun main() = runBlocking<Unit> {
    launch { // Main safety, always launch on main
        log("in launch")
        val persons = retrievePersons()
        log("after retrieve")
        println(persons)
    }

    println("DONE")
}

private suspend fun retrievePersons(): List<Person> {
    log("in retrievePersons")
    val response = HttpClient().get<HttpResponse>("http://localhost:2500/persons")
    val persons = jsonParser.parse(Person.serializer().list, response.readText())
    return expensiveOperation(persons)
//    return persons
}

private suspend fun expensiveOperation(persons: List<Person>) = coroutineScope {
    withContext(Dispatchers.IO) {
        log("in expensive`Operation")
        println("Performing expensive operation on IO thread")
        delay(1500)
        println("Done with expensive operation")
        persons.map { it.copy(firstName = "${it.firstName}-COMPUTED") }
    }
}