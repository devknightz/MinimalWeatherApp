package you.devknights.minimalweather.db

import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.Rule
import you.devknights.minimalweather.database.WeatherDatabase
import java.util.concurrent.TimeUnit

abstract class DbTest {
    @Rule
    @JvmField
    val countingTaskExecutorRule = CountingTaskExecutorRule()
    private lateinit var _db: WeatherDatabase
    val db: WeatherDatabase
        get() = _db


    @Before
    fun initDB() {
        _db = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(), WeatherDatabase::class.java)
                .build()
    }


    @After
    fun closeDB() {
        countingTaskExecutorRule.drainTasks(10, TimeUnit.SECONDS)
        _db.close()
    }
}