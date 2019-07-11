package com.qfedu.fourstudy.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *@Author feri
 *@Date Created in 2019/6/28 14:15
 * Exchange 的Direct模式
 */
public class DirectSend_Main {
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel=RabbitMQUtil.channel;
        //1、定义Exchange
        /**
         * 参数说明：
         * 1、交换机名称
         * 2、类型
         * 3、是否持久化
         * 4、是否自动删除
         * 5、是否为内置交换机  如果为内置交换机，那么外界无法使用
         * 6、属性
         * */
        channel.exchangeDeclare("direct_1901",BuiltinExchangeType.DIRECT,false,true,false,null);
        //2、发送消息
        channel.basicPublish("direct_1901","log",null,("日志记录："+System.currentTimeMillis()).getBytes());
        //3、销毁
        channel.close();
    }
}
