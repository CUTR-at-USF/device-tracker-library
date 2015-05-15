package edu.usf.cutr.trackerlib.tracker;

import android.content.Context;
import android.location.Location;

import edu.usf.cutr.trackerlib.R;
import edu.usf.cutr.trackerlib.data.TrackerConfig;
import edu.usf.cutr.trackerlib.io.TrackerAnalytics;
import edu.usf.cutr.trackerlib.location.LocationManager;

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

        TrackerAnalytics.reportEventWithCategory(TrackerAnalytics.EventCategory.APP_SETTINGS.toString(),
                applicationContext.getString(R.string.analytics_action_init),
                applicationContext.getString(R.string.analytics_label_init_tracking));
    }

    public void updateTrackerConfig(TrackerConfig trackerConfig){
        trackerBehavior.updateTrackerConfig(trackerConfig);
    }

    private void initLocationProvider() {
        locationManager = new LocationManager(applicationContext, this);
    }

    public void startTracker() {
        locationManager.startTracker();

        trackerBehavior.startTracker();

        TrackerAnalytics.reportEventWithCategory(TrackerAnalytics.EventCategory.APP_SETTINGS.toString(),
                applicationContext.getString(R.string.analytics_action_init),
                applicationContext.getString(R.string.analytics_label_start_tracking));
    }

    public void stopTracker() {
        locationManager.stopTracker();

        trackerBehavior.stopTracker();

        TrackerAnalytics.reportEventWithCategory(TrackerAnalytics.EventCategory.APP_SETTINGS.toString(),
                applicationContext.getString(R.string.analytics_action_init),
                applicationContext.getString(R.string.analytics_label_stop_tracking));
    }

    public void cancelTracker() {
        trackerBehavior.cancelTracker();

        TrackerAnalytics.reportEventWithCategory(TrackerAnalytics.EventCategory.APP_SETTINGS.toString(),
                applicationContext.getString(R.string.analytics_action_init),
                applicationContext.getString(R.string.analytics_label_cancel_tracking));
    }

    @Override
    public void onLocationChanged(Location location) {
        trackerBehavior.onLocationUpdate(location);

        TrackerAnalytics.reportEventWithCategory(TrackerAnalytics.EventCategory.TRACE.toString(),
                applicationContext.getString(R.string.analytics_action_trace),
                applicationContext.getString(R.string.analytics_label_location_update) +
        " lat:" +location.getLatitude() + " - long:" + location.getLongitude() );
    }
}