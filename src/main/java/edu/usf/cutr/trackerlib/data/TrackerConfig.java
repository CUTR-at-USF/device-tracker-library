package edu.usf.cutr.trackerlib.data;

/**
 *
 */
public class TrackerConfig {

    public enum TrackerType {
        REAL_TIME, BATCH;
    }

    private String trackerServerUrl;

    private TrackerType trackerType;

    public TrackerConfig(String trackerServerUrl, TrackerType trackerType) {
        this.trackerServerUrl = trackerServerUrl;
        this.trackerType = trackerType;
    }

    public String getTrackerServerUrl() {
        return trackerServerUrl;
    }

    public void setTrackerServerUrl(String trackerServerUrl) {
        this.trackerServerUrl = trackerServerUrl;
    }

    public TrackerType getTrackerType() {
        return trackerType;
    }

    public void setTrackerType(TrackerType trackerType) {
        this.trackerType = trackerType;
    }
}
