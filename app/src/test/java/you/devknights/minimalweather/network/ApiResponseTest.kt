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

package you.devknights.minimalweather.network

import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.runners.MethodSorters

import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.hamcrest.Matchers.nullValue

/**
 * @author vinayagasundar
 */

@RunWith(JUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ApiResponseTest {

    @Test
    fun exception() {
        val exception = Exception("foo")
        val apiResponse = ApiResponse<String>(exception)
        assertThat<Map<String, String>>(apiResponse.links, notNullValue())
        assertThat<String>(apiResponse.body, nullValue())
        assertThat(apiResponse.code, `is`(500))
        assertThat<String>(apiResponse.errorMessage, `is`("foo"))
    }

    @Test
    fun success() {
        val apiResponse = ApiResponse(Response.success("foo"))
        assertThat<String>(apiResponse.errorMessage, nullValue())
        assertThat(apiResponse.code, `is`(200))
        assertThat<String>(apiResponse.body, `is`("foo"))
        assertThat<Int>(apiResponse.nextPage, `is`(nullValue()))
    }

    @Test
    fun error() {
        val apiResponse = ApiResponse(Response.error<String>(400, ResponseBody.create(MediaType.parse("application/txt"), "Not Found")))

        assertThat(apiResponse.code, `is`(400))
        assertThat<String>(apiResponse.body, nullValue())
        assertThat<String>(apiResponse.errorMessage, `is`("Not Found"))
    }
}
