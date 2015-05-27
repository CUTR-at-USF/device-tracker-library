/*
 * Copyright (C) 2015 Cagri Cetin (cagricetin@mail.usf.edu), University of South Florida
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

import android.location.Location;

import edu.usf.cutr.trackerlib.data.TrackerConfig;

/**
 * Implements the basic Tracker behaviors for batch and real-time trackers
 */
public interface TrackerBehavior {

    void initTracker();

    void startTracker();

    void stopTracker();

    void cancelTracker();

    void onLocationUpdate(Location location);

    void updateTrackerConfig(TrackerConfig trackerConfig);
}
