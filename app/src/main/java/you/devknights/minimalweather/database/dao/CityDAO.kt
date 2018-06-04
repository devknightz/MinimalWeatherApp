package you.devknights.minimalweather.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import you.devknights.minimalweather.database.entity.City

@Dao
interface CityDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(city: City)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cities: List<City>)

    @Query("select * from city")
    fun getAll(): LiveData<List<City>>
}