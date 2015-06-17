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
package edu.usf.cutr.trackerlib.tracker.batch;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import java.util.List;

import edu.usf.cutr.trackerlib.data.TrackData;
import edu.usf.cutr.trackerlib.io.DataManager;
import edu.usf.cutr.trackerlib.io.DataManagerImpl;
import edu.usf.cutr.trackerlib.io.PreferenceHelper;
import edu.usf.cutr.trackerlib.io.network.BaseConnectionManager;
import edu.usf.cutr.trackerlib.io.network.ConnectionClient;
import edu.usf.cutr.trackerlib.io.network.SocketConnectionManager;
import edu.usf.cutr.trackerlib.location.DouglasPeuckerDecimation;
import edu.usf.cutr.trackerlib.location.LocationDecimation;
import edu.usf.cutr.trackerlib.server.TrackerServer;
import edu.usf.cutr.trackerlib.utils.ConfigUtils;
import edu.usf.cutr.trackerlib.utils.ConnectionUtils;
import edu.usf.cutr.trackerlib.utils.DeviceUtils;
import edu.usf.cutr.trackerlib.utils.Logger;
import edu.usf.cutr.trackerlib.utils.ServerUtils;
import edu.usf.cutr.trackerlib.utils.TimeUtils;

/**
 * Service for pushing locations to server
 */
public class BatchUpdateService extends IntentService implements ConnectionClient.Callback {

    private ConnectionClient connectionClient;

    private DataManager dataManager;

    private LocationDecimation locationDecimation;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public BatchUpdateService() {
        super("BatchUpdateService");
    }

    @Override
    public void onCreate() {
        TrackerServer trackerServer = ServerUtils.loadServerInfo(getApplicationContext());
        BaseConnectionManager cm = new SocketConnectionManager();

        connectionClient = new ConnectionClient(cm, trackerServer, getApplicationContext());
        connectionClient.setCallback(this);

        dataManager = new DataManagerImpl(getApplicationContext());

        locationDecimation = new DouglasPeuckerDecimation();

        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        boolean isWifiUpdateOK = ConfigUtils.isWifiUpdateOK(connectionClient.getTrackerServer().
                useWifiOnly(), getApplicationContext());
        boolean isNetworkActive = ConnectionUtils.isNetworkActive(getApplicationContext());

        if (isWifiUpdateOK && isNetworkActive){
            startBatchUpdate();
        } else {
            rescheduleBatchUpdate(getApplicationContext());
            stopSelf();
        }
    }

    private void startBatchUpdate() {
        // Get all the location updates
        List<TrackData> trackDataList = dataManager.getAllTrackData();

        Double trackDistanceThreshold = BatchUpdateConstants.TRACK_DISTANCE_THRESHOLD;
        if (connectionClient.getTrackerServer().getTrackDistanceThreshold() != null){
            trackDistanceThreshold = connectionClient.getTrackerServer().getTrackDistanceThreshold()
                    .doubleValue();
        }
        // Apply Douglas-Peucker algorithm
        List<TrackData> decimatedTrackDataList = locationDecimation.decimate(
                trackDistanceThreshold, trackDataList);

        String uuid = DeviceUtils.getDeviceId(getApplicationContext());
        // Create a login message for the server
        connectionClient.sendLoginMessage(uuid);

        connectionClient.sendAllTrackData(decimatedTrackDataList);
    }

    @Override
    public void onSendFinished() {
        // Reschedule batch update periodically
        rescheduleBatchUpdate(getApplicationContext());

        // Stop service elements
        connectionClient.stopConnection();
        dataManager.wipeData();
        stopSelf();
    }

    private void rescheduleBatchUpdate(Context context) {
        long updateDateMillis = TimeUtils.createBatchUpdateTimeMillis();

        Intent intent = new Intent(getApplicationContext(), BatchBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                BatchUpdateConstants.BATCH_PROCESS_REQUEST_CODE, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().
                getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, updateDateMillis, pendingIntent);

        Logger.verbose("Batch update scheduled");

        //Save current update time
        PreferenceHelper.saveLong(context, BatchUpdateConstants.BATCH_UPDATE_TIME,
                updateDateMillis);
    }
}