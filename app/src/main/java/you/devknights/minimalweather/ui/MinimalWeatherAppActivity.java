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

package you.devknights.minimalweather.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import you.devknights.minimalweather.R;

/**
 * This will be the Base {@link AppCompatActivity} for all the Activity in the App
 * @author vinayagasundar
 */
public abstract class MinimalWeatherAppActivity extends AppCompatActivity {


    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(null);
    }


    /**
     * Return the layout resource ID which used in {@link #setContentView(int)}
     * @return
     */
    @LayoutRes
    public abstract int getLayoutResId();
}
