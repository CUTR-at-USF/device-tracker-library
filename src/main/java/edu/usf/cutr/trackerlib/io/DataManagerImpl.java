package edu.usf.cutr.trackerlib.io;

import android.content.Context;

import java.util.List;

import edu.usf.cutr.trackerlib.data.TrackData;

/**
 * Created by cagricetin on 4/23/15.
 */
public class DataManagerImpl implements DataManager{

    private TrackerDao trackerDao;

    public DataManagerImpl(Context applicationContext) {
        trackerDao = new TrackerDaoImpl(applicationContext);
    }

    @Override
    public void saveTrackData(TrackData trackData) {
        trackerDao.saveTrackData(trackData);
    }

    @Override
    public void saveAllTrackData(List<TrackData> trackDataList) {
        for (TrackData td: trackDataList){
            saveTrackData(td);
        }
    }

    @Override
    public List<TrackData> getAllTrackData() {
        return trackerDao.getAllTrackData();
    }

    @Override
    public void wipeData() {
        trackerDao.deleteAllData();
    }
}
