package com.yangzhuo.es.doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class EsDocBatchDelete {

    public static void main(String[] args) throws IOException {

        //创建es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );
        
        //批量删除数据
        BulkRequest bulkRequest = new BulkRequest();

        DeleteRequest deleteRequest = new DeleteRequest().index("user").id("1004");
        DeleteRequest deleteRequest1 = new DeleteRequest().index("user").id("1003");
        
        bulkRequest.add(deleteRequest, deleteRequest1);

        //响应状态
        BulkResponse bulkResponse = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);

        System.out.println(bulkResponse.getIngestTook());
        
        
        //关闭es客户端
        esClient.close();
    }
}
