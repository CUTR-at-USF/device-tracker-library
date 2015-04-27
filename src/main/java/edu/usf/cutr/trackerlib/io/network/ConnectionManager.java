package edu.usf.cutr.trackerlib.io.network;

import java.util.List;

import edu.usf.cutr.trackerlib.data.TrackData;

/**
 * Created by cagricetin on 4/27/15.
 */
public interface ConnectionManager {

    public void sendTrackData(TrackData trackData);

    public void sendAllTrackDate(List<TrackData> trackDataList);
}
