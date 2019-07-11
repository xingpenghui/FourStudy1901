package com.qfedu.fourstudy.rabbitmq;

import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *@Author feri
 *@Date Created in 2019/6/28 11:06
 */
public class HelloConsumer_Main {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1、创建连接工厂
        ConnectionFactory factory=new ConnectionFactory();
        //2、设置连接信息 服务器地址和端口 用户名和密码
        factory.setHost("39.105.189.141");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        //3、获取连接对象
        Connection connection=factory.newConnection();
        //4、创建通道
        Channel channel=connection.createChannel();
        //5、声明队列
        channel.queueDeclare("java1901",false,false,false,null);
        //6、创建消息消费者
        Consumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者："+new String(body));
            }
        };
        //7、监听消息
        /**
         * 参数说明：
         * 1、队列名称 监听哪个队列
         * 2、是否自动应答 消费者获取消息之后 自动向服务器发送确认 服务器接收到确认 会将消息删除*/
        channel.basicConsume("java1901",true,consumer);

        //8、销毁
        channel.close();
        connection.close();
    }
}
