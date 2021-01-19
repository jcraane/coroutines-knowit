package nl.jamiecraane.knowit.shared

import kotlinx.serialization.json.Json

val jsonParser = Json {
    ignoreUnknownKeys = true
    isLenient = true
}
fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")
