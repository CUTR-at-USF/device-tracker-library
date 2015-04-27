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
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(500);
        Toast.makeText(context, "sadsad", Toast.LENGTH_LONG).show();
    }
}
