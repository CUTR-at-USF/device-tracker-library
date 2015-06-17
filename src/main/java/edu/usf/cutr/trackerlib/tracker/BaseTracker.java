/*
 * Copyright (C) 2015 University of South Florida (cagricetin@mail.usf.edu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.usf.cutr.trackerlib.tracker;

import android.content.Context;

import edu.usf.cutr.trackerlib.io.DataManager;
import edu.usf.cutr.trackerlib.io.DataManagerImpl;
import edu.usf.cutr.trackerlib.server.TrackerServer;

/**
 * Base tracker object that implements tracker behaviors
 */
public abstract class BaseTracker implements TrackerBehavior{

    /**
     * Server implementation for location updates
     */
    private TrackerServer trackerServer;

    /**
     * Data manager implementation for storing information (e.g., batch update locations)
     */
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
