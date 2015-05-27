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
package edu.usf.cutr.trackerlib.tracker;

import android.content.Context;
import android.location.Location;

import edu.usf.cutr.trackerlib.R;
import edu.usf.cutr.trackerlib.data.TrackerConfig;
import edu.usf.cutr.trackerlib.io.TrackerAnalytics;
import edu.usf.cutr.trackerlib.location.LocationManager;

/**
 * Controls location tracking events
 */
public class DeviceTracker implements LocationManager.Callback{

    private TrackerBehavior trackerBehavior;

    private Context applicationContext;

    private LocationManager locationManager;

    /**
     * Only can be used by library objects
     * @param trackerBehavior takes tracker behavior (batch or real-time)
     * @param applicationContext takes app context
     */
    protected DeviceTracker(TrackerBehavior trackerBehavior, Context applicationContext) {
        this.trackerBehavior = trackerBehavior;
        this.applicationContext = applicationContext;
    }

    /**
     * Initialize trackers and location tracking systems
     */
    public void initTracker() {
        initLocationProvider();

        trackerBehavior.initTracker();

        TrackerAnalytics.reportEventWithCategory(TrackerAnalytics.EventCategory.APP_SETTINGS.toString(),
                applicationContext.getString(R.string.analytics_action_init),
                applicationContext.getString(R.string.analytics_label_init_tracking));
    }

    /**
     * Updates tracker configs
     * @param trackerConfig takes config object
     */
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
    }
}