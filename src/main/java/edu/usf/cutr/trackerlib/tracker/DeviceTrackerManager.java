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

    /**
     * Initialize device tracker, needed to be called when the application starts
     * @param config Tracker config object contains ip address of the server
     * @param applicationContext Android application context
     * @param uuid unique identifier for the device
     */
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
     * @param applicationContext Android application context
     * @param trackerId Google analytics tracker id
     */
    public static void initAnalytics(Context applicationContext, String trackerId){
        TrackerAnalytics.init(applicationContext, trackerId);
    }

    /**
     * Starts tracker
     */
    public static void startTracker(){
        if (deviceTracker != null) {
            deviceTracker.startTracker();
            Logger.debug("Device tracking started");
        }
    }

    /**
     * Stops tracker
     * If tracker configured with batch updates, keeps the batch updates
     */
    public static void stopTracker() {
        if (deviceTracker != null) {
            deviceTracker.stopTracker();
            Logger.debug("Device tracking stopped");
        }
    }

    /**
     * Stops the tracker and also cancels the batch updates
     */
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