package com.qfedu.fourstudy.nio;

/**
 *@Author feri
 *@Date Created in 2019/7/11 11:23
 */
public class Server_Main {
    public static void main(String[] args) {
        //启动服务器
        NioServer nioServer=new NioServer();
        nioServer.init();
        nioServer.lxlistener();
        System.out.println("聊天室已经启动……");
    }
}
