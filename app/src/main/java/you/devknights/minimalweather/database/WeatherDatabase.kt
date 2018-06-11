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

package you.devknights.minimalweather.database



import androidx.room.Database
import androidx.room.RoomDatabase
import you.devknights.minimalweather.database.dao.CityDAO
import you.devknights.minimalweather.database.dao.WeatherCityDAO
import you.devknights.minimalweather.database.dao.WeatherDAO
import you.devknights.minimalweather.database.entity.City
import you.devknights.minimalweather.database.entity.Weather
import you.devknights.minimalweather.database.entity.WeatherCity

/**
 * Database for Weather Apps. It'll return all the DAO.
 * @author vinayagasundar
 */
@Database(entities = [(Weather::class), (City::class), WeatherCity::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDAO(): WeatherDAO

    abstract fun cityDAO(): CityDAO

    abstract fun weatherCityDao(): WeatherCityDAO

    companion object {

        const val TABLE_WEATHER = "weather"

        internal val DATABASE_NAME = "weather.db"
    }


}
