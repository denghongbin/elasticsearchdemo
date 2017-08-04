package com.starsmobi.domain.result;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {

    private static final long serialVersionUID = 3684248976755323061L;
    /**
     * 状态码
     */
    private int code;

    /**
     * 返回消息提示
     */
    private String message;

    /**
     * 实体结果集合
     */
    private List<EntityResult> entityResultList;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<EntityResult> getEntityResultList() {
        return entityResultList;
    }

    public void setEntityResultList(List<EntityResult> entityResultList) {
        this.entityResultList = entityResultList;
    }

    public SearchResult(int code, String message, List<EntityResult> entityResultList) {
        this.code = code;
        this.message = message;
        this.entityResultList = entityResultList;
    }

    public SearchResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public SearchResult() {
    }
}
