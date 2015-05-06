package edu.usf.cutr.trackerlib.io.network;

import java.util.List;

import edu.usf.cutr.trackerlib.data.TrackData;

/**
 * Created by cagricetin on 4/27/15.
 */
public interface ConnectionManager {

    public boolean isConnectionClosed();

    public boolean isConnectionBusy();

    public void send(String message);

    public void connect(final String address, final int port);

    public void disconnect();
}
