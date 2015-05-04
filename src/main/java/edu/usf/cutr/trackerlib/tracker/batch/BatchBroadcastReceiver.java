package edu.usf.cutr.trackerlib.tracker.batch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

/**
 * Created by cagricetin on 4/27/15.
 */
public class BatchBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, BatchUpdateService.class);
        context.startService(serviceIntent);
    }
}
