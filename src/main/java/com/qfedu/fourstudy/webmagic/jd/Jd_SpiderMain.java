package com.qfedu.fourstudy.webmagic.jd;

import com.qfedu.fourstudy.webmagic.BkyPage;
import com.qfedu.fourstudy.webmagic.BkyPipeline;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

/**
 *@Author feri
 *@Date Created in 2019/6/17 11:53
 */
public class Jd_SpiderMain {
    public static void main(String[] args) {
        //
        String url="https://list.jd.com/list.html?cat=670,671,672";

        new Spider(new JdPage()).addUrl(url).addPipeline(new ConsolePipeline()).thread(1).start();

    }
}
