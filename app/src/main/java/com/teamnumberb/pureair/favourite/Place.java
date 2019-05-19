package com.teamnumberb.pureair.favourite;

import java.io.Serializable;
import java.util.Objects;

public class Place implements Serializable {
    private String name;
    private double latitude;
    private double longitude;

    public Place(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return Double.compare(place.latitude, latitude) == 0 &&
                Double.compare(place.longitude, longitude) == 0 &&
                Objects.equals(name, place.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, latitude, longitude);
    }
}
