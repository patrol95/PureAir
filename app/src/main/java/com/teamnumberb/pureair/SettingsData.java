package com.teamnumberb.pureair;

import java.io.Serializable;

class SettingsData implements Serializable {
    int acceptablePm25InMicrogramsPerCubeMeter = 0;
    int acceptableDistanceInMeters = 0;
    int priority = 0;

    SettingsData() {
    }

    SettingsData(int acceptablePm25InMicrogramsPerCubeMeter,
                 int acceptableDistanceInMeters,
                 int priority) {
        this.acceptablePm25InMicrogramsPerCubeMeter = acceptablePm25InMicrogramsPerCubeMeter;
        this.acceptableDistanceInMeters = acceptableDistanceInMeters;
        this.priority = priority;
    }
}
