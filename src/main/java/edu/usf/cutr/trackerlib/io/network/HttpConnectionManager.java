package edu.usf.cutr.trackerlib.io.network;

import android.content.Context;

import java.util.List;

import edu.usf.cutr.trackerlib.data.TrackData;

/**
 * Created by cagricetin on 4/27/15.
 */
public class HttpConnectionManager implements ConnectionManager {

    private String url;

    private Context applicationContext;

    public HttpConnectionManager(String url, Context applicationContext) {
        this.url = url;
        this.applicationContext = applicationContext;
    }

    @Override
    public void sendTrackData(TrackData trackData) {
        //TODO: implement rest
    }

    @Override
    public void sendAllTrackDate(List<TrackData> trackDataList) {
        for (TrackData td: trackDataList){
            sendTrackData(td);
        }
    }
}
