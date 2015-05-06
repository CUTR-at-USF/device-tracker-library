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
 * Created by cagricetin on 4/20/15.
 */
public class RealTimeTrackerImpl extends BaseTracker {

    private ConnectionClient connectionClient;

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
