package you.devknights.minimalweather.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import you.devknights.minimalweather.database.WeatherDatabase
import you.devknights.minimalweather.database.dao.CityDAO
import you.devknights.minimalweather.database.dao.WeatherCityDAO
import you.devknights.minimalweather.database.dao.WeatherDAO
import javax.inject.Singleton

@Module
internal class RoomModule {

    @Provides
    @Singleton
    fun provideDB(application: Application): WeatherDatabase {
        return Room.databaseBuilder(application, WeatherDatabase::class.java, "weather.db")
                .build()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(db: WeatherDatabase): WeatherDAO = db.weatherDAO()

    @Provides
    @Singleton
    fun provideCityDao(db: WeatherDatabase): CityDAO = db.cityDAO()


    @Provides
    @Singleton
    fun provideWeatherCityDao(db: WeatherDatabase): WeatherCityDAO = db.weatherCityDao()
}