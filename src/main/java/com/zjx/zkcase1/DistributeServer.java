package com.zjx.zkcase1;

import org.apache.zookeeper.*;

import java.io.IOException;

public class DistributeServer {
    private static String connectString="192.168.119.129:2181,192.168.119.131:2181,192.168.119.132:2181";
    private static int sessionTimeOut=200000;
    private ZooKeeper zk=null;
    private String parentNode="/zengjinxin";

    //获取zk连接
    public void getConnect() throws IOException {
        zk = new ZooKeeper(connectString, sessionTimeOut, new Watcher() {
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }
    //注册服务器
    public void registServer(String hostName) throws InterruptedException, KeeperException {
        String create = zk.create(parentNode + "/guoqin", hostName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(hostName +" is online "+ create);
    }
    //业务代码
    public void business(String hostName) throws InterruptedException {
        System.out.println(hostName + " is working ...");
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws Exception {
        DistributeServer distributeServer = new DistributeServer();
        //获取连接
        distributeServer.getConnect();
        //创建server节点
        distributeServer.registServer("guoqin");
        //业务代码
        distributeServer.business("guoqin");
    }
}
