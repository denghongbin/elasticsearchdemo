package com.starsmobi.elasticsearchclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starsmobi.domain.GoodsEs;
import com.starsmobi.domain.SearchEntity;
import com.starsmobi.service.IGoodService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.elasticsearch.index.query.QueryBuilders.*;


/**
 * Created by vincent on 2017-08-02.
 */
public class ElasticsearchClient {

    private static TransportClient client;

    /**
     * jackson用于序列化操作的mapper
     */
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final int DEFAULT_PORT = 9300;//ElasticsearchClient 集群的主节点的默认是9300

    @Autowired
    private IGoodService goodService;

    private static boolean t = true;
    private static boolean f = false;


    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchClient.class);

    private void elasticSearchClient(){
        //创建ES 客户端连接
        Settings settings = Settings.builder().put("client.transport.sniff",true)
                .put("cluster.name","mygoods").build();
        try {
             client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        } catch (UnknownHostException e) {
        }
        LOGGER.info("elasticsearch 客户端初始化成功!");
    }

    /**
     * 创建索引
     * @param list
     * @return
     */
    public static boolean bulkGoodIndex(List<GoodsEs> list){

        //创建ES 客户端连接
        Settings settings = Settings.builder().put("client.transport.sniff",true)
                .put("cluster.name","vincent-application").build();
        try {
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        } catch (UnknownHostException e) {
            LOGGER.info("elasticsearch UnknownHostException 客户端连接失败");
        }
        LOGGER.info("elasticsearch 客户端初始化成功!");

        LOGGER.info("elasticsearch 开始创建索引"+list.size());
        list.forEach(goodsEs -> {
            try {
                final String goodsJson = objectMapper.writeValueAsString(goodsEs);
                client.prepareIndex("go", "go", goodsEs.getId()).setSource(goodsJson, XContentType.JSON).execute();
                LOGGER.info("elasticsearch 创建索引成功");
            } catch (JsonProcessingException e) {
                LOGGER.info("商品对象json格式化异常:{}");
            }
        });
        client.close();
        return t;
    }

    public static SearchResponse search(String index, String type, SearchEntity searchEntity){

        //创建ES 客户端连接
        Settings settings = Settings.builder().put("client.transport.sniff",true)
                .put("cluster.name","vincent-application").build();
        try {
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        } catch (UnknownHostException e) {
            LOGGER.info("elasticsearch UnknownHostException 客户端连接失败");
        }
        LOGGER.info("elasticsearch 客户端初始化成功!");



        Map<String, Object> fieldMap = searchEntity.getFieldMap();
        LOGGER.info("++++"+fieldMap.get("name")+"=++++++++"+fieldMap.get("stores.storeId"));

        if (fieldMap == null || fieldMap.isEmpty()) {
            return null;
        }
        SortOrder sortOrder = buildSort(searchEntity);

        BoolQueryBuilder bqb = boolQuery();

        //bqb.must(matchQuery("name",fieldMap.get("name")));


        fieldMap.forEach((key, value) -> bqb.must(matchQuery(key, value)));

        LOGGER.info("index:"+index);
        SearchResponse response = client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(bqb)
                .addSort(SortBuilders.scoreSort())
                .addSort(searchEntity.getOrderField(), sortOrder)
                .setFrom(0)
                .setSize(20)
                .execute()
                .actionGet();
        LOGGER.info("查找成功");
        client.close();
        return response;
    }

    /**
     * 构建排序信息
     *
     * @param searchEntity 查询实体类
     * @return SortOrder
     */
    private static SortOrder buildSort(SearchEntity searchEntity) {
        SortOrder sortOrder;
        if (Objects.equals(searchEntity.getSortOrder(), "asc")) {
            sortOrder = SortOrder.ASC;
        } else if (Objects.equals("desc", searchEntity.getSortOrder())) {
            sortOrder = SortOrder.DESC;
        } else {
            sortOrder = SortOrder.DESC;
        }
        return sortOrder;
    }
}
