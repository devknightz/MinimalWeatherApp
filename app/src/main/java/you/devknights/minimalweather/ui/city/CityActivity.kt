package you.devknights.minimalweather.ui.city

import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import you.devknights.minimalweather.R
import you.devknights.minimalweather.ui.MinimalWeatherAppActivity
import you.devknights.minimalweather.ui.city.select.SelectCityFragment
import javax.inject.Inject

class CityActivity : MinimalWeatherAppActivity(), HasSupportFragmentInjector {


    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override val layoutResId = R.layout.activity_city


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.city_container, SelectCityFragment())
                    .commit()
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }
}