package edu.usf.cutr.trackerlib.tracker;

import android.content.Context;

import edu.usf.cutr.trackerlib.data.TrackerConfig;
import edu.usf.cutr.trackerlib.io.DataManager;
import edu.usf.cutr.trackerlib.io.DataManagerImpl;
import edu.usf.cutr.trackerlib.server.TrackerServer;

/**
 * Created by cagricetin on 4/20/15.
 */
public abstract class BaseTracker implements TrackerBehavior{

    private TrackerServer trackerServer;

    private DataManager dataManager;

    private Context applicationContext;

    protected BaseTracker(TrackerServer trackerServer, Context applicationContext) {
        this.trackerServer = trackerServer;
        this.applicationContext = applicationContext;
        this.dataManager = new DataManagerImpl(applicationContext);
    }

    public TrackerServer getTrackerServer() {
        return trackerServer;
    }

    public Context getApplicationContext() {
        return applicationContext;
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
