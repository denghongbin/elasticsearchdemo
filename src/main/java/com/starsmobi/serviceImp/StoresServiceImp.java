package com.starsmobi.serviceImp;

import com.alibaba.fastjson.JSONArray;
import com.starsmobi.ToolUntils;
import com.starsmobi.domain.Location;
import com.starsmobi.domain.StoresDto;
import com.starsmobi.domain.StoresEs;
import com.starsmobi.mapper.StoresEsMapper;
import com.starsmobi.service.IStoresService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincent on 2018-01-09.
 */
@Service("storesService")
public class StoresServiceImp implements IStoresService {

    private static final Logger logger = LoggerFactory.getLogger(StoresServiceImp.class);

    @Autowired
    private StoresEsMapper storesEsMapper;



    @Override
    public List<StoresEs> listStores() {

        List<StoresDto> stores = storesEsMapper.listStores();

        logger.info("查找到list大小为："+stores.size());
        List<StoresEs>  StoresEs = new ArrayList<>();

        stores.forEach(s->{

            //本地数据库存储坐标格式为 [lat, lon], ES要求格式为[lon, lat]
            Object[] locations = ToolUntils.stringPositionReplacement(s.getPolygon());
            logger.info("坐标换位置成功！");

//            Location location = new Location();
//            location.setType("polygon");
//            location.setCoordinates(locations);

            StoresEs ses = new StoresEs();
            ses.setId(s.getId());
            ses.setName(s.getName());
            ses.setStoreId(s.getStoreId());
            ses.setLocation(locations);
            ses.setCreateTime(s.getCreateTime());
            ses.setUpdateTime(s.getUpdateTime());
            ses.setIsDisable(s.getIsDisable());


            System.out.println("坐标换位置之后："+ses.toString());

            StoresEs.add(ses);

        });

        return StoresEs;
    }
}
