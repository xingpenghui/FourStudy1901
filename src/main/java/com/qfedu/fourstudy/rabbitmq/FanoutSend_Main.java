package com.qfedu.fourstudy.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *@Author feri
 *@Date Created in 2019/6/28 14:15
 */
public class FanoutSend_Main {
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel=RabbitMQUtil.channel;
        //1、定义Exchange
        channel.exchangeDeclare("fanout_1901",BuiltinExchangeType.FANOUT);
        //2、发送消息
        channel.basicPublish("fanout_1901","",null,"我是交换机过来的消息".getBytes());
        //3、销毁
        channel.close();
    }
}
