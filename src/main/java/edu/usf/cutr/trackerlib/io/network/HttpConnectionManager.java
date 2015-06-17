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

/**
 * Connection manager implementation for submitting locations
 * Protocol: HTTP
 * This class created for future use
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
