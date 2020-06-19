package nl.jamiecraane.knowit.shared

import kotlinx.serialization.Serializable

@Serializable
data class Task(val id: Long, val name: String) {
}
