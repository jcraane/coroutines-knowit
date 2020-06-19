package nl.jamiecraane.knowit.coroutines

import kotlinx.serialization.Serializable

@Serializable
data class Person(val firstName: String, val lastName: String) {
}
