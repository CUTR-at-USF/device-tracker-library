package edu.usf.cutr.trackerlib.data;

/**
 * The National Marine Electronics Association (NMEA) developed a specification that
 * defines various marine equipments. NMEA sentences are commonly used to track GPS signals.
 * Ref: http://www.gpsinformation.org/dale/nmea.htm
 */
public enum NMEASentence {
    GPRMC("$GPRMC"), //minimum recommended data
    GPAPB("$GPAPB"), // minimum recommended data when following a route
    GPGGA("$GPGGA");

    private final String desc;

    private NMEASentence(final String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return desc;
    }
}
