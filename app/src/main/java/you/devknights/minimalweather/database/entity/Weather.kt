/*
 * Copyright 2017 vinayagasundar
 * Copyright 2017 randhirgupta
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package you.devknights.minimalweather.database.entity


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import you.devknights.minimalweather.database.WeatherDatabase

/**
 * @author Randhir
 * @since 6/6/2017.
 */

@Entity(tableName = WeatherDatabase.TABLE_WEATHER)
data class Weather(

        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,

        ///////////////////////////////////////////////////////////////////////////
        // Place information
        ///////////////////////////////////////////////////////////////////////////


        @Embedded var city: City? = null,

        ///////////////////////////////////////////////////////////////////////////
        // Weather Information
        ///////////////////////////////////////////////////////////////////////////

        var weatherId: Int = 0,
        var weatherMain: String? = null,
        var weatherDescription: String? = null,
        var weatherIcon: String? = null,


        ///////////////////////////////////////////////////////////////////////////
        // Temperature, wind , pressure information
        ///////////////////////////////////////////////////////////////////////////

        var temperature: Float = 0.toFloat(),
        var pressure: Float = 0.toFloat(),
        var humidity: Float = 0.toFloat(),
        var windSpeed: Float = 0.toFloat(),


        ///////////////////////////////////////////////////////////////////////////
        // Sunrise and sunset timing information
        ///////////////////////////////////////////////////////////////////////////

        var sunriseTime: Long = 0,
        var sunsetTime: Long = 0,
        var startTime: Long = 0,
        var endTime: Long = 0
)
