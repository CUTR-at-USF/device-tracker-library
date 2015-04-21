package edu.usf.cutr.trackerlib.server;

import java.util.List;

import edu.usf.cutr.trackerlib.data.TrackData;

/**
 * Created by cagricetin on 4/21/15.
 */
public interface TrackerServer {

    public void sendTrackData(TrackData trackData);

    public void sendAllTrackData(List<TrackData> trackDataList);
}
