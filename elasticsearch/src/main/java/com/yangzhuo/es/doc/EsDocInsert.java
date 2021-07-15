package com.yangzhuo.es.doc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yangzhuo.es.entity.User;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class EsDocInsert {

    public static void main(String[] args) throws IOException {

        //创建es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );
        
        //插入数据
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.index("user").id("1001");
        User user = new User();
        user.setName("张三");
        user.setAge(20);
        user.setSex("male");
        
        //向es插入数据,必须将数据位转换为json格式
        ObjectMapper mapper = new ObjectMapper();
        String userJson = mapper.writeValueAsString(user);
        indexRequest.source(userJson, XContentType.JSON);

        //响应状态
        IndexResponse indexResponse = esClient.index(indexRequest, RequestOptions.DEFAULT);

        System.out.println(indexResponse);
        
        
        //关闭es客户端
        esClient.close();
    }
}
