package edu.usf.cutr.trackerlib.tracker;

import android.content.Context;

import edu.usf.cutr.trackerlib.data.NMEASentence;
import edu.usf.cutr.trackerlib.data.TrackerConfig;
import edu.usf.cutr.trackerlib.io.TrackerAnalytics;
import edu.usf.cutr.trackerlib.server.TraccarServerImpl;
import edu.usf.cutr.trackerlib.server.TrackerServer;
import edu.usf.cutr.trackerlib.tracker.batch.BatchTrackerImpl;
import edu.usf.cutr.trackerlib.tracker.realtime.RealTimeTrackerImpl;
import edu.usf.cutr.trackerlib.utils.DeviceUtils;
import edu.usf.cutr.trackerlib.utils.Logger;

/**
 * DeviceTrackerManager manages all the tracking events in an application
 */
public class DeviceTrackerManager {

    /**
     * DeviceTrackerManager is a singleton class
     */
    private static DeviceTracker deviceTracker;

    public static void init(TrackerConfig config, Context applicationContext, String uuid) {

        //Use traccar server as a server
        TrackerServer trackerServer = new TraccarServerImpl(config, NMEASentence.GPRMC);

        //Save Unique device id
        DeviceUtils.saveDeviceId(uuid, applicationContext);

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

    /**
     * Optional Google analytics implementation
     * Starts events when initialized
     * @param applicationContext
     * @param trackerId
     */
    public static void initAnalytics(Context applicationContext, String trackerId){
        TrackerAnalytics.init(applicationContext, trackerId);
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

    public static void cancelTracker() {
        if (deviceTracker != null) {
            deviceTracker.cancelTracker();
            Logger.debug("Device tracking cancelled");
        }
    }

    public static void updateTrackerConfig(TrackerConfig trackerConfig){
        if (deviceTracker != null) {
            deviceTracker.updateTrackerConfig(trackerConfig);
        }
    }
}
