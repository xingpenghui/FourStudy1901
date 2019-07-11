package com.qfedu.fourstudy.webmagic;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

/**
 *@Author feri
 *@Date Created in 2019/6/17 10:21
 */
public class Hello_Spider_Main {
    public static void main(String[] args) {
        new Spider(new HelloPage()).addUrl("https://www.cnblogs.com/").addPipeline(new ConsolePipeline()).thread(5).start();
    }
}