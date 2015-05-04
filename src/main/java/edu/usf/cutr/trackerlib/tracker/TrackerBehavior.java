package edu.usf.cutr.trackerlib.tracker;

import android.location.Location;

import edu.usf.cutr.trackerlib.data.TrackerConfig;

/**
 * Created by cagricetin on 4/20/15.
 */
public interface TrackerBehavior {

    public void initTracker();

    public void stopTracker();

    public void cancelTracker();

    public void onLocationUpdate(Location location);

    public void updateTrackerConfig(TrackerConfig trackerConfig);
}
