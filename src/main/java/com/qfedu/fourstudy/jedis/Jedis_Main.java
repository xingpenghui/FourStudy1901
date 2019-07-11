package com.qfedu.fourstudy.jedis;

import redis.clients.jedis.Jedis;

/**
 *@Author feri
 *@Date Created in 2019/6/6 17:16
 */
public class Jedis_Main {
    public static void main(String[] args) {
        //1、创建Jedis对象
        Jedis jedis=new Jedis("39.105.189.141",6380);
        //2、认证密码
        jedis.auth("qfjava");
        //3、操作
        jedis.set("str1","abe");
        System.out.println(jedis.get("str1"));
        jedis.lpush("list1","111");
        System.out.println(jedis.lpop("list1"));
        //4、关闭
        jedis.close();
    }
}
