package com.qfedu.fourstudy.lambda;

/**
 *@Author feri
 *@Date Created in 2019/7/11 11:43
 */
public class Lambda_Main {
    public static void main(String[] args) {
        //传统写法
        //创建线程对象
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("我是线程，我嚣张");
            }
        });
        //启动
        thread.start();

        //基于Lambda表达式
        Thread lambdaThread=new Thread(()->{
            System.out.println(Thread.currentThread().getName()+",我是子线程，不敢嚣张");
        });
        lambdaThread.start();
    }
}
