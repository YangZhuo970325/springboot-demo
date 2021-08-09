package com.yangzhuo.es;

import com.yangzhuo.es.dao.ProductDao;
import com.yangzhuo.es.entity.Product;
import org.elasticsearch.common.joda.Joda;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.management.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataESProductDaoTest {
    
    @Autowired
    private ProductDao productDao;

    /**
     * 新增
     */
    @Test
    public void save() {
        Product product = new Product();
        product.setId(2L);
        product.setTitle("小米手机");
        product.setCategory("手机");
        product.setPrice(3999.00);
        product.setImages("http://www.baiodu.com");
        productDao.save(product);
    }

    /**
     * 修改
     */
    @Test
    public void update() {
        Product product = new Product();
        product.setId(2L);
        product.setTitle("小米手机11");
        product.setCategory("手机");
        product.setPrice(3999.00);
        product.setImages("http://www.baiodu.com");
        productDao.save(product);
    }

    /**
     * 根据id查找
     */
    @Test
    public void findById() {
        Product product = productDao.findById(2L).get();
        System.out.println(product);
    }

    /**
     * 查找全部
     */
    @Test
    public void findAll() {
        Iterable<Product> products = productDao.findAll();
        for (Product product : products) {
            System.out.println(product);
        }
    }


    /**
     * 删除
     */
    @Test
    public void delete() {
        Product product = new Product();
        product.setId(2L);
        productDao.delete(product);
    }

    /**
     * 批量新增
     */
    @Test
    public void saveAll() {
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setId(2L);
            product.setTitle("小米手机" + i);
            product.setCategory("手机");
            product.setPrice(3999.00);
            product.setImages("http://www.baiodu.com");
            productList.add(product);
        }
        productDao.saveAll(productList);        
    }

    /**
     * 分页查询
     */
    @Test
    public void findByPageable() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        int from = 0;
        int size = 5;
        PageRequest pageRequest = PageRequest.of(from, size, sort);
        //分页查询
        Page<Product> productPage = productDao.findAll(pageRequest);
        for (Product product : productPage) {
            System.out.println(product);
        }
    }

    /**
     * 批量删除
     */
    @Test
    public void batchDelete() {
        Product product = new Product();
        product.setId(2L);
        productDao.delete(product);
    }

    /**
     * term查询
     */
    @Test
    public void termQuery() {
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", "小米");
        Iterable<Product> products = productDao.search(termQueryBuilder);
        for (Product product : products) {
            System.out.println(product);
        }
    }

    /**
     * term查询
     */
    @Test
    public void termQueryByPage() {
        int from = 0;
        int size = 5;
        PageRequest pageRequest = PageRequest.of(from, size);
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", "小米");
        Iterable<Product> products = productDao.search(termQueryBuilder, pageRequest);
        for (Product product : products) {
            System.out.println(product);
        }
    }
}
