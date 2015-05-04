package edu.usf.cutr.trackerlib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by cagricetin on 5/4/15.
 */
public class ConfigUtils {

    public static boolean isWifiUpdateOK(boolean useWifiOnly, Context context) {
        if (!useWifiOnly){
            return true;
        }else {
            ConnectivityManager connManager = (ConnectivityManager) context.
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return mWifi.isConnected();
        }
    }
}
