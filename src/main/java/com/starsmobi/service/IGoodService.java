package com.starsmobi.service;

import com.starsmobi.domain.GoodsEs;
import com.starsmobi.domain.StoreEs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by vincent on 2017-08-02.
 */
public interface IGoodService {

    public List<GoodsEs> listGoods();

    public List<StoreEs> listStores(String goodId);
}
