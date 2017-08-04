package com.starsmobi.domain.result;

import java.io.Serializable;
import java.math.BigDecimal;

public class EntityResult implements Serializable {

    private static final long serialVersionUID = -5780649285806473030L;
    /**
     * 商品id
     */
    private String id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品编码
     */
    private String code;

    /**
     * 条形码
     */
    private String barcode;

    /**
     * 助记码（商品名称首字母）
     */
    private String mnemonicCode;

    /**
     * 商城价（实际销售价格）
     */
    private BigDecimal price;

    /**
     * 原价（参考价格）
     */
    private BigDecimal originalPrice;

    /**
     * 成本价
     */
    private BigDecimal costPrice;


    /**
     * 商品类型id
     */
    private String goodsTypeId;


    /**
     * 商品类型名称
     */
    private String goodsTypeName;

    /**
     * 商品类别id
     */
    private String goodsCategoryId;


    /**
     * 供应商id
     */
    private String providerId;

    /**
     * 供应商名称
     */
    private String providerName;

    /**
     * 商品缩略图url
     */
    private String thumbnail;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getMnemonicCode() {
        return mnemonicCode;
    }

    public void setMnemonicCode(String mnemonicCode) {
        this.mnemonicCode = mnemonicCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public String getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(String goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public String getGoodsCategoryId() {
        return goodsCategoryId;
    }

    public void setGoodsCategoryId(String goodsCategoryId) {
        this.goodsCategoryId = goodsCategoryId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "EntityResult{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", barcode='" + barcode + '\'' +
                ", mnemonicCode='" + mnemonicCode + '\'' +
                ", price=" + price +
                ", originalPrice=" + originalPrice +
                ", costPrice=" + costPrice +
                ", goodsTypeId='" + goodsTypeId + '\'' +
                ", goodsTypeName='" + goodsTypeName + '\'' +
                ", goodsCategoryId='" + goodsCategoryId + '\'' +
                ", providerId='" + providerId + '\'' +
                ", providerName='" + providerName + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
