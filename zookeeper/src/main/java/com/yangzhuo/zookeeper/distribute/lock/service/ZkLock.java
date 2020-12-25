package com.yangzhuo.zookeeper.distribute.lock.service;

public interface ZkLock extends AutoCloseable {

    public Boolean lock();

}
