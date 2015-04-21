package edu.usf.cutr.trackerlib.tracker;

import edu.usf.cutr.trackerlib.data.TrackerConfig;
import edu.usf.cutr.trackerlib.server.TrackerServer;

/**
 * Created by cagricetin on 4/20/15.
 */
public abstract class BaseTracker implements TrackerBehavior{

    private TrackerServer trackerServer;

    protected BaseTracker(TrackerServer trackerServer) {
        this.trackerServer = trackerServer;
    }

    public TrackerServer getTrackerServer() {
        return trackerServer;
    }
}
