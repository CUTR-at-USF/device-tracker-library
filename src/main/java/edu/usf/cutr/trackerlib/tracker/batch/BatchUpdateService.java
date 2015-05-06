package edu.usf.cutr.trackerlib.tracker.batch;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import edu.usf.cutr.trackerlib.data.TrackData;
import edu.usf.cutr.trackerlib.io.DataManager;
import edu.usf.cutr.trackerlib.io.DataManagerImpl;
import edu.usf.cutr.trackerlib.io.PreferenceHelper;
import edu.usf.cutr.trackerlib.io.network.BaseConnectionManager;
import edu.usf.cutr.trackerlib.io.network.ConnectionClient;
import edu.usf.cutr.trackerlib.io.network.SocketConnectionManager;
import edu.usf.cutr.trackerlib.server.TrackerServer;
import edu.usf.cutr.trackerlib.utils.ConfigUtils;
import edu.usf.cutr.trackerlib.utils.ConnectionUtils;
import edu.usf.cutr.trackerlib.utils.DeviceUtils;
import edu.usf.cutr.trackerlib.utils.LocationUtils;
import edu.usf.cutr.trackerlib.utils.Logger;
import edu.usf.cutr.trackerlib.utils.ServerUtils;

public class BatchUpdateService extends Service implements ConnectionClient.Callback {

    private ConnectionClient connectionClient;

    private DataManager dataManager;

    @Override
    public void onCreate() {
        TrackerServer trackerServer = ServerUtils.loadServerInfo(getApplicationContext());
        BaseConnectionManager cm = new SocketConnectionManager();
        connectionClient = new ConnectionClient(cm, trackerServer, getApplicationContext());

        dataManager = new DataManagerImpl(getApplicationContext());

        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        boolean isWifiUpdateOK = ConfigUtils.isWifiUpdateOK(connectionClient.getTrackerServer().
                        useWifiOnly(), getApplicationContext());
        boolean isNetworkActive = ConnectionUtils.isNetworkActive(getApplicationContext());

        if (isWifiUpdateOK && isNetworkActive){
            startBatchUpdate();
        } else {
            rescheduleBatchUpdate(getApplicationContext());
            stopSelf();
        }

        return Service.START_STICKY;
    }

    private void startBatchUpdate() {
        List<TrackData> trackDataList = dataManager.getAllTrackData();
        List<TrackData> decimatedTrackDataList = LocationUtils.decimate(
                BatchUpdateConstants.DECIMATE_TOLERANCE, trackDataList);

        String uuid = DeviceUtils.getDeviceId(getApplicationContext());
        connectionClient.sendLoginMessage(uuid);

        connectionClient.sendAllTrackData(decimatedTrackDataList);
    }

    @Override
    public void onSendFinished() {
        connectionClient.stopConnection();

        dataManager.wipeData();
        stopSelf();
    }

    private void rescheduleBatchUpdate(Context context) {

        long updateDateMillis = createBatchUpdateTimeForTomorrowMillis();

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

    private long createBatchUpdateTimeForTomorrowMillis() {
        Calendar date = new GregorianCalendar();
        // reset hour, minutes, seconds and millis
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        date.add(Calendar.DATE, 2);
        return date.getTimeInMillis();
    }
}
