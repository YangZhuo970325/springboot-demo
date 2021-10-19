package com.yangzhuo.redis;

import org.junit.Test;

import java.util.*;

public class MapTest {
    
    
    @Test
    public void test() {
        Map<String, String> map = new HashMap<>();
        map.put("username", "yz");
        map.put("age", "24");
        map.put("sex", "male");
        
        // 方法1：for循环遍历map的entry
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " -----> " + entry.getValue());
        }
        
        // 方法2：遍历map的key值
        Set<String> keys = map.keySet();
        for (String key : keys) {
            Object value = map.get(key);
            System.out.println(key + " ------> " + value);
        }
        
        // 方法3：迭代器遍历map的entry的方式
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
            String key = entry.getKey();
            String value = entry.getKey();
            System.out.println(key + " ------> " + value);
        }
        
        // 方法4：迭代器遍历key的方式
        Iterator<String> iterator1 = map.keySet().iterator();
        while (iterator1.hasNext()) {
            String key = iterator1.next();
            Object value = map.get(key);
            System.out.println(key + " ------> " + value);
        }

        Set<String> set = new HashSet();
        set.add("12");
        set.remove("12");
        
    }
    
}
