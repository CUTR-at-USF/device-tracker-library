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
package edu.usf.cutr.trackerlib.utils;

import android.content.Context;

import edu.usf.cutr.trackerlib.io.PreferenceHelper;

public class DeviceUtils {

    private static final String DEVICE_ID = "deviceId";

    public static void saveDeviceId(String id, Context context){
        PreferenceHelper.saveString(context, DEVICE_ID, id);
    }

    public static String getDeviceId(Context context){
        return PreferenceHelper.getString(context, DEVICE_ID);
    }
}
