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

package you.devknights.minimalweather.ui.landing

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment

import javax.inject.Inject

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_landing.*
import you.devknights.minimalweather.R
import you.devknights.minimalweather.ui.MinimalWeatherAppActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import you.devknights.minimalweather.ui.weather.WeatherFragment
import you.devknights.minimalweather.util.ThemeUtil


/**
 * This will be the Landing screen of the App.
 * @author vinayagasundar
 */
class LandingActivity : MinimalWeatherAppActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>


    override val layoutResId: Int
        get() = R.layout.activity_landing

    var isTouchByUser = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, WeatherFragment())
                    .commit()
        }

        setUpBottomDrawer(parent_container as View)

        switch_auto_night_mode.setOnTouchListener { _, _ ->
            isTouchByUser = true
            false
        }

        switch_auto_night_mode.setOnCheckedChangeListener { _, isChecked ->

           if (isTouchByUser) {
               isTouchByUser = false
               if (isChecked) {
                   ThemeUtil.setAutoNightMode(isChecked)
               } else {
                   ThemeUtil.setAutoNightMode(false, AppCompatDelegate.MODE_NIGHT_YES)
               }

               recreate()
           }
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return dispatchingAndroidInjector
    }

    private fun setUpBottomDrawer(view: View) {
        val bottomDrawer: View = view.findViewById(R.id.bottom_drawer)
        val bottomDrawerBehavior = BottomSheetBehavior.from<View>(bottomDrawer)
        bottomDrawerBehavior.state = BottomSheetBehavior.STATE_HIDDEN


        bottom_bar.setOnMenuItemClickListener {
            it?.let {
                if (it.itemId == R.id.more_info) {
                    bottomDrawerBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
                    return@setOnMenuItemClickListener true
                }
            }

            false
        }

        bottom_bar.replaceMenu(R.menu.bottom_bar_menu)
    }
}
