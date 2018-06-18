package you.devknights.minimalweather.ui.landing

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import you.devknights.minimalweather.database.entity.WeatherCity
import you.devknights.minimalweather.ui.weather.WeatherFragment

class WeatherCityAdapter(fragmentManager: FragmentManager,
                         private val weatherCityList: List<WeatherCity>) :
        FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return WeatherFragment.newInstance(weatherCityList[position])
    }

    override fun getCount(): Int = weatherCityList.size
}