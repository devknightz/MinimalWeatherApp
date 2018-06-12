package you.devknights.minimalweather.database.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "weather_city")
data class WeatherCity(var cityId: Long = -1,
                       var latitude: Double = 0.0,
                       var longitude: Double = 0.0,
                       var index: Int = 0) {

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null


    @Ignore
    var city: City? = null
}