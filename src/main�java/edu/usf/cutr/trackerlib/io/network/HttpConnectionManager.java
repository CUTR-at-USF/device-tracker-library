package edu.usf.cutr.trackerlib.io.network;

import android.content.Context;

import java.util.List;

import edu.usf.cutr.trackerlib.data.TrackData;
import edu.usf.cutr.trackerlib.server.TrackerServer;

/**
 * Created by cagricetin on 4/27/15.
 */
public class HttpConnectionManager extends BaseConnectionManager {

    public HttpConnectionManager(ConnectionHandler handler) {
        super(handler);
    }

    @Override
    public boolean isConnectionClosed() {
        return false;
    }

    @Override
    public boolean isConnectionBusy() {
        return false;
    }

    @Override
    public void send(String message) {

    }

    @Override
    public void connect(String address, int port) {

    }

    @Override
    public void disconnect() {

    }
}
