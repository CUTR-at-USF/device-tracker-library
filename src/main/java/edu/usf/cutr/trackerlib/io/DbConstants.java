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
package edu.usf.cutr.trackerlib.io;

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
    protected static final String DATETIME = "locationDate";
}
