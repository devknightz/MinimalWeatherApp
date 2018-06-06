package you.devknights.minimalweather.database

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KeyValueStore
@Inject constructor(private val preferences: SharedPreferences) {

    companion object {
        const val KEY_CITY_SYNC_TIME = "city_sync_time"
        const val KEY_AUTO_NIGHT_MODE = "auto_night_mode";
    }

    fun getCitySyncTime() = preferences.getLong(KEY_CITY_SYNC_TIME, -1)

    fun setCitySyncTime(value: Long) = preferences
            .edit()?.putLong(KEY_CITY_SYNC_TIME, value)?.apply()


    fun setAutoNightMode(value: Int) = preferences
            .edit()?.putInt(KEY_AUTO_NIGHT_MODE, value)?.apply()


    fun getAutoNightMode() = preferences.getInt(KEY_AUTO_NIGHT_MODE, 0 /*MODE_NIGHT_AUTO = 0*/)

}