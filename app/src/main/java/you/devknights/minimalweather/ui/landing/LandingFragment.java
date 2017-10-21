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

package you.devknights.minimalweather.ui.landing;


import android.Manifest;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import you.devknights.minimalweather.R;
import you.devknights.minimalweather.database.entity.WeatherEntity;
import you.devknights.minimalweather.model.Resource;
import you.devknights.minimalweather.model.Status;
import you.devknights.minimalweather.repo.weather.WeatherRepository;
import you.devknights.minimalweather.util.UnitConvUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class LandingFragment extends Fragment {

    private static final int PERMISSION_REQUEST_CODE = 1420;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    private static final String TAG = "LandingFragment";

    private TextView mCityText;
    private TextView mTimeText;

    private ImageView mWeatherStatusImage;
    private TextView mWeatherTemperatureText;
    private TextView mTimeReleatedText;

    private TextView mSunriseText;
    private TextView mWindText;
    private TextView mTemperatureText;


    private LandingViewModel mLandingViewModel;


    public LandingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_landing, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCityText = (TextView) view.findViewById(R.id.cityText);
        mTimeText = (TextView) view.findViewById(R.id.timeText);
        mWeatherStatusImage = (ImageView) view.findViewById(R.id.weatherStatusImage);
        mWeatherTemperatureText = (TextView) view.findViewById(R.id.weatherTemperatureText);

        mTimeReleatedText = (TextView) view.findViewById(R.id.timeRelatedMessageText);
        mSunriseText = (TextView) view.findViewById(R.id.sunriseText);
        mWindText = (TextView) view.findViewById(R.id.windText);
        mTemperatureText = (TextView) view.findViewById(R.id.temperatureText);

        mLandingViewModel = ViewModelProviders.of(this).get(LandingViewModel.class);

        if (!isPermissionGranted()) {
            requestPermissions(PERMISSIONS, PERMISSION_REQUEST_CODE);
        } else {
            startLocationUpdates();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        }
    }

    private void startLocationUpdates() {
        mLandingViewModel.getLocation().observe(this, this::getDataFromLocation);
    }

    private void getDataFromLocation(Location location) {
        LiveData<Resource<WeatherEntity>> resourceLiveData = WeatherRepository.getInstance()
                .getWeatherInfoAsLiveData(location);

        resourceLiveData.observe(this, weatherEntityResource -> {
            if (weatherEntityResource != null && weatherEntityResource.status == Status.SUCCESS) {
                bindData(weatherEntityResource.data);
            }
        });

    }


    private void bindData(WeatherEntity weather) {
        mCityText.setText(weather.getPlaceName());
        mTimeText.setText(DateFormat.format("EEEE, hh:mm a", Calendar.getInstance()
                .getTimeInMillis()));

        String temperatureInCelsius = getString(R.string.temp_in_celsius,
                UnitConvUtil.kelvinToCelsius(weather.getTemperature()));

        mWeatherTemperatureText.setText(temperatureInCelsius);

        long timeInMills = weather.getSunriseTime() * 1000;

        mSunriseText.setText(DateFormat.format("hh.mm", timeInMills));
        mWindText.setText(getString(R.string.wind_speed_in_miles, weather.getWindSpeed()));
        mTemperatureText.setText(temperatureInCelsius);


        mWeatherStatusImage.setImageResource(getWeatherIcon(weather.getWeatherIcon()));
    }

    private boolean isPermissionGranted() {
        return ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }



    private
    @DrawableRes
    int getWeatherIcon(String icon) {
        switch (icon) {
            case "01d":
            case "01n":
                return R.drawable.clear_sky;

            case "02d":
            case "02n":
                return R.drawable.few_clouds;

            case "03d":
            case "03n":
                return R.drawable.scattered_clouds;

            case "04d":
            case "04n":
                return R.drawable.broken_clouds;

            case "09d":
            case "09n":
                return R.drawable.shower_rain;


            case "10d":
            case "10n":
                return R.drawable.rain;


            case "11d":
            case "11n":
                return R.drawable.thunderstorm;


            case "13d":
            case "13n":
                return R.drawable.snow;


            case "50d":
            case "50n":
                return R.drawable.mist;

            default:
                return R.drawable.clear_sky;
        }
    }
}
