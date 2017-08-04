package com.starsmobi.domain;

import java.io.Serializable;

public class SearchRequest implements Serializable {

    private static final long serialVersionUID = -7622704992740563613L;
    /**
     * 查询关键字
     */
    private String keywords;

    /**
     * 店铺ID
     */
    private String storeId;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
