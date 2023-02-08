package com.yangzhuo.zookeeper.distribute.lock.service.impl;

import com.yangzhuo.zookeeper.distribute.lock.service.ZkLock;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class DistZkLock implements ZkLock, Watcher {

    private final ZooKeeper zooKeeper;

    private final String businessName;

    private String businessNode;

    private final Consumer<String> closeHandler;

    // 方式二
    // CountDownLatch countDownLatch = new CountDownLatch(1);

    public DistZkLock(String connectString, String businessName, Consumer<String> closeHandler) throws IOException {
        this.zooKeeper = new ZooKeeper(connectString, 30000, this);
        this.businessName = businessName;
        this.closeHandler = closeHandler;
    }

    @Override
    public Boolean lock() {
        try {
            // 创建业务 根节点 /order
            Stat exists = zooKeeper.exists("/" + businessName, false);
            if (exists == null) {
                zooKeeper.create("/" + businessName,
                        businessName.getBytes(),
                        ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.PERSISTENT);
            }
            // 创建锁 临时有序节点 /order/order_00000001
            businessNode = zooKeeper.create("/" + businessName + "/" + businessName + "_", businessName.getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);
            // 获取业务 根节点的所有子节点 并 排序，获取最小的序号的节点
            List<String> childrenNodes = zooKeeper.getChildren("/" + businessName, false);
            Collections.sort(childrenNodes);
            String firstNode = childrenNodes.get(0);
            // 如果创建的节点是第一个节点（最小的节点），则获得锁
            if (businessNode.endsWith(firstNode)) {
                return true;
            }

            // 如果未获得锁，则等待上一节点（监听节点）
            String previousNode = firstNode;
            for (String node : childrenNodes) {
                if (businessNode.endsWith(node)) {
                    // 创建上一节点的监听器
                    zooKeeper.exists("/" + businessName + "/" + previousNode, true);
                    break;
                } else {
                    // 设置下一个节点的前序节点，以便设置监听（需要监听的节点后移）
                    // 例如当前获取锁的节点是node_00001,    node_00002监听node_00001
                    // 当前业务创建的节点是node_00003,则previousNode节点从node_00001后移至node_00002
                    // 便于遍历到node_00003时，进行监听
                    previousNode = node;
                }
            }

            // 等待上一个节点释放
            // 方式一
            synchronized (this) {
                wait();
            }
            // 方式二
            // countDownLatch.await();

            // 结束等待，则获取锁，返回成功
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void close() throws Exception {
        zooKeeper.delete(businessNode, -1);
        zooKeeper.close();
        closeHandler.accept(businessNode);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (Event.EventType.NodeDeleted.equals(watchedEvent.getType())) {
            // 方式一
            synchronized (this) {
                notify();
            }
            // 方式二
            //countDownLatch.countDown();
        }
    }
}
