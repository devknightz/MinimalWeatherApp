/*
 * Copyright 2017 vinayagasundar
 * Copyright 2017 randhirgupta
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package you.devknights.minimalweather.ui.weather


import android.location.Location
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_weather.*
import you.devknights.minimalweather.R
import you.devknights.minimalweather.database.entity.Weather
import you.devknights.minimalweather.database.entity.WeatherCity
import you.devknights.minimalweather.di.Injectable
import you.devknights.minimalweather.model.Resource
import you.devknights.minimalweather.model.Status
import you.devknights.minimalweather.util.UnitConvUtil
import java.util.*
import javax.inject.Inject


/**
 * [Fragment] for displaying the Weather Details
 */
class WeatherFragment : Fragment(), Injectable {

    @Inject
    lateinit var mFactory: ViewModelProvider.Factory

    lateinit var mWeatherViewModel: WeatherViewModel

    lateinit var mWeatherCity: WeatherCity

    companion object {
        private val TAG = "WeatherFragment"
        private const val PARAMS_WEATHER_CITY = "PARAMS_WEATHER_CITY"

        fun newInstance(weatherCity: WeatherCity): Fragment {
            val fragment = WeatherFragment()

            val args = Bundle()

            args.putParcelable(PARAMS_WEATHER_CITY, weatherCity)

            fragment.arguments = args

            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* [PARAMS_WEATHER_CITY] should always contain a value.*/
        mWeatherCity = arguments?.getParcelable(PARAMS_WEATHER_CITY) as WeatherCity

        loadingProgressBar?.visibility = View.VISIBLE

        mWeatherViewModel = ViewModelProviders.of(this, mFactory)
                .get(WeatherViewModel::class.java)
        val location = Location("")
        location.latitude = mWeatherCity.latitude
        location.longitude = mWeatherCity.longitude

        getDataFromLocation(location)
    }


    private fun getDataFromLocation(location: Location) {
        val resourceLiveData = mWeatherViewModel
                .getWeatherData(location)


        resourceLiveData.observe(this, Observer<Resource<Weather>> { weatherEntityResource ->
            if (weatherEntityResource != null && weatherEntityResource.status == Status.SUCCESS) {
                weatherEntityResource.data?.let {
                    bindData(it)
                }
            }
        })
    }


    private fun bindData(weather: Weather) {
        cityText?.text = weather.city?.name
        timeText?.text = DateFormat.format("EEEE, hh:mm a", Calendar.getInstance()
                .timeInMillis)

        val temperatureInCelsius = getString(R.string.temp_in_celsius,
                UnitConvUtil.kelvinToCelsius(weather.temperature))

        weatherTemperatureText?.text = temperatureInCelsius

        val timeInMills = weather.sunriseTime * 1000

        sunriseText?.text = DateFormat.format("hh.mm", timeInMills)
        windText?.text = getString(R.string.wind_speed_in_miles, weather.windSpeed)
        temperatureText?.text = temperatureInCelsius


        weatherStatusImage?.setImageResource(getWeatherIcon(weather.weatherIcon))

        detailContainer?.alpha = 0f
        detailContainer?.visibility = View.VISIBLE
        loadingProgressBar?.visibility = View.GONE

        detailContainer?.animate()?.alpha(1f)?.setDuration(400)?.start()
    }


    @DrawableRes
    private fun getWeatherIcon(icon: String?): Int {
        when (icon) {
            "01d", "01n" -> return R.drawable.clear_sky

            "02d", "02n" -> return R.drawable.few_clouds

            "03d", "03n" -> return R.drawable.scattered_clouds

            "04d", "04n" -> return R.drawable.broken_clouds

            "09d", "09n" -> return R.drawable.shower_rain


            "10d", "10n" -> return R.drawable.rain


            "11d", "11n" -> return R.drawable.thunderstorm


            "13d", "13n" -> return R.drawable.snow


            "50d", "50n" -> return R.drawable.mist

            else -> return R.drawable.clear_sky
        }
    }


}
