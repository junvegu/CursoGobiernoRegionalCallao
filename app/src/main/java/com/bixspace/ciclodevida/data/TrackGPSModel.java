package com.bixspace.ciclodevida.data;

import java.io.Serializable;

/**
 * Created by junior on 06/12/16.
 */

public class TrackGPSModel implements Serializable {

    private double latitude;
    private double longitude;


    public TrackGPSModel(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
