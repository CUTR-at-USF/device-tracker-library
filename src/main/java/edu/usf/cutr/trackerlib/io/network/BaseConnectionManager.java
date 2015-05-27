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

/**
 * Base connection manager implementation
 */
public abstract class BaseConnectionManager implements ConnectionManager {

    public static final int SOCKET_TIMEOUT = 10 * 1000;

    /**
     * Callbacks
     */
    public interface ConnectionHandler {

        /**
         * used when the device connects to server
         * @param result true or false
         */
        void onConnected(boolean result);

        /**
         * invoke when the client sends a message to the server
         * @param result true or false
         */
        void onSent(boolean result);
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
