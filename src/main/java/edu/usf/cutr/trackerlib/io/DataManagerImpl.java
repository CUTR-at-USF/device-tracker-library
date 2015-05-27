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
package edu.usf.cutr.trackerlib.io;

import android.content.Context;

import java.util.List;

import edu.usf.cutr.trackerlib.data.TrackData;

/**
 * Data manager implementation for storing track data in the db
 */
public class DataManagerImpl implements DataManager{

    private TrackerDao trackerDao;

    public DataManagerImpl(Context applicationContext) {
        trackerDao = new TrackerDaoImpl(applicationContext);
    }

    @Override
    public void saveTrackData(TrackData trackData) {
        trackerDao.saveTrackData(trackData);
    }

    @Override
    public void saveAllTrackData(List<TrackData> trackDataList) {
        for (TrackData td: trackDataList){
            saveTrackData(td);
        }
    }

    @Override
    public List<TrackData> getAllTrackData() {
        return trackerDao.getAllTrackData();
    }

    @Override
    public void wipeData() {
        trackerDao.deleteAllData();
    }
}
