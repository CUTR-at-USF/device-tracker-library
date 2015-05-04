package edu.usf.cutr.trackerlib.utils;

import android.content.Context;

import edu.usf.cutr.trackerlib.data.NMEASentence;
import edu.usf.cutr.trackerlib.io.PreferenceHelper;
import edu.usf.cutr.trackerlib.server.ServerType;
import edu.usf.cutr.trackerlib.server.TraccarServerImpl;
import edu.usf.cutr.trackerlib.server.TrackerServer;

/**
 * Created by cagricetin on 4/29/15.
 */
public class ServerUtils {

    private static final String ADDRESS = "address";

    private static final String PORT = "port";

    private static final String LOGIN_MESSAGE = "loginMessage";

    private static final String SERVER_TYPE = "serverType";

    private static final String NMEA_SENTENCE = "nmeaSentence";

    private static final String WIFI_ONLY = "wifiOnly";

    public static void saveServerInfo(TrackerServer trackerServer, Context context) {
        PreferenceHelper.saveString(context, ADDRESS, trackerServer.getAddress());
        PreferenceHelper.saveInt(context, PORT, trackerServer.getPort());
        PreferenceHelper.saveString(context, LOGIN_MESSAGE, trackerServer.getLoginMessage());
        PreferenceHelper.saveString(context, SERVER_TYPE, trackerServer.getServerType().name());
        PreferenceHelper.saveBoolean(context, WIFI_ONLY, trackerServer.useWifiOnly());

        if (trackerServer.getServerType().equals(ServerType.TRACCAR)){
            PreferenceHelper.saveString(context, NMEA_SENTENCE, trackerServer.getNmeaSentence().name());
        }
    }

    public static TrackerServer loadServerInfo(Context context) {
        TrackerServer ts = null;
        String address = PreferenceHelper.getString(context, ADDRESS);
        Integer port = PreferenceHelper.getInteger(context, PORT);
        String loginMessage = PreferenceHelper.getString(context, LOGIN_MESSAGE);
        boolean useWifiOnly = PreferenceHelper.getBoolean(context, WIFI_ONLY);

        String trackerType = PreferenceHelper.getString(context, SERVER_TYPE);
        if (ServerType.TRACCAR.name().equals(trackerType)){
            String nmeaSentenceName = PreferenceHelper.getString(context, NMEA_SENTENCE);
            NMEASentence nmeaSentence = Enum.valueOf(NMEASentence.class, nmeaSentenceName);
            ts = new TraccarServerImpl(address, port, loginMessage, nmeaSentence, useWifiOnly);
        }else {
            //Implement if other types of tracker servers used
        }
        return ts;
    }
}
