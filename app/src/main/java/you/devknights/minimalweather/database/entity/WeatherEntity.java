/*
 * Copyright 2017 vinayagasundar
 * Copyright 2017 randhirgupta
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package you.devknights.minimalweather.database.entity;

import you.devknights.minimalweather.model.Weather;

/**
 * @author Randhir
 * @since 6/6/2017.
 */

public class WeatherEntity implements Weather {

    ///////////////////////////////////////////////////////////////////////////
    // Place information
    ///////////////////////////////////////////////////////////////////////////

    private int placeId;
    private String placeName;
    private double placeLat;
    private double placeLon;

    ///////////////////////////////////////////////////////////////////////////
    // Weather Information
    ///////////////////////////////////////////////////////////////////////////

    private int weatherId;
    private String weatherMain;
    private String weatherDescription;
    private String weatherIcon;


    ///////////////////////////////////////////////////////////////////////////
    // Temperature, wind , pressure information
    ///////////////////////////////////////////////////////////////////////////

    private float temperature;
    private float pressure;
    private float humidity;
    private float windSpeed;


    ///////////////////////////////////////////////////////////////////////////
    // Sunrise and sunset timing information
    ///////////////////////////////////////////////////////////////////////////

    private long sunriseTime;
    private long sunsetTime;
    private long startTime;
    private long endTime;

    @Override
    public int getPlaceId() {
        return placeId;
    }

    @Override
    public String getPlaceName() {
        return placeName;
    }

    @Override
    public double getPlaceLat() {
        return placeLat;
    }

    @Override
    public double getPlaceLon() {
        return placeLon;
    }

    @Override
    public int getWeatherId() {
        return weatherId;
    }

    @Override
    public String getWeatherMain() {
        return weatherMain;
    }

    @Override
    public String getWeatherDescription() {
        return weatherDescription;
    }

    @Override
    public String getWeatherIcon() {
        return weatherIcon;
    }

    @Override
    public float getTemperature() {
        return temperature;
    }

    @Override
    public float getPressure() {
        return pressure;
    }

    @Override
    public float getHumidity() {
        return humidity;
    }

    @Override
    public float getWindSpeed() {
        return windSpeed;
    }

    @Override
    public long getSunriseTime() {
        return sunriseTime;
    }

    @Override
    public long getSunsetTime() {
        return sunsetTime;
    }

    @Override
    public long getStartTime() {
        return startTime;
    }

    @Override
    public long getEndTime() {
        return endTime;
    }
}
