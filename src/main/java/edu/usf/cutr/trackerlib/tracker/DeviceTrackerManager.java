package edu.usf.cutr.trackerlib.tracker;

import android.content.Context;

import edu.usf.cutr.trackerlib.data.TrackerConfig;
import edu.usf.cutr.trackerlib.server.TraccarServer;

/**
 * DeviceTrackerManager manages all the tracking events in an application
 */
public class DeviceTrackerManager {

    /**
     * DeviceTrackerManager is a singleton class
     */
    private static DeviceTracker deviceTracker;

    public static void init(TrackerConfig config, Context applicationContext) {

        //TODO: check if Google play services is active
        BaseTracker baseTracker;
        if (TrackerConfig.TrackerType.REAL_TIME.equals(config.getTrackerType())){
            baseTracker = new RealTimeTracker(new TraccarServer(config.getTrackerServerUrl()));
        } else {
            baseTracker = new BatchTracker(new TraccarServer(config.getTrackerServerUrl()));
        }
        deviceTracker = new DeviceTracker(baseTracker, applicationContext);
        deviceTracker.initTracker();
    }

    public static void startTracker(){
        if (deviceTracker != null) {
            deviceTracker.startTracker();
        }
    }

    public static void stopTracker() {
        if (deviceTracker != null) {
            deviceTracker.stopTracker();
        }
    }

}
