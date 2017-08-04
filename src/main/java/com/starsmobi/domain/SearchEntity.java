package com.starsmobi.domain;

import java.util.Map;

public class SearchEntity extends SearchRequest {

    private static final long serialVersionUID = -5378186128839366823L;
    /**
     * 搜索的字段
     */
    private String field;

    /**
     * 搜索的多字段集合
     */
    private String[] fields;

    /**
     * 排序的字段
     */
    private String orderField;

    /**
     * asc-升序,desc-降序
     */
    private String sortOrder;

    /**
     * key:搜索的字段，value:搜索关键词或内容
     */
    private Map<String, Object> fieldMap;

    /**
     * 子文档查询字段
     */
    private String childField;

    public String getChildField() {
        return childField;
    }

    public void setChildField(String childField) {
        this.childField = childField;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Map<String, Object> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(Map<String, Object> fieldMap) {
        this.fieldMap = fieldMap;
    }

}
