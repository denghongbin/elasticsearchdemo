package com.starsmobi.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Description: .</p>
 * <p>Company: 深圳市旺生活互联网科技有限公司</p>
 * <p>Copyright: 2015-2017 happylifeplat.com All Rights Reserved</p>
 *  存放在es里的商品实体类
 * @author vincent
 * @version 1.0
 * @date 2017/7/31 9:46
 * @since JDK 1.8
 */
public class StoresEs implements Serializable{

    private static final long serialVersionUID = 1543164075681892367L;

    private String id;

    private String storeId;

    private String name;

    private String polygon;

    private Date createTime;

    private Date updateTime;

    private int isDisable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPolygon() {
        return polygon;
    }

    public void setPolygon(String polygon) {
        this.polygon = polygon;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(int isDisable) {
        this.isDisable = isDisable;
    }

    @Override
    public String toString() {
        return "StoresEs{" +
                "storeId='" + storeId + '\'' +
                ", name='" + name + '\'' +
                ", polygon='" + polygon + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isDisable=" + isDisable +
                '}';
    }
}
