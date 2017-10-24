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

package you.devknights.minimalweather.ui.landing;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.location.Location;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import you.devknights.minimalweather.database.entity.WeatherEntity;
import you.devknights.minimalweather.model.Resource;
import you.devknights.minimalweather.repo.weather.WeatherRepository;

/**
 * @author vinayagasundar
 */

public class LandingViewModel extends AndroidViewModel {

    private LiveData<Location> mLocation;

    private final WeatherRepository weatherRepository;

    @Inject
    public LandingViewModel(@NonNull Application application, WeatherRepository weatherRepository) {
        super(application);
        this.weatherRepository = weatherRepository;
    }


    public LiveData<Location> getLocation() {
        if (mLocation == null) {
            mLocation = new LocationLiveData(this.getApplication());
        }

        return mLocation;
    }


    public LiveData<Resource<WeatherEntity>> getWeatherData(Location location) {
        return weatherRepository.getWeatherInfoAsLiveData(location);
    }


}
