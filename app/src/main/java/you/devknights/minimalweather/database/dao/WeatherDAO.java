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

package you.devknights.minimalweather.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import you.devknights.minimalweather.database.WeatherDatabase;
import you.devknights.minimalweather.database.entity.WeatherEntity;

/**
 * A DAO interface for {@link WeatherEntity} model
 * @author vinayagasundar
 */

@Dao
public interface WeatherDAO {

    @Insert
    void insert(WeatherEntity weatherEntity);

    @Query("SELECT * FROM " + WeatherDatabase.TABLE_WEATHER)
    List<WeatherEntity> getAll();

    @Query("SELECT * FROM " + WeatherDatabase.TABLE_WEATHER +
            " WHERE placeLat = :latitude AND placeLon = :longitude" +
            " AND endTime >= :currentTimeInSecs ORDER BY endTime DESC")
    LiveData<WeatherEntity> getWeatherByLocation(String latitude, String longitude, long currentTimeInSecs);

    @Query("SELECT * FROM " + WeatherDatabase.TABLE_WEATHER
            + " WHERE endTime <= :currentTimeInMills")
    List<WeatherEntity> getAllExpiredData(long currentTimeInMills);

    @Delete
    void deleteWeatherData(List<WeatherEntity> weatherEntities);
}
