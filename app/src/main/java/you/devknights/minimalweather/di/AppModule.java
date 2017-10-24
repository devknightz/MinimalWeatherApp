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

package you.devknights.minimalweather.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import you.devknights.minimalweather.database.WeatherDatabase;
import you.devknights.minimalweather.database.dao.WeatherDAO;
import you.devknights.minimalweather.network.ApiService;
import you.devknights.minimalweather.network.LiveDataCallAdapterFactory;

/**
 * @author vinayagasundar
 */
@Module(includes = {ViewModelModule.class, LiveDataModule.class})
class AppModule {

    @Provides @Singleton
    ApiService providesApiService(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(ApiService.class);
    }

    @Provides @Singleton
    Gson providesGson() {
        return new Gson();
    }


    @Provides @Singleton
    WeatherDatabase provideDB(Application application) {
        return Room.databaseBuilder(application, WeatherDatabase.class, "weather.db")
                .build();
    }

    @Provides @Singleton
    WeatherDAO provideWeatherDao(WeatherDatabase db) {
        return db.weatherDAO();
    }

    @Provides @Singleton
    Context provideContext(Application application) {
        return application;
    }

}
