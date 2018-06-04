package you.devknights.minimalweather.database

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KeyValueStore
@Inject constructor(private val preferences: SharedPreferences) {

    companion object {
        const val KEY_CITY_SYNC_TIME = "city_sync_time";
    }

    fun getCitySyncTime() = preferences.getLong(KEY_CITY_SYNC_TIME, -1)

    fun setCitySyncTime(value: Long) = preferences
            .edit()?.putLong(KEY_CITY_SYNC_TIME, value)?.apply()

}