package com.qfedu.fourstudy.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.Set;

/**
 *@Author feri
 *@Date Created in 2019/7/11 11:23
 */
public class NioClient {
    //发送消息的Buffer
    private ByteBuffer sendMsg=ByteBuffer.allocate(1024);//容量1204个字节
    //接收消息的Buffer
    private ByteBuffer receiveMsg=ByteBuffer.allocate(1024);
    //创建选择器
    private Selector selector;
    public void init(){

        SocketChannel socketChannel= null;
        try {
            //打开连接
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            //打开选择器 实现事件的监听
            selector=Selector.open();
            //实现事件注册 监听客户端的连接
            socketChannel.register(selector,SelectionKey.OP_CONNECT);
            //连接服务器
            socketChannel.connect(new InetSocketAddress("localhost",9999));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //轮询  查看事件是否有变化
    public void lxlistener(){
        while (true){
            try {
                selector.select();//获取事件
                //处理事件
                Set<SelectionKey> keys=selector.selectedKeys();
                //遍历所有的事件
                keys.forEach((k -> parse(k)));
                keys.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //处理四大事件
    private void parse(SelectionKey key){
        //监听是否连接成功
        if(key.isConnectable()){
            try {
                SocketChannel schannel= (SocketChannel) key.channel();
                if(schannel.isConnectionPending()){
                    //连接是否可用
                    schannel.finishConnect();//确认连接
                    System.out.println("连接成功");
                    //开启发送消息模式
                    sendMsg(schannel);
                }
                //注册读取事件
                schannel.register(selector,SelectionKey.OP_READ);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(key.isReadable()){
            System.out.println("接收消息："+key.toString());
            //读取客户端的消息
            SocketChannel schannel= (SocketChannel) key.channel();
            //获取消息
            receiveMsg.clear();//清空缓冲区
            int blen= 0;
            try {
                blen = schannel.read(receiveMsg);
                if(blen>0){
                    receiveMsg.flip();//切换读写模式
                    String rmsg=new String(receiveMsg.array(),0,blen);
                    System.out.println("接收消息："+rmsg);
                }
                System.out.println(receiveMsg.position());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //发送消息 为了引起阻塞 开启多线程
    private void sendMsg(SocketChannel socketChannel){
        new Thread(()->{
            while (true) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("请输入要发送的内容：");
                String s = scanner.nextLine();
                sendMsg.clear();
                sendMsg.put(s.getBytes());
                sendMsg.flip();
                try {
                    //发送消息
                    socketChannel.write(sendMsg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
