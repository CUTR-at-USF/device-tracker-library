/*
 * Copyright (C) 2015 Cagri Cetin (cagricetin@mail.usf.edu), University of South Florida
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
package edu.usf.cutr.trackerlib.server;

import java.util.Calendar;
import java.util.Formatter;
import java.util.Locale;
import java.util.TimeZone;

import edu.usf.cutr.trackerlib.data.NMEASentence;
import edu.usf.cutr.trackerlib.data.TrackData;
import edu.usf.cutr.trackerlib.data.TrackerConfig;

/**
 * Traccar tracking server implementation
 * Traccar server project: https://www.traccar.org/
 */
public class TraccarServerImpl implements TrackerServer {

    private TrackerConfig trackerConfig;

    private NMEASentence nmeaSentence;

    public TraccarServerImpl(TrackerConfig trackerConfig, NMEASentence nmeaSentence) {
        this.nmeaSentence = nmeaSentence;
        this.trackerConfig = trackerConfig;
    }

    public TraccarServerImpl(String address, Integer port,NMEASentence nmeaSentence,
                             boolean useWifiOnly) {
        this.nmeaSentence = nmeaSentence;
        this.trackerConfig = new TrackerConfig(address, port, null, useWifiOnly);
    }

    @Override
    public String prepareLoginMessage(String id) {
        StringBuilder s = new StringBuilder("$PGID,");
        Formatter f = new Formatter(s, Locale.ENGLISH);

        s.append(id);

        byte checksum = 0;
        for (byte b : s.substring(1).getBytes()) {
            checksum ^= b;
        }
        f.format("*%02x\r\n", (int) checksum);
        f.close();

        return s.toString();
    }

    @Override
    public String prepareLocationMessage(TrackData trackData) {
        if (NMEASentence.GPRMC.equals(nmeaSentence)){
            StringBuilder s = new StringBuilder("$GPRMC,");
            Formatter f = new Formatter(s, Locale.ENGLISH);
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.ENGLISH);
            calendar.setTimeInMillis(trackData.getTime());

            f.format("%1$tH%1$tM%1$tS.%1$tL,A,", calendar);

            double lat = trackData.getLatitude();
            double lon = trackData.getLongitude();
            f.format("%02d%07.4f,%c,", (int) Math.abs(lat), Math.abs(lat) % 1 * 60, lat < 0 ? 'S' : 'N');
            f.format("%03d%07.4f,%c,", (int) Math.abs(lon), Math.abs(lon) % 1 * 60, lon < 0 ? 'W' : 'E');

            double speed = trackData.getSpeed() * 1.943844; // speed in knots
            f.format("%.2f,%.2f,", speed, trackData.getBearing());
            f.format("%1$td%1$tm%1$ty,,", calendar);

            byte checksum = 0;
            for (byte b : s.substring(1).getBytes()) {
                checksum ^= b;
            }
            f.format("*%02x\r\n", (int) checksum);
            f.close();

            return s.toString();
        } else {
            //Implement other formats
            return null;
        }
    }

    @Override
    public String getAddress() {
        return trackerConfig.getServerAddress();
    }

    @Override
    public Integer getPort() {
        return trackerConfig.getServerPort();
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public ServerType getServerType() {
        return ServerType.TRACCAR;
    }

    @Override
    public NMEASentence getNmeaSentence() {
        return nmeaSentence;
    }

    @Override
    public boolean useWifiOnly() {
        return trackerConfig.isUseOnlyWifi();
    }

    @Override
    public void setUseWifiOnly(boolean useWifiOnly) {
        trackerConfig.setUseOnlyWifi(useWifiOnly);
    }
}
