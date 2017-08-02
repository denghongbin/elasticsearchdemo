package com.starsmobi.controller;

import com.starsmobi.domain.GoodsEs;
import com.starsmobi.elasticsearchclient.ElasticsearchClient;
import com.starsmobi.service.IGoodService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by vincent on 2017-08-02.
 */
@RestController
@RequestMapping("/goodindex")
public class GoodController {

    @Autowired
    private IGoodService goodService;

    @ApiOperation(value = "为商品创建索引")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public String createGoodIndex(){
        List<GoodsEs> goodslist = goodService.listGoods();
        boolean b = ElasticsearchClient.bulkGoodIndex(goodslist);
        if (b){
            return "创建成功！";
        }
        return "创建失败！";
    }
}
