package com.starsmobi.service;

import com.starsmobi.domain.GoodsEs;
import com.starsmobi.domain.StoresEs;

import java.util.List;

/**
 * Created by vincent on 2017-08-02.
 */
public interface IGoodService {

    public List<GoodsEs> listGoods();

    public List<StoresEs> listStores(String goodId);
}
