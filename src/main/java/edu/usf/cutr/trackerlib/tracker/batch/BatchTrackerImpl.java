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
package edu.usf.cutr.trackerlib.tracker.batch;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import edu.usf.cutr.trackerlib.data.TrackData;
import edu.usf.cutr.trackerlib.data.TrackerConfig;
import edu.usf.cutr.trackerlib.io.PreferenceHelper;
import edu.usf.cutr.trackerlib.server.TrackerServer;
import edu.usf.cutr.trackerlib.tracker.BaseTracker;
import edu.usf.cutr.trackerlib.utils.Logger;
import edu.usf.cutr.trackerlib.utils.ServerUtils;

/**
 * Batch tracker implementation
 */
public class BatchTrackerImpl extends BaseTracker {

    public BatchTrackerImpl(TrackerServer trackerServer, Context applicationContext) {
        super(trackerServer, applicationContext);
    }

    /**
     * inits tracker and schedules a batch update
     */
    @Override
    public void initTracker() {
//        boolean isBatchUpdateScheduled = getBatchUpdateScheduled();
//        if (!isBatchUpdateScheduled) {
            scheduleBatchUpdate();
//        }

        // Save server information for future use
        ServerUtils.saveServerInfo(getTrackerServer(), getApplicationContext());
    }

    @Override
    public void startTracker() {
        // Do nothing
    }

    @Override
    public void stopTracker() {
        // Do nothing
    }

    @Override
    public void cancelTracker() {
        getDataManager().wipeData();
        cancelBatchUpdate();
    }

    @Override
    public void onLocationUpdate(Location location) {
        TrackData trackData = new TrackData(location);
        getDataManager().saveTrackData(trackData);
    }

    @Override
    public void updateTrackerConfig(TrackerConfig trackerConfig) {
        getTrackerServer().setUseWifiOnly(trackerConfig.isUseOnlyWifi());
        ServerUtils.saveServerInfo(getTrackerServer(), getApplicationContext());
    }

    private void scheduleBatchUpdate() {
        scheduleBatchUpdate(-1);
    }

    /**
     * Schedules a batch update for given time
     * @param updateDateMillis takes current time in millis
     */
    private void scheduleBatchUpdate(long updateDateMillis) {
        // Creates batch update for at the and of the day
        if (updateDateMillis == -1){
            updateDateMillis = createBatchUpdateTimeMillis();
        }

        Intent intent = new Intent(getApplicationContext(), BatchBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                BatchUpdateConstants.BATCH_PROCESS_REQUEST_CODE, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().
                getSystemService(getApplicationContext().ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, updateDateMillis, pendingIntent);

        Toast.makeText(getApplicationContext(), "Batch update scheduled", Toast.LENGTH_LONG);
        Logger.verbose("Batch update scheduled");

        //Save current update time
        PreferenceHelper.saveLong(getApplicationContext(), BatchUpdateConstants.BATCH_UPDATE_TIME,
                updateDateMillis);
    }

    /**
     * Creates batch update for at the and of the day
     * @return schedule time
     */
    private long createBatchUpdateTimeMillis() {
        // today
        Calendar date = new GregorianCalendar();
        // reset hour, minutes, seconds and millis
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        date.add(Calendar.DATE, 1);

//        return date.getTimeInMillis();
        return System.currentTimeMillis() + (3 * 1000);
    }

    /**
     * Cancels the batch update and removes all data
     */
    private void cancelBatchUpdate() {
        cancelScheduledBatchUpdate();
        getDataManager().wipeData();
    }

    private void cancelScheduledBatchUpdate() {
        Intent intent = new Intent(getApplicationContext(), BatchBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(),
                BatchUpdateConstants.BATCH_PROCESS_REQUEST_CODE, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(
                Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    /**
     *
     * @return if there is already batch update scheduled for today
     */
    public boolean isBatchUpdateScheduled() {
        long updateTimeMillis = PreferenceHelper.getLong(getApplicationContext(),
                BatchUpdateConstants.BATCH_UPDATE_TIME);

        Calendar todayDate = new GregorianCalendar();
        todayDate.add(Calendar.DATE, 1);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");

        String todayDateString;
        try {
            todayDateString = sdf.format(todayDate.getTime());
        }catch (Exception e){
            todayDateString = "";
        }

        String updateDateString;
        try {
            updateDateString = sdf.format(updateTimeMillis);
        }catch (Exception e){
            updateDateString = "";
        }

        return todayDateString.equals(updateDateString);
    }
}