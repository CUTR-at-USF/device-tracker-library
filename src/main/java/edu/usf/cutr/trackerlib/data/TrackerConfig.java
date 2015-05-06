package edu.usf.cutr.trackerlib.data;

/**
 *
 */
public class TrackerConfig {

    public enum TrackerType {
        REAL_TIME, BATCH;
    }

    private String serverAddress;

    private Integer serverPort;

    private TrackerType trackerType;

    private boolean useOnlyWifi = false;

    /**
     *
     * @param serverAddress
     * @param serverPort
     * @param trackerType
     */
    public TrackerConfig(String serverAddress, Integer serverPort, TrackerType trackerType,
                          boolean useOnlyWifi) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.trackerType = trackerType;
        this.useOnlyWifi = useOnlyWifi;
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
}
