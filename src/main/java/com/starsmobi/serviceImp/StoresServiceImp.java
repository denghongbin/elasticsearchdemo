package com.starsmobi.serviceImp;

import com.alibaba.fastjson.JSONArray;
import com.starsmobi.domain.StoresEs;
import com.starsmobi.mapper.StoresEsMapper;
import com.starsmobi.service.IStoresService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        List<StoresEs> storesEss = storesEsMapper.listStores();

        storesEss.forEach(s->{
            JSONArray  aas = JSONArray.parseArray(s.getPolygon());

        });

        return null;
    }
}
