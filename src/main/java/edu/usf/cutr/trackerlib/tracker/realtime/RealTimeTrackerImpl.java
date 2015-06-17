/*
 * Copyright (C) 2015 University of South Florida (cagricetin@mail.usf.edu)
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
package edu.usf.cutr.trackerlib.tracker.realtime;

import android.content.Context;
import android.location.Location;

import edu.usf.cutr.trackerlib.data.TrackData;
import edu.usf.cutr.trackerlib.data.TrackerConfig;
import edu.usf.cutr.trackerlib.io.network.BaseConnectionManager;
import edu.usf.cutr.trackerlib.io.network.ConnectionClient;
import edu.usf.cutr.trackerlib.io.network.SocketConnectionManager;
import edu.usf.cutr.trackerlib.server.TrackerServer;
import edu.usf.cutr.trackerlib.tracker.BaseTracker;
import edu.usf.cutr.trackerlib.utils.DeviceUtils;

/**
 * Real time tracker implementation
 */
public class RealTimeTrackerImpl extends BaseTracker {

    /**
     * Connection client for server connection
     */
    private ConnectionClient connectionClient;

    /**
     *
     * @param trackerServer takes tracker server impls
     * @param applicationContext takes app context
     */
    public RealTimeTrackerImpl(TrackerServer trackerServer, Context applicationContext) {
        super(trackerServer, applicationContext);
    }

    @Override
    public void initTracker() {
        BaseConnectionManager cm = new SocketConnectionManager();
        connectionClient = new ConnectionClient(cm, getTrackerServer(), getApplicationContext());
    }

    @Override
    public void startTracker() {
        String uuid = DeviceUtils.getDeviceId(getApplicationContext());
        connectionClient.sendLoginMessage(uuid);
    }

    @Override
    public void stopTracker() {
        connectionClient.stopConnection();
    }

    @Override
    public void cancelTracker() {
        this.stopTracker();
    }

    @Override
    public void onLocationUpdate(Location location) {
        TrackData trackData = new TrackData(location);
        connectionClient.sendTrackData(trackData);
    }

    @Override
    public void updateTrackerConfig(TrackerConfig trackerConfig) {
        getTrackerServer().setUseWifiOnly(trackerConfig.isUseOnlyWifi());
    }
}
