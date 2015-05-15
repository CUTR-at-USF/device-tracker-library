package edu.usf.cutr.trackerlib.io;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by cagricetin on 5/15/15.
 */
public class TrackerAnalytics {

    private static String trackerId;

    private static Context applicationContext;

    private static Tracker tracker;


    /**
     * Event categories for segmentation
     * app_settings, ui_action, submit is similar with OBA IOS
     */
    public enum EventCategory {
        TRACE("trace"), LOCATION_UPDATE("location_update"), APP_SETTINGS("app_settings");
        private final String stringValue;

        private EventCategory(final String s) {
            stringValue = s;
        }

        public String toString() {
            return stringValue;
        }
    }

    public static void init(Context applicationContext, String trackerId) {
        TrackerAnalytics.trackerId = trackerId;
        TrackerAnalytics.applicationContext = applicationContext;

        GoogleAnalytics analytics = GoogleAnalytics.getInstance(applicationContext);
        tracker = analytics.newTracker(trackerId);
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
