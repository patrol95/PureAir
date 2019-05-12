package com.teamnumberb.pureair.favourite;

public class Place {
    private int name;
    private int latitude;
    private int longitude;

    public Place(int name, int latitude, int longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getName() {
        return name;
    }

    public int getLatitude() {
        return latitude;
    }

    public int getLongitude() {
        return longitude;
    }
}
