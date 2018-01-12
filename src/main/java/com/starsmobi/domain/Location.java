package com.starsmobi.domain;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by vincent on 2018-01-11.
 */
public class Location implements Serializable{
    private String type;

    private Object[] coordinates;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Object[] coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "Location{" +
                "type='" + type + '\'' +
                ", coordinates=" + Arrays.toString(coordinates) +
                '}';
    }
}
