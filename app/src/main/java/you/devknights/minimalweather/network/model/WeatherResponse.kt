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

package you.devknights.minimalweather.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import you.devknights.minimalweather.database.entity.WeatherEntity

class WeatherResponse {

    @SerializedName("coord")
    @Expose
    private var coord: Coord? = null
    @SerializedName("weather")
    @Expose
    private var weather: List<Weather>? = null
    @SerializedName("base")
    @Expose
    private var base: String? = null
    @SerializedName("main")
    @Expose
    private var main: Main? = null
    @SerializedName("visibility")
    @Expose
    private var visibility: Int = 0
    @SerializedName("wind")
    @Expose
    private var wind: Wind? = null
    @SerializedName("clouds")
    @Expose
    private var clouds: Clouds? = null
    @SerializedName("dt")
    @Expose
    private var dt: Int = 0
    @SerializedName("sys")
    @Expose
    private var sys: Sys? = null
    @SerializedName("id")
    @Expose
    private var id: Int = 0
    @SerializedName("name")
    @Expose
    private var name: String? = null
    @SerializedName("cod")
    @Expose
    private var cod: Int = 0


    fun buildWeather(): WeatherEntity {
        val weatherEntity = WeatherEntity()

        weatherEntity.placeId = id
        weatherEntity.placeName = name
        weatherEntity.placeLat = coord?.lat ?: 0.toDouble()
        weatherEntity.placeLon = coord?.lon ?: 0.toDouble()

        weather?.let {
            if (it.isNotEmpty()) {
                val weather = it[0]

                weatherEntity.weatherId = weather.id
                weatherEntity.weatherMain = weather.main
                weatherEntity.weatherDescription = weather.description
                weatherEntity.weatherIcon = weather.icon
            }
        }


        weatherEntity.temperature = main?.temp ?: 0.toFloat()
        weatherEntity.pressure = main?.pressure ?: 0.toFloat()
        weatherEntity.humidity = main?.humidity ?: 0.toFloat()
        weatherEntity.windSpeed = wind?.speed?.toFloat() ?: 0.toFloat()
        weatherEntity.sunriseTime = sys?.sunrise ?: 0
        weatherEntity.sunsetTime = sys?.sunset ?: 0

        return weatherEntity

    }

}
