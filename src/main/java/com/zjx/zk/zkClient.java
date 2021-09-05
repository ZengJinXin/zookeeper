package com.zjx.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class zkClient {
    private static String connectString = "192.168.119.129:2181,192.168.119.131:2181,192.168.119.132:2181";//服务器ip
    private static int sessionTimeOut = 200000;//超时事件
    private ZooKeeper zkClient = null;//客户端

    @Before
    public void init() throws IOException {
        zkClient = new ZooKeeper(connectString, sessionTimeOut, new Watcher() {
            public void process(WatchedEvent watchedEvent) {

                System.out.println("---------------------------");
                // 收到事件通知后的回调函数（用户的业务逻辑）
                System.out.println(watchedEvent.getType() + "--" + watchedEvent.getPath());
                // 再次启动监听
                try {
                    System.out.println("-----------------------------");
                    List<String> children = zkClient.getChildren("/", true);
                    for (String child : children) {
                        System.out.println(child);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Test
    public void create() throws InterruptedException, KeeperException {
        String s = zkClient.create("/zeng", "zengjinxin".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    @Test
    public void getChildNode() throws InterruptedException, KeeperException {
        List<String> children = zkClient.getChildren("/", true);
        for (String child : children) {
            System.out.println(child);
        }
        // 延时阻塞
        Thread.sleep(Long.MAX_VALUE);
    }
    @Test
    public void exiet() throws InterruptedException, KeeperException {
        Stat stat = zkClient.exists("/zeng", true);
        System.out.println(stat==null?"false":"true");

    }
}
