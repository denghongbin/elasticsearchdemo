package com.starsmobi.domain;

import java.io.Serializable;
import java.util.List;

/** 坐标集合
 * Created by vincent on 2018-01-09.
 */
public class CoordinateCollection implements Serializable{


    private static final long serialVersionUID = -7475486598185906070L;
    private List<Coordinate> coordinates;

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "CoordinateCollection{" +
                "coordinates=" + coordinates +
                '}';
    }
}
