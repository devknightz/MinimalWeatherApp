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

package you.devknights.minimalweather.repo.weather;

import android.arch.lifecycle.LiveData;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import javax.inject.Inject;
import javax.inject.Singleton;

import you.devknights.minimalweather.BuildConfig;
import you.devknights.minimalweather.core.executor.AppExecutors;
import you.devknights.minimalweather.database.dao.WeatherDAO;
import you.devknights.minimalweather.database.entity.WeatherEntity;
import you.devknights.minimalweather.model.Resource;
import you.devknights.minimalweather.network.ApiResponse;
import you.devknights.minimalweather.network.ApiService;
import you.devknights.minimalweather.network.model.WeatherResponse;
import you.devknights.minimalweather.repo.NetworkBoundResource;

/**
 * @author vinayagasundar
 */
@Singleton
public final class WeatherRepository {

    private final ApiService mApiService;
    private final WeatherDAO mWeatherDAO;
    private final AppExecutors mAppExecutors;


    @Inject
    WeatherRepository(ApiService apiService, WeatherDAO weatherDAO, AppExecutors mAppExecutors) {
        mApiService = apiService;
        mWeatherDAO = weatherDAO;
        this.mAppExecutors = mAppExecutors;
    }


    public LiveData<Resource<WeatherEntity>> getWeatherInfoAsLiveData(Location location) {
        return new NetworkBoundResource<WeatherEntity, WeatherResponse>(mAppExecutors) {

            @Override
            protected void saveCallResult(@NonNull WeatherResponse item) {
                WeatherEntity weatherEntity = item.buildWeather();
                long startTime = System.currentTimeMillis();
                weatherEntity.setStartTime(startTime);
                weatherEntity.setEndTime(startTime + 300000);
                mWeatherDAO.insert(weatherEntity);
            }

            @Override
            protected boolean shouldFetch(@Nullable WeatherEntity data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<WeatherEntity> loadFromDb() {
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

                String latitude = decimalFormat.format(location.getLatitude());
                String longitude = decimalFormat.format(location.getLongitude());

                return mWeatherDAO.getWeatherByLocation(latitude, longitude, System.currentTimeMillis());
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<WeatherResponse>> createCall() {
                return mApiService.getWeatherDataWithLocationCall(location.getLatitude(),
                        location.getLongitude(),
                        BuildConfig.OPEN_WEATHER_API_KEY);
            }
        }.asLiveData();
    }
}
