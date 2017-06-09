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

package you.devknights.minimalweather.model;

/**
 * This interface gives the details of weather
 *
 * @author Randhir
 * @since 6/5/2017.
 */

public interface Weather {
    int getPlaceId();

    String getPlaceName();

    double getPlaceLat();

    double getPlaceLon();

    int getWeatherId();

    String getWeatherMain();

    String getWeatherDescription();

    String getWeatherIcon();

    float getTemperature();

    float getPressure();

    float getHumidity();

    float getWindSpeed();

    long getSunriseTime();

    long getSunsetTime();

    long getStartTime();

    long getEndTime();

}
