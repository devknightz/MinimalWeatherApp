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

package you.devknights.minimalweather.ui.landing;

import android.Manifest;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import javax.inject.Inject;

/**
 * @author vinayagasundar
 */
@SuppressWarnings("MissingPermission")
public class LocationLiveData extends LiveData<Location> {

    private final Context mContext;

    private FusedLocationProviderClient mFusedLocationProviderClient = null;

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            Location location = locationResult.getLastLocation();
            setValue(location);

            removeCallback();
        }


        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability) {
            super.onLocationAvailability(locationAvailability);

            if (locationAvailability.isLocationAvailable()) {
                getLastKnownLocation();
            }
        }
    };

    @Inject
    public LocationLiveData(Context context) {
        this.mContext = context;
    }


    @Override
    protected void onActive() {
        super.onActive();
        if (!isPermissionGranted()) {
            return;
        }

        FusedLocationProviderClient locationProviderClient = getFusedLocationProviderClient();
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        Looper looper = Looper.myLooper();

        locationProviderClient.requestLocationUpdates(locationRequest, mLocationCallback, looper);

    }


    @Override
    protected void onInactive() {
        super.onInactive();
        removeCallback();
    }

    private void removeCallback() {
        FusedLocationProviderClient locationProviderClient = getFusedLocationProviderClient();
        locationProviderClient.removeLocationUpdates(mLocationCallback);
    }

    private boolean isPermissionGranted() {
        return ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    private FusedLocationProviderClient getFusedLocationProviderClient() {
        if (mFusedLocationProviderClient == null) {
            mFusedLocationProviderClient = LocationServices
                    .getFusedLocationProviderClient(mContext);
        }

        return mFusedLocationProviderClient;
    }


    private void getLastKnownLocation() {
        getFusedLocationProviderClient().getLastLocation()
                .addOnCompleteListener(task -> {
                    Location location = task.getResult();
                    // Fail safe
                    if (location == null) {
                        return;
                    }

                    setValue(location);
                    removeCallback();
                });
    }
}
