package com.starsmobi.mapper;

import com.starsmobi.domain.GoodsEs;
import com.starsmobi.domain.StoreEs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsEsMapper {

    List<GoodsEs> listGoods();

    List<StoreEs> listStores(@Param("goodId") String goodId);

}
