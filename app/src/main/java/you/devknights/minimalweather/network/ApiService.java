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

package you.devknights.minimalweather.network;

import android.arch.lifecycle.LiveData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import you.devknights.minimalweather.network.model.WeatherResponse;

/**
 * This interface contains all the API information
 *
 * @author randhirgupta
 */

public interface ApiService {

    @GET("weather")
    LiveData<ApiResponse<WeatherResponse>> getWeatherDataCall(@Query("q") String city,
                                                 @Query("appid") String apiKey);

    @GET("weather")
    LiveData<ApiResponse<WeatherResponse>> getWeatherDataWithLocationCall(@Query("lat") Double latitude,
                                                 @Query("lon") Double longitude,
                                                 @Query("appid") String apiKey);
}
