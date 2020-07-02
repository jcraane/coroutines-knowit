package nl.jamiecraane.knowit.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


@ExperimentalCoroutinesApi
fun main() = runBlocking<Unit> {
    val addressAndLatLon = flowOf(retrieveAddress())
        .map { address ->
            val latLon = reverseGeocode(address)
            searchVicinity(latLon, 100)
        }

    addressAndLatLon.collect {
        println(it)
    }
}

private suspend fun retrieveAddress(): String {
    delay(450)
    return "Kerkstraat 34"
}

private suspend fun reverseGeocode(address: String): LatLon {
    delay(350)
    return LatLon(5.1, 50.0)
}

private suspend fun searchVicinity(latLon: LatLon, radius: Int): List<String> {
    delay(150)
    return listOf("Shops", "GasStations")
}
