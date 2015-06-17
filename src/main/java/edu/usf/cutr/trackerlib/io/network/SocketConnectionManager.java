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
package edu.usf.cutr.trackerlib.io.network;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import edu.usf.cutr.trackerlib.utils.Logger;

/**
 * Connection manager implementation for submitting locations
 * Protocol: TCP
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
    public void send(final String message) {
        connectionBusy = true;
        new Thread(new Runnable() {
            private static final String THREAD_MESSAGE = "result";
            public void run() {
                Message returnMessage = new Message();
                Bundle bundle = new Bundle();
                bundle.putBoolean(THREAD_MESSAGE, true);
                try {
                    socketStream.write(message.getBytes());
                    socketStream.flush();
                } catch (Exception e) {
                    bundle.putBoolean(THREAD_MESSAGE, false);
                }
                returnMessage.setData(bundle);
                handler.sendMessage(returnMessage);
            }

            private Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    connectionBusy = false;
                    super.handleMessage(msg);
                    boolean result = msg.getData().getBoolean(THREAD_MESSAGE);
                    getHandler().onSent(result);
                }
            };
        }).start();
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
