package nl.jamiecraane.knowit.coroutines.samples

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readText
import kotlinx.coroutines.*
import kotlinx.serialization.builtins.list
import nl.jamiecraane.knowit.shared.Person
import nl.jamiecraane.knowit.shared.jsonParser
import nl.jamiecraane.knowit.shared.log

fun main() = runBlocking<Unit> {
    launch { // Main safety, always launch on main
        log("in launch")
        val persons = retrievePersons()
        log("after retrieve")
        println(persons)
    }

    println("DONE")
}

// suspend function can only be called from a launch or async block or from within another suspend function
// Main safety, always launch on main also means execute network and io on IO thread (NetworkOnMainThreadException)
private suspend fun retrievePersons(): List<Person> = withContext(Dispatchers.IO) {
    log("in retrievePersons")
    val response = HttpClient().get<HttpResponse>("http://localhost:2500/persons")
    val persons = jsonParser.parse(Person.serializer().list, response.readText())
    expensiveOperation(persons)
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
