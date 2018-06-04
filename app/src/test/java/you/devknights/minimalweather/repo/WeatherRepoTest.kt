package you.devknights.minimalweather.repo

import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import you.devknights.minimalweather.database.dao.WeatherDAO
import you.devknights.minimalweather.network.ApiService
import you.devknights.minimalweather.repo.weather.WeatherRepository
import you.devknights.minimalweather.util.InstantAppExecutor


@RunWith(JUnit4::class)
class WeatherRepoTest {

    private val weatherDao = mock(WeatherDAO::class.java)

    private val weatherService = mock(ApiService::class.java)

    private val repo = WeatherRepository(weatherService, weatherDao, InstantAppExecutor())

    private val location = mock(Location::class.java)



    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()


    @Test
    fun getWeatherInfo() {
        val location: Location = location

        val time = System.currentTimeMillis() + 1000

        repo.getWeatherInfoAsLiveData(location)

        verify(weatherDao).getWeatherByLocation("0", "0", time)
    }
}