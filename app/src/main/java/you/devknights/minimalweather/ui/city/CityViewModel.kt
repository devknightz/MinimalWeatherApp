package you.devknights.minimalweather.ui.city

import androidx.lifecycle.ViewModel
import you.devknights.minimalweather.database.entity.City
import you.devknights.minimalweather.repo.weather.CityRepository
import javax.inject.Inject


class CityViewModel @Inject constructor(private val cityRepository: CityRepository) : ViewModel() {


    fun getAllWeatherCity() = cityRepository.getAllWeatherCity()


    fun getAllCity() = cityRepository.getAllCity()


    fun addToWeatherCity(city: City) {
        cityRepository.addToWeatherCity(city)
    }
}