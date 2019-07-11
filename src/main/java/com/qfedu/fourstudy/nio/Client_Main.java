package com.qfedu.fourstudy.nio;

/**
 *@Author feri
 *@Date Created in 2019/7/11 11:23
 */
public class Client_Main {
    public static void main(String[] args) {
        NioClient nioClient=new NioClient();
        nioClient.init();
        nioClient.lxlistener();
    }
}
