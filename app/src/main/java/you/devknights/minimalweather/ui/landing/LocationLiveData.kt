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

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

import javax.inject.Inject

/**
 * @author vinayagasundar
 */
class LocationLiveData @Inject
constructor(private val mContext: Context) : LiveData<Location>() {

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)

            val location = locationResult?.lastLocation
            value = location

            removeCallback()
        }


        override fun onLocationAvailability(locationAvailability: LocationAvailability?) {
            super.onLocationAvailability(locationAvailability)

            if (locationAvailability?.isLocationAvailable != null) {
                getLastKnownLocation()
            }
        }
    }

    private val isPermissionGranted: Boolean
        get() = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private val fusedLocationProviderClient: FusedLocationProviderClient
        get() {
            if (mFusedLocationProviderClient == null) {
                mFusedLocationProviderClient = LocationServices
                        .getFusedLocationProviderClient(mContext)
            }

            return mFusedLocationProviderClient as FusedLocationProviderClient
        }


    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        if (!isPermissionGranted) {
            return
        }

        val locationProviderClient = fusedLocationProviderClient
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val looper = Looper.myLooper()

        locationProviderClient.requestLocationUpdates(locationRequest, mLocationCallback, looper)

    }


    override fun onInactive() {
        super.onInactive()
        removeCallback()
    }

    private fun removeCallback() {
        val locationProviderClient = fusedLocationProviderClient
        locationProviderClient.removeLocationUpdates(mLocationCallback)
    }


    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation() {
        fusedLocationProviderClient.lastLocation
                .addOnCompleteListener { task ->

                    task.result?.let {
                        value = it
                    }
                    removeCallback()
                }
    }
}
