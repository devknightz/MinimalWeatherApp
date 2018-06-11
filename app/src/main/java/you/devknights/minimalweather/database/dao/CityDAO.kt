package you.devknights.minimalweather.database.dao

import android.database.Cursor
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


    @Query("SELECT * FROM city WHERE _id = :cityId")
    fun getCityById(cityId: Long): City

    @Query("SELECT * FROM city WHERE name like '%' || :cityName || '%'  COLLATE NOCASE")
    fun searchByCityName(cityName: String): Cursor
}