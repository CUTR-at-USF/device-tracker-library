package edu.usf.cutr.trackerlib.tracker.batch;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import edu.usf.cutr.trackerlib.server.TrackerServer;
import edu.usf.cutr.trackerlib.tracker.BaseTracker;
import edu.usf.cutr.trackerlib.utils.Logger;

/**
 * Created by cagricetin on 4/20/15.
 */
public class BatchTrackerImpl extends BaseTracker {

    private static final int BATCH_PROCESS_REQUEST_CODE = 1234;

    public BatchTrackerImpl(TrackerServer trackerServer, Context applicationContext) {
        super(trackerServer, applicationContext);
    }

    @Override
    public void initTracker() {
        //TODO: check if there is already scheduled job
        scheduleBatchUpdate();
    }

    @Override
    public void startTracker() {

    }

    @Override
    public void stopTracker() {
        //TODO: cancel batch update flush data
    }

    private void scheduleBatchUpdate() {
        Intent intent = new Intent(getApplicationContext(), BatchBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                BATCH_PROCESS_REQUEST_CODE, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().
                getSystemService(getApplicationContext().ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis()
                + (10 * 1000), pendingIntent);

        Toast.makeText(getApplicationContext(), "Batch update scheduled", Toast.LENGTH_LONG);
        Logger.verbose("Batch update scheduled");
    }

}
