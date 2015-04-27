package edu.usf.cutr.trackerlib.tracker;

import android.content.Context;
import android.location.Location;

import edu.usf.cutr.trackerlib.location.LocationManager;
import edu.usf.cutr.trackerlib.utils.Logger;

/**
 * Created by cagricetin on 4/20/15.
 */
public class DeviceTracker implements LocationManager.Callback{

    private TrackerBehavior trackerBehavior;

    private Context applicationContext;

    private LocationManager locationManager;

    protected DeviceTracker(TrackerBehavior trackerBehavior, Context applicationContext) {
        this.trackerBehavior = trackerBehavior;
        this.applicationContext = applicationContext;
    }

    public void setTrackerBehavior(TrackerBehavior trackerBehavior) {
        this.trackerBehavior = trackerBehavior;
    }

    public void initTracker() {
        initLocationProvider();

        trackerBehavior.initTracker();
    }

    private void initLocationProvider() {
        locationManager = new LocationManager(applicationContext, this);
    }

    public void startTracker() {
        locationManager.startTracker();

        trackerBehavior.startTracker();
    }

    public void stopTracker() {
        locationManager.stopTracker();

        trackerBehavior.stopTracker();
    }

    @Override
    public void onLocationChanged(Location location) {
        Logger.debug("Location update: " + location.toString());
    }
}