package com.starsmobi.controller;

import com.google.common.collect.Lists;
import com.starsmobi.domain.GoodsEs;
import com.starsmobi.domain.SearchEntity;
import com.starsmobi.domain.SearchRequest;
import com.starsmobi.domain.result.EntityResult;
import com.starsmobi.domain.result.SearchResult;
import com.starsmobi.elasticsearchclient.ElasticsearchClient;
import com.starsmobi.service.IGoodService;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchException;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cglib.core.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by vincent on 2017-08-02.
 */
@RestController
@RequestMapping("/goodindex")
public class GoodController {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchClient.class);

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

    @ApiOperation(value = "搜索商品")
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    public SearchResult search(@RequestBody SearchRequest searchRequest){
//        if (StringUtils.isEmpty(searchRequest.getKeywords())){
//            return new SearchResult(400,"关键字不能为空");
//        }

        SearchEntity searchEntity = new SearchEntity();
        searchEntity.setKeywords(searchRequest.getKeywords());

        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("name", searchRequest.getKeywords());

        if (!StringUtils.isEmpty(searchRequest.getStoreId())){
            fieldMap.put("stores.storeId", searchRequest.getStoreId());
        }
        searchEntity.setFieldMap(fieldMap);

        String index = "go";
        String type = "go";

        /*
          排序
         */
        searchEntity.setSortOrder("asc");
        searchEntity.setOrderField("price");


        final SearchResponse searchResponse = ElasticsearchClient.search(index,type,searchEntity);

        return buildResult(searchResponse);
    }

    /**
     * 根据es返回的数据构造查询结果
     *
     * @param response es  response
     * @return SearchResult
     * @throws SearchException 异常信息
     */
    private SearchResult buildResult(SearchResponse response){
        if (response == null || response.getHits() == null || response.getHits().getHits()==null) {
            return new SearchResult(201,"未查询到到任何数据！");
        }
        SearchResult searchResult = new SearchResult();
        searchResult.setCode(200);
        searchResult.setMessage("success");
        try {
            LOGGER.info("++++++PPPPPP");
            final ArrayList<SearchHit> searchHits = Lists.newArrayList(response.getHits().getHits());
            LOGGER.info("+++++++++++++dasdasdasdasd");
            if (null != searchHits && searchHits.size() != 0) {
                LOGGER.info("+++++++++++++88888888888");
                final List<EntityResult> entityResultList = searchHits.stream().filter(Objects::nonNull)
                        .map(this::buildEntityResultBySearchHit).collect(Collectors.toList());
                searchResult.setEntityResultList(entityResultList);
            }
        } catch (Exception e) {
            searchResult.setCode(201);
            searchResult.setMessage("失败");
            return new SearchResult(201,"查询发生异常信息");
        }

       LOGGER.info("++++++++++:"+searchResult.getEntityResultList().size());
        return searchResult;
    }

    /**
     * 构建返回对象实体
     * @param searchHit es返回的实体
     * @return EntityResult
     */
    private EntityResult buildEntityResultBySearchHit(SearchHit searchHit){

        final Map<String, Object> source = searchHit.getSource();
        EntityResult entityResult = new EntityResult();
        LOGGER.info("++++++++++:"+String.valueOf(source.get("name")));
        if (source != null) {
            entityResult.setId(String.valueOf(source.get("id")));
            entityResult.setName(String.valueOf(source.get("name")));

            LOGGER.info("++++++++++:"+String.valueOf(source.get("name")));

            entityResult.setGoodsCategoryId(String.valueOf(source.get("goodsCategoryId")));
            entityResult.setGoodsTypeId(String.valueOf(source.get("goodsTypeId")));
            entityResult.setGoodsTypeName(String.valueOf(source.get("goodsTypeName")));
            entityResult.setProviderId(String.valueOf(source.get("providerId")));
            entityResult.setProviderName(String.valueOf(source.get("providerName")));
            entityResult.setThumbnail(String.valueOf(source.get("thumbnail")));
            if(Objects.nonNull(source.get("price"))){
                entityResult.setPrice(new BigDecimal(String.valueOf(source.get("price"))));
            }
            if(Objects.nonNull(source.get("costPrice"))){
                entityResult.setPrice(new BigDecimal(String.valueOf(source.get("costPrice"))));
            }
            if(Objects.nonNull(source.get("originalPrice"))){
                entityResult.setPrice(new BigDecimal(String.valueOf(source.get("originalPrice"))));
            }
            entityResult.setBarcode(String.valueOf(source.get("barcode")));
            entityResult.setCode(String.valueOf(source.get("code")));
        }
        return  entityResult;
    }
}
