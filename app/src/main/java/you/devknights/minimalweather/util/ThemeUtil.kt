package you.devknights.minimalweather.util

import androidx.appcompat.app.AppCompatDelegate

object ThemeUtil {

    fun setAutoNightMode(mode: Int = AppCompatDelegate.MODE_NIGHT_NO) {
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}