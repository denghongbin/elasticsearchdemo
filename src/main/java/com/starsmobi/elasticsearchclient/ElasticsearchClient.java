package com.starsmobi.elasticsearchclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starsmobi.domain.GoodsEs;
import com.starsmobi.domain.StoreEs;
import com.starsmobi.service.IGoodService;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;


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

//                List<StoreEs> storeEsList = goodsEs.getStores();
//                final String storeEs = objectMapper.writeValueAsString(storeEsList);
//                LOGGER.info("elasticsearch 创建索引成功"+storeEs);
//                client.prepareUpdate("go", "go", goodsEs.getId()).setDoc(storeEs, XContentType.JSON);

                LOGGER.info("elasticsearch 创建索引成功");
            } catch (JsonProcessingException e) {
                LOGGER.info("商品对象json格式化异常:{}");
            }
        });
        client.close();
        return t;
    }
}
