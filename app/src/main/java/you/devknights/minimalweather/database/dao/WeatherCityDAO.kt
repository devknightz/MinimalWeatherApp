package you.devknights.minimalweather.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import you.devknights.minimalweather.database.entity.WeatherCity

@Dao
interface WeatherCityDAO {

    @Query("SELECT * FROM weather_city")
    fun getAllCity(): LiveData<WeatherCity>
}