package you.devknights.minimalweather.ui.landing

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import you.devknights.minimalweather.core.executor.AppExecutors
import you.devknights.minimalweather.database.KeyValueStore
import javax.inject.Inject

class LandingViewModel
@Inject constructor(application: Application,
                    private val keyValueStore: KeyValueStore,
                    private val appExecutors: AppExecutors) : AndroidViewModel(application) {


    fun changeAutoNightMode(enableAutoNightMode: Boolean): LiveData<Int> {
        val data = MutableLiveData<Int>()

        appExecutors.runOnDisk {
            var nightMode: Int = AppCompatDelegate.MODE_NIGHT_NO

            if (enableAutoNightMode) {
                nightMode = AppCompatDelegate.MODE_NIGHT_AUTO
            }

            keyValueStore.setAutoNightMode(nightMode)

            data.postValue(nightMode)
        }

        return data
    }


    fun isAutoNightModeEnabled(): LiveData<Boolean> {
        val data = MutableLiveData<Boolean>()

        appExecutors.runOnDisk {
            val nightMode = keyValueStore.getAutoNightMode()

            data.postValue(nightMode == AppCompatDelegate.MODE_NIGHT_AUTO)
        }

        return data
    }
}