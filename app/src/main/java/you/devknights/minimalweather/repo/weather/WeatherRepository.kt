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

package you.devknights.minimalweather.repo.weather

import android.arch.lifecycle.LiveData
import android.location.Location

import java.math.RoundingMode
import java.text.DecimalFormat

import javax.inject.Inject
import javax.inject.Singleton

import you.devknights.minimalweather.BuildConfig
import you.devknights.minimalweather.core.executor.AppExecutors
import you.devknights.minimalweather.database.dao.WeatherDAO
import you.devknights.minimalweather.database.entity.WeatherEntity
import you.devknights.minimalweather.model.Resource
import you.devknights.minimalweather.network.ApiResponse
import you.devknights.minimalweather.network.ApiService
import you.devknights.minimalweather.network.model.WeatherResponse
import you.devknights.minimalweather.repo.NetworkBoundResource

/**
 * @author vinayagasundar
 */
@Singleton
class WeatherRepository @Inject
internal constructor(private val mApiService: ApiService, private val mWeatherDAO: WeatherDAO, private val mAppExecutors: AppExecutors) {


    fun getWeatherInfoAsLiveData(location: Location): LiveData<Resource<WeatherEntity>> {
        return object : NetworkBoundResource<WeatherEntity, WeatherResponse>(mAppExecutors) {

            override fun saveCallResult(item: WeatherResponse) {
                val weatherEntity = item.buildWeather()
                val startTime = System.currentTimeMillis()
                weatherEntity.startTime = startTime
                weatherEntity.endTime = startTime + 300000
                mWeatherDAO.insert(weatherEntity)
            }

            override fun shouldFetch(data: WeatherEntity?): Boolean {
                return data == null
            }

            override fun loadFromDb(): LiveData<WeatherEntity> {
                val decimalFormat = DecimalFormat("#.##")
                decimalFormat.roundingMode = RoundingMode.HALF_UP

                val latitude = decimalFormat.format(location.latitude)
                val longitude = decimalFormat.format(location.longitude)

                return mWeatherDAO.getWeatherByLocation(latitude, longitude, System.currentTimeMillis())
            }

            override fun createCall(): LiveData<ApiResponse<WeatherResponse>> {
                return mApiService.getWeatherDataWithLocationCall(location.latitude,
                        location.longitude,
                        BuildConfig.OPEN_WEATHER_API_KEY)
            }
        }.asLiveData()
    }
}
