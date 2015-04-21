package edu.usf.cutr.trackerlib.location;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by cagricetin on 4/21/15.
 */
public class LocationManager implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public interface Callback {

        public void onLocationChanged(Location location);
    }

    //Fastest location update interval set to 30 seconds
    private static final long FASTEST_INTERVAL = 1000 * 30;

    private LocationRequest mLocationRequest;

    private GoogleApiClient mGoogleApiClient;

    private Callback callback;

    public LocationManager(Context applicationContext, Callback callback) {
        this.callback = callback;

        createLocationRequest();

        mGoogleApiClient = new GoogleApiClient.Builder(applicationContext)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    public void startTracker() {
        mGoogleApiClient.connect();
    }

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
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private void startLocationUpdates() {
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }
}
