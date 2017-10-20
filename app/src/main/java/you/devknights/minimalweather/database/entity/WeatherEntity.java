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

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import you.devknights.minimalweather.database.WeatherDatabase;
import you.devknights.minimalweather.model.Weather;

/**
 * @author Randhir
 * @since 6/6/2017.
 */

@Entity(tableName = WeatherDatabase.TABLE_WEATHER)
public class WeatherEntity implements Weather {

    @PrimaryKey(autoGenerate = true)
    private long _id;

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

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    @Override
    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    @Override
    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    @Override
    public double getPlaceLat() {
        return placeLat;
    }

    public void setPlaceLat(double placeLat) {
        this.placeLat = placeLat;
    }

    @Override
    public double getPlaceLon() {
        return placeLon;
    }

    public void setPlaceLon(double placeLon) {
        this.placeLon = placeLon;
    }

    @Override
    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    @Override
    public String getWeatherMain() {
        return weatherMain;
    }

    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
    }

    @Override
    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    @Override
    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    @Override
    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    @Override
    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    @Override
    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    @Override
    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    @Override
    public long getSunriseTime() {
        return sunriseTime;
    }

    public void setSunriseTime(long sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    @Override
    public long getSunsetTime() {
        return sunsetTime;
    }

    public void setSunsetTime(long sunsetTime) {
        this.sunsetTime = sunsetTime;
    }

    @Override
    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
