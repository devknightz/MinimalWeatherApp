package you.devknights.minimalweather.ui.landing

import android.location.Location
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.*
import you.devknights.minimalweather.core.executor.AppExecutors
import you.devknights.minimalweather.database.KeyValueStore
import you.devknights.minimalweather.database.entity.WeatherCity
import you.devknights.minimalweather.repo.weather.CityRepository
import javax.inject.Inject

class LandingViewModel
@Inject constructor(private val keyValueStore: KeyValueStore,
                    private val cityRepository: CityRepository,
                    private val appExecutors: AppExecutors,
                    private val locationLiveData: LocationLiveData) : ViewModel() {


    private val weatherCityLiveData = MediatorLiveData<List<WeatherCity>>()

    private val locationObserver: Observer<Location> = Observer {
        it?.let {
            val currentLocationWeatherCity = WeatherCity(latitude = it.latitude,
                    longitude = it.longitude)


            var updatedList = weatherCityLiveData.value?.let {
                val updatedList = if (it.isNotEmpty()) {
                    listOf<WeatherCity>().plus(currentLocationWeatherCity).plus(it)
                } else {
                    listOf(currentLocationWeatherCity)
                }

                updatedList
            }

            if (updatedList == null) {
                updatedList = listOf(currentLocationWeatherCity)
            }

            updatedList.let {
                weatherCityLiveData.postValue(it)
            }

            removeLocationObserver()
        }
    }


    fun syncCityData() {
        cityRepository.syncCityData()
    }


    fun changeAutoNightMode(enableAutoNightMode: Boolean): LiveData<Int> {
        val data = MutableLiveData<Int>()

        appExecutors.runOnDisk {
            var nightMode: Int = AppCompatDelegate.MODE_NIGHT_NO

            if (enableAutoNightMode) {
                nightMode = AppCompatDelegate.MODE_NIGHT_AUTO
            }

            keyValueStore.setAutoNightMode(nightMode)

            data.postValue(nightMode)
        }

        return data
    }


    fun isAutoNightModeEnabled(): LiveData<Boolean> {
        val data = MutableLiveData<Boolean>()

        appExecutors.runOnDisk {
            val nightMode = keyValueStore.getAutoNightMode()

            data.postValue(nightMode == AppCompatDelegate.MODE_NIGHT_AUTO)
        }

        return data
    }


    fun getAllWeatherCity(): LiveData<List<WeatherCity>> {
        initLocationUpdate()

        val allWeatherCity = cityRepository.getAllWeatherCity()
        weatherCityLiveData.addSource(allWeatherCity) {
            weatherCityLiveData.removeSource(allWeatherCity)
            weatherCityLiveData.postValue(it)
        }

        return weatherCityLiveData
    }


    private fun initLocationUpdate() {
        locationLiveData.observeForever(locationObserver)
    }

    private fun removeLocationObserver() {
        locationLiveData.removeObserver(locationObserver)
    }
}