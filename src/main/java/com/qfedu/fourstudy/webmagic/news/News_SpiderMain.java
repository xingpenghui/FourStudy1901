package com.qfedu.fourstudy.webmagic.news;

import com.qfedu.fourstudy.webmagic.BkyPage;
import com.qfedu.fourstudy.webmagic.BkyPipeline;
import com.qfedu.fourstudy.webmagic.work.WorkPage;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

/**
 *@Author feri
 *@Date Created in 2019/6/17 10:21
 */
public class News_SpiderMain {
    public static void main(String[] args) {
        //http://auto.huanqiu.com/globalnews/?agt=15438
        new Spider(new WorkPage()).addUrl("http://auto.huanqiu.com/globalnews/?agt=15438").addPipeline(new ConsolePipeline()).thread(1).run();
    }
}