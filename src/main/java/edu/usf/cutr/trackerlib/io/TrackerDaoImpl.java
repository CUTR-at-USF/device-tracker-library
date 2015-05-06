package edu.usf.cutr.trackerlib.io;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import edu.usf.cutr.trackerlib.data.TrackData;

/**
 * Created by cagricetin on 4/27/15.
 */
public class TrackerDaoImpl extends SQLiteOpenHelper implements TrackerDao {
    /**
     * Data access object
     * Only accessible from io package
     *
     * @param context application context
     */
    protected TrackerDaoImpl(Context context) {
        super(context, DbConstants.DATABASE_NAME, null, DbConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTables(db);

        onCreate(db);
    }

    private void createTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +
                DbConstants.TABLE_NAME + " (" +
                DbConstants.ID + " VARCHAR PRIMARY KEY, " +
                DbConstants.LATITUDE + " DOUBLE NOT NULL," +
                DbConstants.LONGITUDE + " DOUBLE NOT NULL," +
                DbConstants.ALTITUDE + " DOUBLE NOT NULL," +
                DbConstants.SPEED + " REAL NOT NULL," +
                DbConstants.BEARING + " REAL NOT NULL," +
                DbConstants.DATETIME + " INT NOT NULL" +
                ");");
    }

    private void dropTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + DbConstants.TABLE_NAME);
    }

    @Override
    public void saveTrackData(TrackData trackData) {
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DbConstants.LATITUDE, trackData.getLatitude());
        values.put(DbConstants.LONGITUDE, trackData.getLongitude());
        values.put(DbConstants.ALTITUDE, trackData.getAltitude());
        values.put(DbConstants.SPEED, trackData.getSpeed());
        values.put(DbConstants.BEARING, trackData.getBearing());
        values.put(DbConstants.DATETIME, trackData.getTime());

        db.insert(DbConstants.TABLE_NAME, null, values);
    }

    @Override
    public List<TrackData> getAllTrackData() {
        SQLiteDatabase db = getReadableDatabase();

        List<TrackData> trackDataList = new ArrayList<>();

        //Select all entries in db and order by time
        Cursor cursor = db.rawQuery("Select * from " + DbConstants.TABLE_NAME + " order by " +
                DbConstants.DATETIME + " DESC", null);
        if (cursor.moveToFirst()) {
            do {
                double latitude = cursor.getDouble(cursor.getColumnIndex(DbConstants.LATITUDE));
                double longitude = cursor.getDouble(cursor.getColumnIndex(DbConstants.LONGITUDE));
                double altitude = cursor.getDouble(cursor.getColumnIndex(DbConstants.ALTITUDE));
                float bearing = cursor.getFloat(cursor.getColumnIndex(DbConstants.BEARING));
                float speed = cursor.getFloat(cursor.getColumnIndex(DbConstants.SPEED));
                int dateTime = cursor.getInt(cursor.getColumnIndex(DbConstants.DATETIME));

                TrackData td = new TrackData(latitude, longitude, altitude, speed, bearing, dateTime);
                trackDataList.add(td);
            } while (cursor.moveToNext());
        }

        return trackDataList;
    }

    @Override
    public void deleteAllData() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + DbConstants.TABLE_NAME);
    }
}
