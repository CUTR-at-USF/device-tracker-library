package edu.usf.cutr.trackerlib.io.network;

/**
 * Created by cagricetin on 4/28/15.
 */
public abstract class BaseConnectionManager implements ConnectionManager {

    public static final int SOCKET_TIMEOUT = 10 * 1000;

    public interface ConnectionHandler {

        public void onConnected(boolean result);

        public void onSent(boolean result);
    }

    private ConnectionHandler handler;

    protected BaseConnectionManager(ConnectionHandler handler) {
        this.handler = handler;
    }

    protected BaseConnectionManager() {

    }

    public void setHandler(ConnectionHandler handler) {
        this.handler = handler;
    }

    public ConnectionHandler getHandler() {
        return handler;
    }
}
