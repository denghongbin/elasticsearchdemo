package com.starsmobi.domain;

import java.io.Serializable;

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
public class StoreEs implements Serializable{

    private static final long serialVersionUID = 1543164075681892367L;
    /**
     * 商品店铺ID
     */
    private String storeId;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    @Override
    public String toString() {
        return "StoreEs{" +
                "storeId='" + storeId + '\'' +
                '}';
    }
}
