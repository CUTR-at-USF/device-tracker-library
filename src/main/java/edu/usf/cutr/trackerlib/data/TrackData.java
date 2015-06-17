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
package edu.usf.cutr.trackerlib.data;

import android.location.Location;

/**
 * TrackData is the model object for data to track
 *
 * Data to track:
 * -> Location
 *      -> latitude
 *      -> longitude
 *      -> altitude
 *      -> speed
 *      -> bearing
 *      -> dateTime
 */
public class TrackData {

    private Location location;

    public TrackData(Location location) {
        this.location = location;
    }

    public TrackData(double latitude, double longitude, double altitude, float speed, float bearing,
                     long dateTime) {
        location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setAltitude(altitude);
        location.setSpeed(speed);
        location.setBearing(bearing);
        location.setTime(dateTime);
    }

    public double getLatitude() {
        return location.getLatitude();
    }

    public double getLongitude() {
        return location.getLongitude();
    }

    public double getAltitude() {
        return location.getAltitude();
    }

    public float getBearing() {
        return location.getBearing();
    }

    public float getSpeed() {
        return location.getSpeed();
    }

    public long getTime() {
        return location.getTime();
    }

    public Location getLocation() {
        return location;
    }
}
