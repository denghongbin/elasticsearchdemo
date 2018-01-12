package com.starsmobi.elasticsearchclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starsmobi.domain.GoodsEs;
import com.starsmobi.domain.SearchEntity;
import com.starsmobi.domain.StoresEs;
import com.starsmobi.service.IGoodService;
import com.vividsolutions.jts.geom.Coordinate;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.geo.builders.ShapeBuilders;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoShapeQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.locationtech.spatial4j.context.SpatialContextFactory;
import org.locationtech.spatial4j.io.GeoJSONWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.*;
import org.elasticsearch.common.geo.ShapeRelation;
import org.elasticsearch.common.geo.builders.ShapeBuilder;


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
     * 创建商品索引
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

    /**
     * 创建店铺索引模版
     * @param
     * @return
     */
    public static boolean bulkStoresIndex(){
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

        // 创建Mapping
        XContentBuilder mapping = createMapping("storess");

        try {
            System.out.println("mapping:" + mapping.string());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //创建空索引
        client.admin().indices().prepareCreate("storess").execute().actionGet();
        LOGGER.info("elasticsearch 创建空索引");


        PutMappingRequest putMapping = Requests.putMappingRequest("storess").type("storess").source(mapping);
        PutMappingResponse response = client.admin().indices().putMapping(putMapping).actionGet();
        if (!response.isAcknowledged()) {
            System.out.println("Could not define mapping for type [" + "stores" + "]/[" + "stores" + "].");
        } else {
            System.out.println("Mapping definition for [" + "stores" + "]/[" + "stores" + "] succesfully created.");
        }

        client.close();
        return true;
    }


    /**
     * 导入店铺索引数据
     * @param
     * @return
     */
    public static boolean importDate(List<StoresEs> storesEs){
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

        List<String> stores = new ArrayList<String>();
        storesEs.forEach(s->{
            stores.add(obj2JsonData(s));
        });

        // 创建索引库
        List<IndexRequest> requests = new ArrayList<IndexRequest>();
        for (int i = 0; i < stores.size(); i++) {

            IndexRequest request = client.prepareIndex("hh", "hh").setSource(stores.get(i)).request();
            //client.prepareIndex("stores", "stores").setSource(stores.get(i)).execute();
            LOGGER.info("elasticsearch 创建索引成功"+i);
            requests.add(request);

        }

        // 批量创建索引
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (IndexRequest request : requests) {
            bulkRequest.add(request);
        }

        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        if (bulkResponse.hasFailures()) {
            System.out.println("批量创建索引错误！");
        }

        client.close();
        return true;
    }


    // 创建店铺mapping
    public static XContentBuilder createMapping(String indexType) {
        XContentBuilder mapping = null;
        try {
            mapping = jsonBuilder().startObject()
                    // 索引库名（类似数据库中的表）
                    .startObject(indexType).startObject("properties")
                    // ID
                    .startObject("Id").field("type", "string").endObject()
                    // 店铺id
                    .startObject("storeId").field("type", "string").endObject()
                    // 店铺名称
                    .startObject("name").field("type", "string").endObject()
                    // 坐标集合
                    .startObject("location").field("type", "geo_shape").endObject()
                    // 创建时间
                    .startObject("createTime").field("type", "string").endObject()
                    // 更新时间
                    .startObject("updateTime").field("type", "string").endObject()
                    // 是否删除
                    .startObject("isDisable").field("type", "string").endObject()
                    .endObject().endObject().endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapping;
    }


    public static String obj2JsonData(StoresEs s){
        String jsonData = null;

        try {
            XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
            jsonBuild.startObject()
                    .field("name", s.getName())
                    .startObject("location").field("type","multipolygon").field("coordinates", s.getLocation()).endObject()
                    .endObject();

            jsonData = jsonBuild.string();
            System.out.println(jsonData);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData;
    }


    public static SearchResponse searchStores(double[] coor){
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

        Coordinate coordinate = new Coordinate(coor[0], coor[1]);

        GeoShapeQueryBuilder qb = null;
        try {
            qb = geoShapeQuery(
                    "pin.location",
                    ShapeBuilders.newPoint(coordinate));
        } catch (IOException e) {
            e.printStackTrace();
        }
        qb.relation(ShapeRelation.INTERSECTS);

        SearchResponse response = client.prepareSearch("to")
                .setTypes("to")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(qb)
                .setFrom(0)
                .setSize(20)
                .execute()
                .actionGet();


        LOGGER.info("查找成功");
        client.close();
        return response;

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
