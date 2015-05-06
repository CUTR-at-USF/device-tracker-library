package edu.usf.cutr.trackerlib.utils;

import android.content.Context;

import edu.usf.cutr.trackerlib.io.PreferenceHelper;

/**
 * Created by cagricetin on 5/5/15.
 */
public class DeviceUtils {

    private static final String DEVICE_ID = "deviceId";

    public static void saveDeviceId(String id, Context context){
        PreferenceHelper.saveString(context, DEVICE_ID, id);
    }

    public static String getDeviceId(Context context){
        return PreferenceHelper.getString(context, DEVICE_ID);
    }
}
