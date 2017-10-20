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


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import you.devknights.minimalweather.R;
import you.devknights.minimalweather.database.AppDatabase;
import you.devknights.minimalweather.database.entity.WeatherEntity;
import you.devknights.minimalweather.model.Weather;
import you.devknights.minimalweather.network.ApiService;
import you.devknights.minimalweather.network.NetworkClient;
import you.devknights.minimalweather.network.model.WeatherResponse;
import you.devknights.minimalweather.util.UnitConvUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class LandingFragment extends Fragment {

    private static final String TAG = "LandingFragment";

    private TextView mCityText;
    private TextView mTimeText;

    private ImageView mWeatherStatusImage;
    private TextView mWeatherTemperatureText;
    private TextView mTimeReleatedText;

    private TextView mSunriseText;
    private TextView mWindText;
    private TextView mTemperatureText;


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

        new LoadWeatherData(new LoadWeatherData.Callback() {
            @Override
            public void onWeatherData(Weather weather) {
                bindData(weather);
            }
        }).execute();
    }


    private void bindData(Weather weather) {
        mCityText.setText(weather.getPlaceName());

        String temperatureInCelsius = getString(R.string.temp_in_celsius,
                UnitConvUtil.kelvinToCelsius(weather.getTemperature()));

        mWeatherTemperatureText.setText(temperatureInCelsius);

        long timeInMills = weather.getSunriseTime() * 1000;

        mSunriseText.setText(DateFormat.format("hh.mm", timeInMills));
        mWindText.setText(getString(R.string.wind_speed_in_miles, weather.getWindSpeed()));
        mTemperatureText.setText(temperatureInCelsius);
    }

    private static class LoadWeatherData extends AsyncTask<Void, Void, Weather> {

        private Callback callback;

        LoadWeatherData(Callback callback) {
            this.callback = callback;
        }

        @Override
        protected Weather doInBackground(Void... params) {
            Weather weather = null;

            ApiService apiService = NetworkClient.getApiService();
            Call<WeatherResponse> weatherResponseCall = apiService.getWeatherDataCall("Bangalore",
                    "63b2611ca2ad0bd3c881be68e0d7b7ab");

            try {
                Response<WeatherResponse> response = weatherResponseCall.execute();
                if (response != null) {
                    WeatherResponse weatherResponse = response.body();
                    if (weatherResponse != null) {
                        weather = weatherResponse.buildWeather();

                        long currentTime = System.currentTimeMillis();

                        ((WeatherEntity) weather).setStartTime(currentTime);
                        ((WeatherEntity) weather).setEndTime(currentTime + 1000);

                        AppDatabase.getInstance()
                                .getDatabase()
                                .weatherDAO()
                                .insert((WeatherEntity) weather);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            if (weather != null) {
                if (callback != null) {
                    callback.onWeatherData(weather);
                }
            }
        }

        interface Callback {
            void onWeatherData(Weather weather);
        }
    }
}
