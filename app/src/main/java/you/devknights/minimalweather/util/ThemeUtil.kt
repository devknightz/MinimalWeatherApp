package you.devknights.minimalweather.util

import androidx.appcompat.app.AppCompatDelegate

object ThemeUtil {

    fun setAutoNightMode(isAutoNightModeOn: Boolean, mode: Int = AppCompatDelegate.MODE_NIGHT_NO) {
       if (isAutoNightModeOn) {
           AppCompatDelegate.setDefaultNightMode(
                   AppCompatDelegate.MODE_NIGHT_AUTO)
       } else {
           AppCompatDelegate.setDefaultNightMode(mode)
       }
    }
}