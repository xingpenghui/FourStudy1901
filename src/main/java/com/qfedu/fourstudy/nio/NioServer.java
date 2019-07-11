package com.qfedu.fourstudy.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *@Author feri
 *@Date Created in 2019/7/11 11:23
 */
public class NioServer {
    //发送消息的Buffer
    private ByteBuffer sendMsg=ByteBuffer.allocate(1024);//容量1204个字节
    //接收消息的Buffer
    private ByteBuffer receiveMsg=ByteBuffer.allocate(1024);
    //创建集合 存储当前在线的所有用户
    private Map<String,SocketChannel> clients=new HashMap<>();
    //创建选择器
    private Selector selector;

    //初始化
    public void init(){
        try {
            //1、服务端 创建服务端网络套接字通道对象
            ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            //获取服务端套接字 可以监听套接字的连接
            ServerSocket serverSocket=serverSocketChannel.socket();
            //设置软件端口号
            serverSocket.bind(new InetSocketAddress(9999));
            //打开选择器 实现事件的监听
            selector=Selector.open();
            //实现事件注册 监听客户端的连接
            serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
            System.out.println("服务器已经启动……");
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
//                //传统遍历
//                for(SelectionKey k:keys){
//                    //验证事件
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //处理四大事件
    private void parse(SelectionKey key){
        if(key.isAcceptable()){
            System.out.println("客户端连接："+key.toString());
            //客户端 连接
            try {
                ServerSocketChannel socketChannel= (ServerSocketChannel) key.channel();
                SocketChannel schannel=socketChannel.accept();
                //设置IO的阻塞模式 true:阻塞 false:非阻塞
                schannel.configureBlocking(false);
                //监听读取消息
                schannel.register(selector,SelectionKey.OP_READ);
                //加入在线队列中
                clients.put(schannel.socket().getInetAddress().getHostAddress(),schannel);
                //发送消息 连接成功  单发
                sendMsg("欢迎加入高薪聊天室",schannel,2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(key.isReadable()){
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
                    String m=schannel.getRemoteAddress()+":说："+rmsg;
                    System.out.println(m);
                    //发送消息 将消息转发给聊天室所有人
                    sendMsg(m,schannel,1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //发送消息 type:类型 1转发 2单发
    private void sendMsg(String msg,SocketChannel socketChannel,int type){
        if(type==1){
            //转发
            if(clients.size()>0){
                for(String s:clients.keySet()){

                    SocketChannel sc=clients.get(s);
                    //验证是否为自己
                    if(!sc.equals(socketChannel)){
                       //如果不是自己 就需要发送
                        //单发
                        sendMsg.clear();
                        sendMsg.put(msg.getBytes());
                        sendMsg.flip();//切换
                        try {
                            //发送消息
                            sc.write(sendMsg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }else {
            //单发
            sendMsg.clear();
            sendMsg.put(msg.getBytes());
            sendMsg.flip();//切换
            try {
                //发送消息
                socketChannel.write(sendMsg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
