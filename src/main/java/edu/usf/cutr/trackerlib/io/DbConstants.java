package edu.usf.cutr.trackerlib.io;

/**
 * Created by cagricetin on 4/27/15.
 */
public class DbConstants {

    protected static final String DATABASE_NAME = "edu.usf.cutr.trackerlib.io";

    protected static final int DATABASE_VERSION = 1;

    /**
     * The table that stores locations
     * Type: String
     */
    protected static final String TABLE_NAME = "TrackLocation";

    /**
     * The unique ID for a row.
     * Type: Int
     */
    protected static final String ID = "_id";

    /**
     * The latitude of the device location.
     * Type: DOUBLE
     */
    protected static final String LATITUDE = "latitude";

    /**
     * The longitude of the device location.
     * Type: DOUBLE
     */
    protected static final String LONGITUDE = "longitude";

    /**
     * The altitude of the device location.
     * Type: DOUBLE
     */
    protected static final String ALTITUDE = "altitude";

    /**
     * The speed of the device.
     * Type: REAL
     */
    protected static final String SPEED = "speed";

    /**
     * The bearing of the device.
     * Type: REAL
     */
    protected static final String BEARING = "bearing";

    /**
     * The dateTime of the location report in milliseconds
     * Type: int
     */
    protected static final String DATETIME = "date";
}
