package edu.usf.cutr.trackerlib.io.network;

import android.os.AsyncTask;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

import edu.usf.cutr.trackerlib.data.TrackData;
import edu.usf.cutr.trackerlib.server.TrackerServer;
import edu.usf.cutr.trackerlib.utils.Logger;

/**
 * Created by cagricetin on 4/28/15.
 */
public class SocketConnectionManager extends BaseConnectionManager {

    private Socket socket;

    private OutputStream socketStream;

    private boolean connectionClosed;

    private boolean connectionBusy;

    public SocketConnectionManager() {
        connectionBusy = false;
        connectionClosed = false;
    }

    @Override
    public boolean isConnectionClosed() {
        return connectionClosed;
    }

    @Override
    public boolean isConnectionBusy() {
        return connectionBusy;
    }

    @Override
    public void connect(final String address, final int port) {
        connectionBusy = true;

        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    socket = new Socket();
                    socket.connect(new InetSocketAddress(address, port));
                    socket.setSoTimeout(SOCKET_TIMEOUT);
                    socketStream = socket.getOutputStream();
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }

            @Override
            protected void onCancelled() {
                if (!connectionClosed) {
                    connectionBusy = false;
                    getHandler().onConnected(false);
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (!connectionClosed) {
                    connectionBusy = false;
                    getHandler().onConnected(result);
                }
            }

        }.execute();

    }

    @Override
    public void send(String message) {
        connectionBusy = true;

        new AsyncTask<String, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(String... params) {
                try {
                    socketStream.write(params[0].getBytes());
                    socketStream.flush();
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }

            @Override
            protected void onCancelled() {
                if (!connectionClosed) {
                    connectionBusy = false;
                    getHandler().onSent(false);
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (!connectionClosed) {
                    connectionBusy = false;
                    getHandler().onSent(result);
                }
            }

        }.execute(message);

    }

    @Override
    public void disconnect() {
        connectionClosed = true;
        try {
            if (socketStream != null) {
                socketStream.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
            Logger.error(e.toString());
        }
    }
}
