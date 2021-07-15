package com.yangzhuo.es.doc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yangzhuo.es.entity.User;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class EsDocBatchInsert {

    public static void main(String[] args) throws IOException {

        //创建es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );
        
        //批量插入数据
        BulkRequest bulkRequest = new BulkRequest();
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.index("user").id("1005");
        User user = new User();
        user.setName("马化腾");
        user.setAge(40);
        user.setSex("male");

        IndexRequest indexRequest1 = new IndexRequest();
        indexRequest1.index("user").id("1006");
        User user1 = new User();
        user1.setName("马云");
        user1.setAge(50);
        user1.setSex("male");

        IndexRequest indexRequest2 = new IndexRequest();
        indexRequest2.index("user").id("1003");
        User user2 = new User();
        user2.setName("王五");
        user2.setAge(21);
        user2.setSex("male");
        
        //向es插入数据,必须将数据位转换为json格式
        ObjectMapper mapper = new ObjectMapper();
        String userJson = mapper.writeValueAsString(user);
        indexRequest.source(userJson, XContentType.JSON);

        ObjectMapper mapper1 = new ObjectMapper();
        String userJson1 = mapper1.writeValueAsString(user1);
        indexRequest1.source(userJson1, XContentType.JSON);

        ObjectMapper mapper2 = new ObjectMapper();
        String userJson2 = mapper2.writeValueAsString(user2);
        indexRequest2.source(userJson2, XContentType.JSON);

        IndexRequest indexRequest3 = 
                new IndexRequest().index("user").id("1004").source(XContentType.JSON, "name", "雷军", "sex", "male", "age", 24);
        
        bulkRequest.add(indexRequest, indexRequest1, indexRequest2, indexRequest3);

        //响应状态
        BulkResponse bulkResponse = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);

        System.out.println(bulkResponse.getIngestTook());
        
        
        //关闭es客户端
        esClient.close();
    }
}
