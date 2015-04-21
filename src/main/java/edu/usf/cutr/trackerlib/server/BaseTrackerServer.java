package edu.usf.cutr.trackerlib.server;

/**
 * Created by cagricetin on 4/21/15.
 */
public abstract class BaseTrackerServer implements TrackerServer{

    private String url;

    protected BaseTrackerServer(String url) {
        this.url = url;
    }
}
