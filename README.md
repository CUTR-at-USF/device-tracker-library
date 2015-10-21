# DeviceTrackerLib

[![Build Status](https://travis-ci.org/CUTR-at-USF/device-tracker-library.svg?branch=master)](https://travis-ci.org/CUTR-at-USF/device-tracker-library)


Android device tracker library is an Android library that enables devices to send their location
information. After initializing this library, it will automatically start sending location information to given server.
As a GPS tracking server, Traccar server was used. Traccar (http://traccar.org/) is an open source GPS tracking server application.

##Installation

In order to use the DeviceTrackerLib in your Android application the following need to be done in the ```Android Studio```

This library requires three permissions:

```
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
```

Add this into the  ```build.gradle``` file:

```
    //user tracker library
    compile project (':trackerlib')
```

##Usage

To initialize the library, the following need to be done in your ```Application``` class:

Tracker initialize:

 ```
 DeviceTrackerManager.init(ip, port, TrackerType (BATCH or REAL_TIME), useWifiOnly);
```

Tracker start tracking:

```
DeviceTrackerManager.startTracker();
```

Tracker init google analytics (optional):

```
DeviceTrackerManager.initAnalytics(getApplicationContext(), "XXX");
```


### Sample usage
```
/*
* Initialize device tracker library
*/
    public void initDeviceTracker() {
            boolean useWifiOnly =  false;
            //Create a Tracker config with ip, port, Tracker type
            TrackerConfig tc = new TrackerConfig("xxx.xxx.xx.x", 5005, TrackerConfig.TrackerType.BATCH,
                    useWifiOnly);

            String uuid = mPrefs.getString(APP_UID, null);

            DeviceTrackerManager.init(tc, getApplicationContext(), uuid);

            DeviceTrackerManager.startTracker();

            DeviceTrackerManager.initAnalytics(context, "XXX");
    }
```

### LICENSE

```
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

 ```

