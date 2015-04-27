package edu.usf.cutr.trackerlib.io;

import java.util.List;

import edu.usf.cutr.trackerlib.data.TrackData;

/**
 * Created by cagricetin on 4/27/15.
 */
public interface TrackerDao {

    public List<TrackData> getAllTrackData();

    public void deleteAllData();

    public void saveTrackData(TrackData trackData);
}
