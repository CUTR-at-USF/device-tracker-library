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

/**
 * Config object for tracker library
 */
public class TrackerConfig {

    /**
     * Used to determine gow the library push the location
     * Batch or Real-time
     */
    public enum TrackerType {
        REAL_TIME, BATCH
    }

    /**
     * Address of the server
     */
    private String serverAddress;

    /**
     * Port of the server
     */
    private Integer serverPort;

    /**
     * BATCH or REAL_TIME update
     */
    private TrackerType trackerType;

    /**
     * Optional track distance threshold
     */
    private Integer trackDistanceThreshold = -1;

    private boolean useOnlyWifi = false;

    /**
     *
     * @param serverAddress Address of the server
     * @param serverPort Port of the server
     * @param trackerType BATCH or REAL_TIME update
     * @param useOnlyWifi Use only wifi to submit locations to the server
     */
    public TrackerConfig(String serverAddress, Integer serverPort, TrackerType trackerType,
                          boolean useOnlyWifi) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.trackerType = trackerType;
        this.useOnlyWifi = useOnlyWifi;
    }

    /**
     *
     * @param serverAddress Address of the server
     * @param serverPort Port of the server
     * @param trackerType BATCH or REAL_TIME update
     * @param useOnlyWifi Use only wifi to submit locations to the server
     * @param trackDistanceThreshold Use to decimate locations on batch update
     */
    public TrackerConfig(String serverAddress, Integer serverPort, TrackerType trackerType,
                          boolean useOnlyWifi, Integer trackDistanceThreshold) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.trackerType = trackerType;
        this.useOnlyWifi = useOnlyWifi;
        this.trackDistanceThreshold = trackDistanceThreshold;
    }

    public TrackerConfig(boolean useOnlyWifi) {
        this.useOnlyWifi = useOnlyWifi;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public TrackerType getTrackerType() {
        return trackerType;
    }

    public void setTrackerType(TrackerType trackerType) {
        this.trackerType = trackerType;
    }

    public boolean isUseOnlyWifi() {
        return useOnlyWifi;
    }

    public void setUseOnlyWifi(boolean useOnlyWifi) {
        this.useOnlyWifi = useOnlyWifi;
    }

    public Integer getTrackDistanceThreshold() {
        return trackDistanceThreshold;
    }

    public void setTrackDistanceThreshold(Integer trackDistanceThreshold) {
        this.trackDistanceThreshold = trackDistanceThreshold;
    }

}
