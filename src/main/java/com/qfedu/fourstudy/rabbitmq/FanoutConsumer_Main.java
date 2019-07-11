package com.qfedu.fourstudy.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *@Author feri
 *@Date Created in 2019/6/28 14:15
 */
public class FanoutConsumer_Main {
    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("消费者已经启动");
        Channel channel=RabbitMQUtil.channel;
        //1、定义Exchange
        channel.exchangeDeclare("fanout_1901",BuiltinExchangeType.FANOUT);
        //2、获取默认队列
        String qname=channel.queueDeclare().getQueue();
        System.out.println("消费者1："+qname);
        //3、绑定队列到交换机
        /**
         * 参数说明：
         * 1、队列名称
         * 2、交换机名称
         * 3、路由规则*/
        channel.queueBind(qname,"fanout_1901","");
        //4、创建消费者
        Consumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者："+new String(body));
            }
        };
        //5、监听消息
        channel.basicConsume(qname,true,consumer);

    }
}
