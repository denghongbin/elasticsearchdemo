package com.starsmobi.serviceImp;

import com.starsmobi.domain.GoodsEs;
import com.starsmobi.domain.StoresEs;
import com.starsmobi.mapper.GoodsEsMapper;
import com.starsmobi.service.IGoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by vincent on 2017-08-02.
 */
@Service("goodService")
public class GoodServiceImp implements IGoodService{

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodServiceImp.class);

    @Autowired
    private GoodsEsMapper goodsEsMapper;

    @Override
    public List<GoodsEs> listGoods() {

        List<GoodsEs> goods = goodsEsMapper.listGoods();
        goods.forEach(g->{
            List<StoresEs> ss = goodsEsMapper.listStores(g.getId());
            LOGGER.info("+++++++++++++++++++"+ss.toString());
            g.setStores(ss);
        });
        LOGGER.info("+++++++++++++++++++"+goods.toString());
        return goods;
    }

    @Override
    public List<StoresEs> listStores(String goodId) {
        return null;
    }
}
