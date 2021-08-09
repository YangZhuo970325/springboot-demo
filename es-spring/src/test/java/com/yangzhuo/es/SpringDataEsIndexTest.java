package com.yangzhuo.es;

import com.yangzhuo.es.entity.Product;
import lombok.AllArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class SpringDataEsIndexTest {
    
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    
    @Test
    public void createIndex() {
        //创建索引，系统初始化时会自动创建索引
        System.out.println("创建索引");

        Map<String, Product> map = new HashMap<>();
        map.get("1231");
    }

    @Test
    public void deleteIndex() {
        //创建索引，系统初始化时会自动创建索引
        boolean flag = elasticsearchRestTemplate.deleteIndex(Product.class);
        System.out.println("删除索引：" + flag);
        
    }
}
