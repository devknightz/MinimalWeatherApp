package you.devknights.minimalweather.db

import androidx.test.runner.AndroidJUnit4
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import you.devknights.minimalweather.database.entity.City


@RunWith(AndroidJUnit4::class)
class CityDaoTest: DbTest() {


    @Test
    fun insertCity() {
        val city = City(1, "name", 0.1, 0.2, "IN")
        db.cityDAO().insert(city)
        val loaded = db.cityDAO().getCityById(1)

        assertThat(loaded.id, `is`(city.id))
        assertThat(loaded.name, `is`(city.name))
    }
}