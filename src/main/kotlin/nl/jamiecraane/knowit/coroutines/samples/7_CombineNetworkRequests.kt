package nl.jamiecraane.knowit.coroutines.samples

import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import kotlin.system.measureTimeMillis

@ExperimentalCoroutinesApi
fun main() = runBlocking<Unit> {
    val time = measureTimeMillis {
        val job = launch {
            val location = getLastKnownLocation() ?: getLocation()
            val weatherDeferred = async { getWeather(location) }
            val reverseGeocodeDeferred = async { reverseGeocode(location) }
            val result = weatherDeferred.await() to reverseGeocodeDeferred.await()
            println(result)
        }

        job.join()
    }
    println(time)
}

private suspend fun getLastKnownLocation(): LatLng? {
    println("<top>.getLastKnownLocation")
    delay(150)
    return null
}

private suspend fun getLocation(): LatLng {
    println("<top>.getLastKnownLocation")
    delay(150)
    return LatLng(5.0, 50.0)
}

private suspend fun getWeather(location: LatLng): Weather {
    delay(1000)
    return Weather(25.0)
}

private suspend fun reverseGeocode(location: LatLng): Address {
    delay(1000)
    return Address("Amsterdam")
}

private data class LatLng(val lat: Double, val lng: Double)
private data class Weather(val temp: Double)
private data class Address(val name: String)
