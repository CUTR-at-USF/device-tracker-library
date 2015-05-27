/*
 * Copyright (C) 2015 Cagri Cetin (cagricetin@mail.usf.edu), University of South Florida
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.usf.cutr.trackerlib.location;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Manages location tracking events
 * Implements Fused location listeners
 */
public class LocationManager implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public interface Callback {

        /**
         * Location change callback
         * Tracks devices location
         * @param location default location object
         */
        void onLocationChanged(Location location);
    }

    //Fastest location update interval set to 30 seconds
    private static final long FASTEST_INTERVAL = 1000 * 30;

    private LocationRequest mLocationRequest;

    private GoogleApiClient mGoogleApiClient;

    private Callback callback;

    private boolean isConnected = false;

    /**
     * Init location manager
     * @param applicationContext takes app context
     * @param callback for the location updates
     */
    public LocationManager(Context applicationContext, Callback callback) {
        this.callback = callback;

        createLocationRequest();

        mGoogleApiClient = new GoogleApiClient.Builder(applicationContext)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    /**
     * starts location updates
     */
    public void startTracker() {
        mGoogleApiClient.connect();
    }

    /**
     * stops location updates
     */
    public void stopTracker() {
        mGoogleApiClient.disconnect();

        stopLocationUpdates();
    }

    @Override
    public void onLocationChanged(Location location) {
        callback.onLocationChanged(location);
    }

    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdates();
        isConnected = true;
    }

    @Override
    public void onConnectionSuspended(int i) {
        isConnected = false;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        isConnected = false;
    }

    private void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    private void stopLocationUpdates() {
        if (isConnected){
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }
}
