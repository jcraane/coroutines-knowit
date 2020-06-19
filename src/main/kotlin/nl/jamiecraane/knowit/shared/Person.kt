package nl.jamiecraane.knowit.shared

import kotlinx.serialization.Serializable

@Serializable
data class Person(val firstName: String, val lastName: String) {
}
