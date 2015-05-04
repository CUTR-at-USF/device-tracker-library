package edu.usf.cutr.trackerlib.server;

import edu.usf.cutr.trackerlib.data.NMEASentence;
import edu.usf.cutr.trackerlib.data.TrackData;

/**
 * Created by cagricetin on 4/21/15.
 */
public interface TrackerServer {

    public String prepareLocationMessage(TrackData trackData);

    public String getLoginMessage();

    public String getAddress();

    public Integer getPort();

    public String getUrl();

    public ServerType getServerType();

    public NMEASentence getNmeaSentence();

    public boolean useWifiOnly();

    public void setUseWifiOnly(boolean useWifiOnly);
}
