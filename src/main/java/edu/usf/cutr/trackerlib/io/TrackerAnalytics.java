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
package edu.usf.cutr.trackerlib.io;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

/**
 * Google analytics implementation for device tracking lib
 */
public class TrackerAnalytics {

    private static Tracker tracker;

    /**
     * Event categories for segmentation
     * app_settings, ui_action, submit is similar with OBA IOS
     */
    public enum EventCategory {
        TRACE("trace"), LOCATION_UPDATE("location_update"), APP_SETTINGS("app_settings");
        private final String stringValue;

        EventCategory(final String s) {
            stringValue = s;
        }

        public String toString() {
            return stringValue;
        }
    }

    public static void init(Context applicationContext, String trackerId) {
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(applicationContext);
        analytics.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);

        tracker = analytics.newTracker(trackerId);
        tracker.setSampleRate(20);
    }

    /**
     * Reports events with categories. Helps segmentation in GA admin console.
     *
     * @param category category name
     * @param action   action name
     * @param label    label name
     */
    public static void reportEventWithCategory(String category, String action, String label) {
        if (isAnalyticsActive()) {
            tracker.send(new HitBuilders.EventBuilder()
                    .setCategory(category)
                    .setAction(action)
                    .setLabel(label)
                    .build());
        }
    }

    private static boolean isAnalyticsActive() {
        return !(tracker == null);
    }
}
