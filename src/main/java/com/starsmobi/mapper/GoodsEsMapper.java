package com.starsmobi.mapper;

import com.starsmobi.domain.GoodsEs;
import com.starsmobi.domain.StoresEs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsEsMapper {

    List<GoodsEs> listGoods();

    List<StoresEs> listStores(@Param("goodId") String goodId);

}
