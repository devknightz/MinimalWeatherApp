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

package you.devknights.minimalweather.api;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import you.devknights.minimalweather.BuildConfig;
import you.devknights.minimalweather.database.entity.WeatherEntity;
import you.devknights.minimalweather.network.ApiService;
import you.devknights.minimalweather.network.LiveDataCallAdapterFactory;
import you.devknights.minimalweather.network.model.WeatherResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static you.devknights.minimalweather.util.LiveDataTestUtil.getValue;

/**
 * @author vinayagasundar
 */

public class ApiServiceTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private ApiService apiService;

    private MockWebServer mockWebServer;


    @Before
    public void createService() {
        mockWebServer = new MockWebServer();

        apiService = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(ApiService.class);
    }

    @After
    public void stopService() throws IOException {
        mockWebServer.shutdown();
    }


    @Test
    public void getWeatherDataForLocation() throws IOException, InterruptedException {
        enqueueResponse("weather_data.json");

        double latitude = 76.95;
        double longitude = 28.69;
        String apiKey = BuildConfig.OPEN_WEATHER_API_KEY;

        String pathValue = String.format(Locale.ENGLISH,
                "/weather?lat=%.2f&lon=%.2f&appid=%s", latitude, longitude, apiKey);

        WeatherResponse weatherResponse = getValue(apiService
                .getWeatherDataWithLocationCall(latitude, longitude, apiKey)).body;

        WeatherEntity weatherEntity = weatherResponse.buildWeather();

        RecordedRequest request = mockWebServer.takeRequest();
        assertThat(request.getPath(), is(pathValue));
        assertThat(weatherEntity, notNullValue());
        assertThat(weatherEntity.getPlaceName(), is("Bahadurgarh"));
        assertThat(weatherEntity.getTemperature(), is(306.6f));

    }


    private void enqueueResponse(String fileName) throws IOException {
        enqueueResponse(fileName, Collections.emptyMap());
    }


    private void enqueueResponse(String fileName, Map<String, String> headers) throws IOException {
        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("api-response/" + fileName);
        BufferedSource source = Okio.buffer(Okio.source(inputStream));
        MockResponse response = new MockResponse();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            response.addHeader(header.getKey(), header.getValue());
        }

        mockWebServer.enqueue(response.setBody(source.readString(StandardCharsets.UTF_8)));
    }
}
