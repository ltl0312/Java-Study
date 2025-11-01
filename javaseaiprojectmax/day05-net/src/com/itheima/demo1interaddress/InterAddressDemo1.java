package com.itheima.demo1interaddress;

import java.net.*;

public class InterAddressDemo1 {
    public static void main(String[] args) {
        // 目标：认识InterAdress获取本机IP对象和对方IP对象
        try {
            //1.获取本机IP对象
            InetAddress local = InetAddress.getLocalHost();
            System.out.println(local);

            //2.获取对方IP对象
            InetAddress remote = InetAddress.getByName("www.baidu.com");
            //System.out.println(remote);
            System.out.println(remote.getHostName());
            System.out.println(remote.getHostAddress());

            //判断本机和对方主机是否互通
            boolean reachable = remote.isReachable(5000);
            System.out.println(reachable);
            System.out.println(reachable ? "互通" : "不通");
            System.out.println(remote.isSiteLocalAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
