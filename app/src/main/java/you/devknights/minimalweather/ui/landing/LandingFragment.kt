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

package you.devknights.minimalweather.ui.landing


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_landing.*

import java.util.Calendar

import javax.inject.Inject

import you.devknights.minimalweather.R
import you.devknights.minimalweather.database.entity.WeatherEntity
import you.devknights.minimalweather.di.Injectable
import you.devknights.minimalweather.model.Resource
import you.devknights.minimalweather.model.Status
import you.devknights.minimalweather.util.UnitConvUtil


/**
 * A simple [Fragment] subclass.
 */
class LandingFragment : Fragment(), Injectable {

    @Inject
    lateinit var mFactory: ViewModelProvider.Factory

    @Inject
    lateinit var mLandingViewModel: LandingViewModel



    private val isPermissionGranted: Boolean
        get() = ActivityCompat.checkSelfPermission(context as Context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context as Context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_landing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingProgressBar?.visibility = View.VISIBLE

        mLandingViewModel = ViewModelProviders.of(this, mFactory)
                .get(LandingViewModel::class.java)

        mLandingViewModel.syncCityData()

        if (!isPermissionGranted) {
            requestPermissions(PERMISSIONS, PERMISSION_REQUEST_CODE)
        } else {
            startLocationUpdates()
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates()
        }
    }

    private fun startLocationUpdates() {
        mLandingViewModel.location.observe(this, Observer<Location> {
            it?.let {
                this.getDataFromLocation(it)
            }
        })
    }

    private fun getDataFromLocation(location: Location) {
        val resourceLiveData = mLandingViewModel
                .getWeatherData(location)


        resourceLiveData.observe(this, Observer<Resource<WeatherEntity>> { weatherEntityResource ->
            if (weatherEntityResource != null && weatherEntityResource.status == Status.SUCCESS) {
                weatherEntityResource.data?.let {
                    bindData(it)
                }
            }
        })
    }


    private fun bindData(weather: WeatherEntity) {
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

    companion object {

        private val PERMISSION_REQUEST_CODE = 1420
        private val PERMISSIONS = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)

        private val TAG = "LandingFragment"
    }
}// Required empty public constructor
