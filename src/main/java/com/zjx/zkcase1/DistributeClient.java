package com.zjx.zkcase1;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DistributeClient {
    private static String connectString="192.168.119.129:2181,192.168.119.131:2181,192.168.119.132:2181";
    private static int sessionTimeOut=200000;
    private static ZooKeeper zk=null;
    private static String parentNode="/zengjinxin";
    //获取zk连接
    public static void getConnect() throws IOException {
        zk = new ZooKeeper(connectString, sessionTimeOut, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                // 再次启动监听
                try {
                    getServerList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // 获取服务器列表信息
    private static void getServerList() throws InterruptedException, KeeperException {

        // 1 获取服务器子节点信息，并且对父节点进行监听
        List<String> children = zk.getChildren(parentNode, true);
        // 2 存储服务器信息列表
        ArrayList<String> servers = new ArrayList<String>();
        //遍历子节点
        for (String child : children) {
            byte[] data = zk.getData(parentNode + "/" + child, true, null);
            servers.add(new String(data));
        }
        // 4 打印服务器列表信息
        System.out.println(servers);
    }

    // 业务功能
    public static void business() throws Exception{
        System.out.println("client is working ...");
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws Exception {
        //获取连接
        getConnect();
        //获取服务器列表
        getServerList();
        //业务功能
        business();

    }
}
