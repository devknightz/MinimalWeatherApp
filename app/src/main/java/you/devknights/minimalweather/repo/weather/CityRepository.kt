package you.devknights.minimalweather.repo.weather

import android.content.Context
import you.devknights.minimalweather.core.executor.AppExecutors
import you.devknights.minimalweather.database.KeyValueStore
import you.devknights.minimalweather.database.dao.CityDAO
import you.devknights.minimalweather.database.entity.City
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityRepository @Inject constructor(private val context: Context,
                                         private val cityDAO: CityDAO,
                                         private val keyValueStore: KeyValueStore,
                                         private val mAppExecutors: AppExecutors) {


    companion object {
        const val WEEK_DAYS_IN_MILLIS = 604800000
    }


    fun syncCityData() {
        mAppExecutors.diskIO().execute {

            val lastSyncTime = keyValueStore.getCitySyncTime()

            val diff = System.currentTimeMillis() - lastSyncTime

            if (diff > WEEK_DAYS_IN_MILLIS) {
                val cityDataStream = context.assets.open("city_list.txt")
                val bufferReader = BufferedReader(InputStreamReader(cityDataStream))

                var line: String? = bufferReader.readLine()

                var firstLine = true

                val cities = mutableListOf<City>()

                while (line != null) {
                    val cityData = line
                    line = bufferReader.readLine()


                    if (firstLine) {
                        firstLine = false
                        continue
                    }

                    val (id, name, lat, lon, countryCode) = cityData.split("\t")
                    val city = City(id.toLong(), name, lat.toDouble(), lon.toDouble(), countryCode)
                    cities.add(city)
                }

                cityDAO.insert(cities)

                keyValueStore.setCitySyncTime(System.currentTimeMillis())
            }


        }
    }
}