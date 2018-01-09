package com.starsmobi.domain;

import java.io.Serializable;

/** 坐标
 * Created by vincent on 2018-01-09.
 */
public class Coordinate implements Serializable{


    private static final long serialVersionUID = -5437751448436577375L;
    private String lat;

    private String lon;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                '}';
    }
}
