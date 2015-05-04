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
 * Created by cagricetin on 4/20/15.
 */
public class BatchTrackerImpl extends BaseTracker {

    public BatchTrackerImpl(TrackerServer trackerServer, Context applicationContext) {
        super(trackerServer, applicationContext);
    }

    @Override
    public void initTracker() {
        boolean isBatchUpdateScheduled = getBatchUpdateScheduled();
        if (isBatchUpdateScheduled) {
            scheduleBatchUpdate();
        }

        ServerUtils.saveServerInfo(getTrackerServer(), getApplicationContext());
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
        TrackData trackData = new TrackData(location, System.currentTimeMillis());
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

    private void scheduleBatchUpdate(long updateDateMillis) {

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

    private long createBatchUpdateTimeMillis() {
        // today
        Calendar date = new GregorianCalendar();
        // reset hour, minutes, seconds and millis
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        return date.getTimeInMillis();
//        System.currentTimeMillis() + (10 * 1000);
    }

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

    public boolean getBatchUpdateScheduled() {
        long updateTimeMillis = PreferenceHelper.getLong(getApplicationContext(),
                BatchUpdateConstants.BATCH_UPDATE_TIME);
        Calendar updateDate = GregorianCalendar.getInstance();
        updateDate.setTimeInMillis(updateTimeMillis);

        Calendar todayDate = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");

        String todayDateString = sdf.format(todayDate);
        String updateDateString = sdf.format(updateDate);

        return todayDateString.equals(updateDateString);
    }
}
