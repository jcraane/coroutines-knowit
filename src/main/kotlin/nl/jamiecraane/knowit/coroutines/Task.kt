package nl.jamiecraane.knowit.coroutines

import kotlinx.serialization.Serializable

@Serializable
data class Task(val id: Long, val name: String) {
}
