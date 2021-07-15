package com.yangzhuo.es.index;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class EsIndexDelete {

    public static void main(String[] args) throws IOException {

        //创建es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );
        
        //删除索引
        DeleteIndexRequest request = new DeleteIndexRequest("user");
        AcknowledgedResponse acknowledgedResponse = 
                esClient.indices().delete(request, RequestOptions.DEFAULT);

        //响应状态
        boolean acknowledged = acknowledgedResponse.isAcknowledged();
        System.out.println("索引操作：" + acknowledged);
        
        //关闭es客户端
        esClient.close();
    }
}
