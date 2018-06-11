package you.devknights.minimalweather.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import you.devknights.minimalweather.database.entity.WeatherCity

@Dao
interface WeatherCityDAO {

    @Query("SELECT * FROM weather_city")
    fun getAllCity(): List<WeatherCity>

    @Query("SELECT MAX(`index`) from weather_city")
    fun maxIndex(): Int

    @Insert
    fun insert(weatherCity: WeatherCity)
}