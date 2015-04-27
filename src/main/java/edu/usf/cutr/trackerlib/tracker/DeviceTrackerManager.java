package edu.usf.cutr.trackerlib.tracker;

import android.content.Context;

import edu.usf.cutr.trackerlib.data.TrackerConfig;
import edu.usf.cutr.trackerlib.server.TraccarServerImpl;
import edu.usf.cutr.trackerlib.server.TrackerServer;
import edu.usf.cutr.trackerlib.tracker.batch.BatchTrackerImpl;
import edu.usf.cutr.trackerlib.tracker.realtime.RealTimeTrackerImpl;
import edu.usf.cutr.trackerlib.utils.Logger;

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

        //Use traccar server as a server
        TrackerServer trackerServer = new TraccarServerImpl(config.getTrackerServerUrl());

        BaseTracker baseTracker;
        if (TrackerConfig.TrackerType.REAL_TIME.equals(config.getTrackerType())){
            baseTracker = new RealTimeTrackerImpl(trackerServer, applicationContext);
        } else {
            baseTracker = new BatchTrackerImpl(trackerServer, applicationContext);
        }

        //Initialize a deviceTracker with a tracker behavior
        deviceTracker = new DeviceTracker(baseTracker, applicationContext);
        deviceTracker.initTracker();

        Logger.debug("Trackerlib initialized");
    }

    public static void startTracker(){
        if (deviceTracker != null) {
            deviceTracker.startTracker();

            Logger.debug("Device tracking started");
        }
    }

    public static void stopTracker() {
        if (deviceTracker != null) {
            deviceTracker.stopTracker();

            Logger.debug("Device tracking stopped");
        }
    }

}
