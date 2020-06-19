package nl.jamiecraane.knowit.coroutines

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

val jsonParser = Json(JsonConfiguration.Stable.copy(ignoreUnknownKeys = true, isLenient = true))
fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")
