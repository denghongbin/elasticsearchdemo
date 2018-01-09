package com.starsmobi.mapper;

import com.starsmobi.domain.StoresEs;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface StoresEsMapper {

    List<StoresEs> listStores();

}
