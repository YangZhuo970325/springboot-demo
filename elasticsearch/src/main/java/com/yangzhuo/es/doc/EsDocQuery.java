package com.yangzhuo.es.doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;

public class EsDocQuery {

    public static void main(String[] args) throws IOException {

        //创建es客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );
        
        //1. 查询索引中全部数据
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("user");
        searchRequest.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));

        //响应状态
        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);

        for (SearchHit hit : searchResponse.getHits()) {
            System.out.println(hit.getSourceAsString());
        }

        // 2. 条件查询
        SearchRequest searchRequest1 = new SearchRequest();
        searchRequest1.indices("user");
        searchRequest1.source(new SearchSourceBuilder().query(QueryBuilders.termQuery("age", 40)));

        //响应状态
        SearchResponse searchResponse1 = esClient.search(searchRequest1, RequestOptions.DEFAULT);

        for (SearchHit hit : searchResponse1.getHits()) {
            System.out.println(hit.getSourceAsString());
        }

        //3. 分页查询索引中全部数据
        SearchRequest searchRequest2 = new SearchRequest();
        searchRequest2.indices("user");
        
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        builder.from(2);
        builder.size(2);
        searchRequest2.source(builder);

        //响应状态
        SearchResponse searchResponse2 = esClient.search(searchRequest2, RequestOptions.DEFAULT);

        for (SearchHit hit : searchResponse2.getHits()) {
            System.out.println(hit.getSourceAsString());
        }

        //4. 排序查询索引中全部数据
        SearchRequest searchRequest3 = new SearchRequest();
        searchRequest3.indices("user");

        SearchSourceBuilder builder3 = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        builder3.sort("age", SortOrder.ASC);
        searchRequest3.source(builder3);

        //响应状态
        SearchResponse searchResponse3 = esClient.search(searchRequest3, RequestOptions.DEFAULT);

        for (SearchHit hit : searchResponse3.getHits()) {
            System.out.println(hit.getSourceAsString());
        }


        //5. 过滤字段查询索引中全部数据
        SearchRequest searchRequest4 = new SearchRequest();
        searchRequest4.indices("user");

        SearchSourceBuilder builder4 = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        builder4.sort("age", SortOrder.ASC);
        
        String[] excludes = {"sex"};
        String[] includes = {"name", "age", "sex"};
        builder4.fetchSource(includes, excludes);
        searchRequest4.source(builder4);

        //响应状态
        SearchResponse searchResponse4 = esClient.search(searchRequest4, RequestOptions.DEFAULT);

        for (SearchHit hit : searchResponse4.getHits()) {
            System.out.println(hit.getSourceAsString());
        }


        //6. 组合查询
        SearchRequest searchRequest5 = new SearchRequest();
        searchRequest5.indices("user");

        SearchSourceBuilder builder5 = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        
        boolQueryBuilder.must(QueryBuilders.matchQuery("age", 21));
        boolQueryBuilder.mustNot(QueryBuilders.matchQuery("sex", "female"));
        builder5.query(boolQueryBuilder);
        
        searchRequest5.source(builder5);

        //响应状态
        SearchResponse searchResponse5 = esClient.search(searchRequest5, RequestOptions.DEFAULT);

        for (SearchHit hit : searchResponse5.getHits()) {
            System.out.println(hit.getSourceAsString());
        }

        //7. 范围查询
        SearchRequest searchRequest6 = new SearchRequest();
        searchRequest6.indices("user");

        SearchSourceBuilder builder6 = new SearchSourceBuilder();
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age");

        rangeQueryBuilder.gte(40);
        rangeQueryBuilder.lte(50);
        builder6.query(rangeQueryBuilder);

        searchRequest6.source(builder6);

        //响应状态
        SearchResponse searchResponse6 = esClient.search(searchRequest6, RequestOptions.DEFAULT);

        for (SearchHit hit : searchResponse6.getHits()) {
            System.out.println(hit.getSourceAsString());
        }

        //8. 模糊查询
        SearchRequest searchRequest7 = new SearchRequest();
        searchRequest7.indices("user");

        SearchSourceBuilder builder7 = new SearchSourceBuilder();
        FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery("name", "马").fuzziness(Fuzziness.ZERO);
        
        builder7.query(fuzzyQueryBuilder);

        searchRequest7.source(builder7);

        //响应状态
        SearchResponse searchResponse7 = esClient.search(searchRequest7, RequestOptions.DEFAULT);

        for (SearchHit hit : searchResponse7.getHits()) {
            System.out.println("fuzzy:" + hit.getSourceAsString());
        }


        //9. 高亮查询
        SearchRequest searchRequest8 = new SearchRequest();
        searchRequest8.indices("user");

        SearchSourceBuilder builder8 = new SearchSourceBuilder();
        FuzzyQueryBuilder fuzzyQueryBuilder1 = QueryBuilders.fuzzyQuery("name", "马").fuzziness(Fuzziness.ZERO);

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color = 'red'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("name");
        
        builder8.highlighter(highlightBuilder);
        builder8.query(fuzzyQueryBuilder1);

        searchRequest8.source(builder8);

        //响应状态
        SearchResponse searchResponse8 = esClient.search(searchRequest8, RequestOptions.DEFAULT);

        for (SearchHit hit : searchResponse8.getHits()) {
            System.out.println("highlight:" + hit);
        }

        //10. 聚合查询
        SearchRequest searchRequest9 = new SearchRequest();
        searchRequest9.indices("user");

        SearchSourceBuilder builder9 = new SearchSourceBuilder();
        AggregationBuilder aggregationBuilder = AggregationBuilders.max("maxAge").field("age");
        builder9.aggregation(aggregationBuilder);

        searchRequest9.source(builder9);

        //响应状态
        SearchResponse searchResponse9 = esClient.search(searchRequest9, RequestOptions.DEFAULT);
        System.out.println("Aggregations :" + searchResponse9);
        for (SearchHit hit : searchResponse9.getHits()) {
            System.out.println("Aggregation:" + hit.getSourceAsString());
        }

        //11. 分组查询
        SearchRequest searchRequest10 = new SearchRequest();
        searchRequest10.indices("user");

        SearchSourceBuilder builder10 = new SearchSourceBuilder();
        AggregationBuilder aggregationBuilder1 = AggregationBuilders.terms("ageGroup").field("age");
        builder10.aggregation(aggregationBuilder1);

        searchRequest10.source(builder10);

        //响应状态
        SearchResponse searchResponse10 = esClient.search(searchRequest10, RequestOptions.DEFAULT);
        System.out.println("group :" + searchResponse10);
        for (SearchHit hit : searchResponse10.getHits()) {
            System.out.println("group:" + hit.getSourceAsString());
        }
        
        //关闭es客户端
        esClient.close();
    }
}
