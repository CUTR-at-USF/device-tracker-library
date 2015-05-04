package edu.usf.cutr.trackerlib.server;

import edu.usf.cutr.trackerlib.data.NMEASentence;
import edu.usf.cutr.trackerlib.data.TrackData;
import edu.usf.cutr.trackerlib.data.TrackerConfig;

/**
 * Created by cagricetin on 4/21/15.
 */
public class TraccarServerImpl implements TrackerServer{

    private TrackerConfig trackerConfig;

    private NMEASentence nmeaSentence;

    public TraccarServerImpl(TrackerConfig trackerConfig, NMEASentence nmeaSentence) {
        this.nmeaSentence = nmeaSentence;
        this.trackerConfig = trackerConfig;
    }

    public TraccarServerImpl(String address, Integer port, String loginMessage,
                             NMEASentence nmeaSentence, boolean useWifiOnly) {
        this.nmeaSentence = nmeaSentence;
        TrackerConfig tc = new TrackerConfig(address, port, null, loginMessage, useWifiOnly);
    }

    @Override
    public String prepareLocationMessage(TrackData trackData) {
        return null;
    }

    @Override
    public String getLoginMessage() {
        return trackerConfig.getLoginMessage();
    }

    @Override
    public String getAddress() {
        return trackerConfig.getServerAddress();
    }

    @Override
    public Integer getPort() {
        return trackerConfig.getServerPort();
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public ServerType getServerType() {
        return ServerType.TRACCAR;
    }

    @Override
    public NMEASentence getNmeaSentence() {
        return nmeaSentence;
    }

    @Override
    public boolean useWifiOnly() {
        return trackerConfig.isUseOnlyWifi();
    }

    @Override
    public void setUseWifiOnly(boolean useWifiOnly) {
        trackerConfig.setUseOnlyWifi(useWifiOnly);
    }
}
