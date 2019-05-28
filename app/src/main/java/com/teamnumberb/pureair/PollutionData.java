package com.teamnumberb.pureair;

import org.osmdroid.util.GeoPoint;

import java.io.Serializable;

public class PollutionData implements Serializable {
    private static GeoPoint wroclawGeoPoint = new GeoPoint(51.10, 17.06);

    public GeoPoint location;
    public double pm25;

    PollutionData() {
        location = wroclawGeoPoint;
        pm25 = 0;
    }

    PollutionData(double latitude, double longitude, double pmValue) {
        location = new GeoPoint(latitude, longitude);
        pm25 = pmValue;
    }
}
