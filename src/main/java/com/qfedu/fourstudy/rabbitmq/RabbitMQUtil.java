package com.qfedu.fourstudy.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *@Author feri
 *@Date Created in 2019/6/28 14:13
 */
public class RabbitMQUtil {

    public static Channel channel;
    static {
        //1、创建连接工厂
        ConnectionFactory factory=new ConnectionFactory();
        //2、设置连接信息 服务器地址和端口 用户名和密码
        factory.setHost("39.105.189.141");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        //3、获取连接对象
        Connection connection= null;
        try {
            connection = factory.newConnection();
            //4、创建通道
            channel=connection.createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }

}
