package com.yangzhuo.threadpool;

import java.util.Random;
import java.util.concurrent.Callable;

public class JeDocPostThread implements Callable<Person> {
    
    @Override
    public Person call() throws Exception{
        
        Random random = new Random();

        System.out.println("线程名称：" + Thread.currentThread().getName());
        System.out.println("线程号：" + Thread.currentThread().getId());
        Person person = new Person();
        person.setName(Thread.currentThread().getName());
        person.setAge(random.nextInt(100));
        person.setSex("male");
        
        Thread.sleep(1000);

        return person;
    }

}
