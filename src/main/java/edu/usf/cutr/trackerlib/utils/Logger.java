package edu.usf.cutr.trackerlib.utils;

import android.util.Log;

import edu.usf.cutr.trackerlib.BuildConfig;

/**
 * Created by cagricetin on 4/21/15.
 */
public class Logger {

    private static final String TAG = "TrackerLib";

    public static void verbose(Object o){
        if (BuildConfig.DEBUG){
            Log.v(TAG, String.valueOf(o));
        }
    }

    public static void error(Object o){
        if (BuildConfig.DEBUG){
            Log.e(TAG, String.valueOf(o));
        }
    }

    public static void debug(Object o){
        if (BuildConfig.DEBUG){
            Log.d(TAG, String.valueOf(o));
        }
    }
}
