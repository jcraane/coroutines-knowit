package nl.jamiecraane.knowit.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


@ExperimentalCoroutinesApi
fun main() = runBlocking<Unit> {
    val addressAndLatLon = flowOf(retrieveAddress())
        .map { it to reverseGeocode(it) }
        .map { searchVicinity(it.second, 100) }

    addressAndLatLon.collect {
        println(it)
    }
}

private suspend fun retrieveAddress() : String {
    delay(450)
    return "Kerkstraat 34"
}

private suspend fun reverseGeocode(address: String): LatLon {
    delay(350)
    return LatLon(5.1 ,50.0)
}

private suspend fun searchVicinity(latLon: LatLon, radius: Int): List<String> {
    delay(150)
    return listOf("Shops", "GasStations")
}

data class LatLon(val lat: Double, val lon: Double)
