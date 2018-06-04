/*
 * Copyright 2017 vinayagasundar
 * Copyright 2017 randhirgupta
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package you.devknights.minimalweather.network


import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.Collections
import java.util.Locale

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.BufferedSource
import okio.Okio
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import you.devknights.minimalweather.BuildConfig
import you.devknights.minimalweather.database.entity.WeatherEntity
import you.devknights.minimalweather.network.model.WeatherResponse

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsNull.notNullValue
import you.devknights.minimalweather.util.LiveDataTestUtil.getValue

/**
 * @author vinayagasundar
 */

class ApiServiceTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private var apiService: ApiService? = null

    private var mockWebServer: MockWebServer? = null


    @Before
    fun createService() {
        mockWebServer = MockWebServer()

        apiService = Retrofit.Builder()
                .baseUrl(mockWebServer!!.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(ApiService::class.java)
    }

    @After
    @Throws(IOException::class)
    fun stopService() {
        mockWebServer!!.shutdown()
    }


    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun getWeatherDataForLocation() {
        enqueueResponse("weather_data.json")

        val latitude = 76.95
        val longitude = 28.69
        val apiKey = BuildConfig.OPEN_WEATHER_API_KEY

        val pathValue = String.format(Locale.ENGLISH,
                "/weather?lat=%.2f&lon=%.2f&appid=%s", latitude, longitude, apiKey)

        val weatherResponse = getValue(apiService!!
                .getWeatherDataWithLocationCall(latitude, longitude, apiKey)).body

        val weatherEntity = weatherResponse!!.buildWeather()

        val request = mockWebServer!!.takeRequest()
        assertThat(request.path, `is`(pathValue))
        assertThat(weatherEntity, notNullValue())
        //        assertThat(weatherEntity.getCity()., is("Bahadurgarh"));
        assertThat(weatherEntity.temperature, `is`(306.6f))

    }


    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader
                .getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val response = MockResponse()
        for ((key, value) in headers) {
            response.addHeader(key, value)
        }

        mockWebServer!!.enqueue(response.setBody(source.readString(StandardCharsets.UTF_8)))
    }
}
