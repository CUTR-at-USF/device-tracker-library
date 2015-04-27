/*
 * Copyright (C) 2015 University of South Florida (cagricetin@mail.usf.edu).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.usf.cutr.trackerlib.data;

import android.location.Location;

import java.util.Date;

public class TrackData {

    private Location location;

    private int dateTime;

    public TrackData(Location location, int dateTime) {
        this.location = location;
        this.dateTime = dateTime;
    }

    public TrackData(double latitude, double longitude, double altitude, float speed, float bearing,
                     int dateTime){
        location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setAltitude(altitude);
        location.setSpeed(speed);
        location.setBearing(bearing);
        this.dateTime = dateTime;
    }

    public double getLatitude(){
        return location.getLatitude();
    }

    public double getLongitude(){
        return location.getLongitude();
    }

    public double getAltitude(){
        return location.getAltitude();
    }

    public float getBearing(){
        return location.getBearing();
    }

    public float getSpeed(){
        return location.getSpeed();
    }

    public int getDateTime() {
        return dateTime;
    }
}
