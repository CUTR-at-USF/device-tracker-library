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
package edu.usf.cutr.trackerlib.io.network;

import android.content.Context;
import android.os.Handler;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import edu.usf.cutr.trackerlib.data.TrackData;
import edu.usf.cutr.trackerlib.server.TrackerServer;

/**
 * Connection client which uses the connection manager
 * Connection client design to communicate with server regardless of communication protocol (e.g.:
 * TCP, HTTP, HTTPS)
 */
public class ConnectionClient implements BaseConnectionManager.ConnectionHandler {

    private static final long RECONNECT_DELAY = 10 * 1000;
    private ConnectionManager connectionManager;
    private TrackerServer trackerServer;
    private Queue<String> messageQueue;
    private Context applicationContext;
    private Callback callback;

    public interface Callback {
        void onSendFinished();
    }

    public ConnectionClient(BaseConnectionManager connectionManager, TrackerServer trackerServer,
                            Context applicationContext) {
        connectionManager.setHandler(this);
        messageQueue = new LinkedList<String>();
        this.connectionManager = connectionManager;
        this.trackerServer = trackerServer;
        this.applicationContext = applicationContext;
        startConnection();

    }

    public void sendTrackData(TrackData trackData) {
        String message = trackerServer.prepareLocationMessage(trackData);
        addQueue(message);
        sendMessages();
    }

    public void sendAllTrackData(List<TrackData> trackDataList) {
        for (TrackData trackData : trackDataList) {
            String message = trackerServer.prepareLocationMessage(trackData);
            addQueue(message);
        }
        sendMessages();
    }

    public void sendLoginMessage(String uuid) {
        String message = trackerServer.prepareLoginMessage(uuid);
        addQueue(message);
    }

    private void addQueue(String message) {
        messageQueue.offer(message);
    }

    private void sendMessages() {
        if (!connectionManager.isConnectionClosed() && !connectionManager.isConnectionBusy()) {
            connectionManager.send(messageQueue.poll());
        }
    }

    public void startConnection() {
        connectionManager.connect(trackerServer.getAddress(), trackerServer.getPort());
    }

    public void stopConnection() {
        connectionManager.disconnect();
    }

    private void reconnect() {
        connectionManager.disconnect();
        connectionManager.connect(trackerServer.getAddress(), trackerServer.getPort());
    }

    private void delayedReconnect() {
        connectionManager.disconnect();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                connectionManager.connect(trackerServer.getAddress(), trackerServer.getPort());
            }
        }, RECONNECT_DELAY);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void onConnected(boolean result) {
        if (!result) {
            delayedReconnect();
        } else {
            sendMessages();
        }
    }

    @Override
    public void onSent(boolean result) {
        if (result) {
            if (!messageQueue.isEmpty()) {
                connectionManager.send(messageQueue.poll());
            } else {
                notifyCallbacks();
            }
        } else {
            delayedReconnect();
        }
    }

    private void notifyCallbacks() {
        if (callback != null) {
            callback.onSendFinished();
        }
    }

    public TrackerServer getTrackerServer() {
        return trackerServer;
    }
}
