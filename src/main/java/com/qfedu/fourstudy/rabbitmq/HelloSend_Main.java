package com.qfedu.fourstudy.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *@Author feri
 *@Date Created in 2019/6/28 10:47
 */
public class HelloSend_Main {
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
       //5、创建队列-定义队列
        /**
         * 参数说明：
         *  1、队列名称
         *  2、消息是否持久化
         *  3、是否排外 2个作用：1、连接关闭是否删除队列的数据 2、消息是否私有
         *  4、是否自动删除 如果没有消费者了，标记是否自动将消息删除
         *  5、map集合 标记删除的细节 属性
         *      比如设置：消息存活时间：ttl:消息存在时间  auto-expire 队列在一定时间内没有被访问 删除
         *      max-xx 设置上限 大小、内存*/
        channel.queueDeclare("java1901",false,false,false,null);
        //6、发送消息
        /**
         * 参数说明：
         * 1、交换器名称 exchange
         * 2、匹配规则或者是队列名称
         * 3、消息的属性 路由的相关设置等
         * 4、要发送的内容*/
        channel.basicPublish("","java1901",null,"Hello,我的MQ".getBytes());

        //7、关闭连接
        channel.close();
        connection.close();
    }
}
