package com.yangzhuo.es.doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class EsDocDelete {

    public static void main(String[] args) throws IOException {

        //创建es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );
        
        //查询数据
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.index("user").id("1003");

        //响应状态
        DeleteResponse deleteResponse = esClient.delete(deleteRequest, RequestOptions.DEFAULT);

        System.out.println(deleteResponse.getResult());
        
        //关闭es客户端
        esClient.close();
    }
}
