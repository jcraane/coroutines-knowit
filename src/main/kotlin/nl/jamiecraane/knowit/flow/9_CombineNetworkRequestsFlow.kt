package nl.jamiecraane.knowit.flow

import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import kotlin.system.measureTimeMillis

@FlowPreview
@ExperimentalCoroutinesApi
fun main() = runBlocking<Unit> {
    val time = measureTimeMillis {
        val job = launch {
            flowOf(getLastKnownLocation())
                .map { it ?: getLocation() }
                .map {
                    val weatherDeferred = async { getWeather(it) }
                    val reverseGeocodeDeferred = async { reverseGeocode(it) }
                    weatherDeferred.await() to reverseGeocodeDeferred.await()
                }
                .flowOn(Dispatchers.Default)
                .collect {
                    println(it)
                }
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
    println("<top>.getWeather")
    delay(1000)
    return Weather(25.0)
}

private suspend fun getWeatherFlow(location: LatLng) = flow {
    println("<top>.getWeather")
    delay(1000)
    emit(Weather(25.0))
}

private suspend fun reverseGeocode(location: LatLng): Address {
    println("<top>.reverseGeocode")
    delay(1000)
    return Address("Amsterdam")
}

private suspend fun reverseGeocodeFlow(location: LatLng) = flow {
    println("<top>.reverseGeocode")
    delay(1000)
    emit(Address("Amsterdam"))
}

private data class LatLng(val lat: Double, val lng: Double)
private data class Weather(val temp: Double)
private data class Address(val name: String)

/*.flatMapMerge {
                    flowOf(getWeather(it)).zip(flowOf(reverseGeocode(it))) { weather, address ->
                        weather to address
                    }
                }*/
/*.flatMapMerge {
    flow { emit(getWeather(it))
    }.zip(flow { emit(reverseGeocode(it)) }) { weather, address ->
        weather to address
    }
}*/
/*.flatMapMerge {
    getWeatherFlow(it).zip(reverseGeocodeFlow(it)) { weather, address ->
        weather to address
    }
}*/
