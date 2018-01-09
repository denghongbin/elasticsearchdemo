package com.starsmobi.domain;

import java.io.Serializable;
import java.util.List;

/** 店铺坐标数组类
 * Created by Administrator on 2018-01-09.
 */
public class PolygonArray implements Serializable{


    private static final long serialVersionUID = 3890102924566987713L;
    private List<CoordinateCollection> polygons;

    public List<CoordinateCollection> getPolygons() {
        return polygons;
    }

    public void setPolygons(List<CoordinateCollection> polygons) {
        this.polygons = polygons;
    }

    @Override
    public String toString() {
        return "PolygonArray{" +
                "polygons=" + polygons +
                '}';
    }
}
