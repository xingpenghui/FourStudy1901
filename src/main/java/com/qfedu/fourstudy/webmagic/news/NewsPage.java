package com.qfedu.fourstudy.webmagic.news;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2019/6/17 14:33
 */
public class NewsPage implements PageProcessor {
    private Site site=Site.me().setRetryTimes(3).setSleepTime(100).setTimeOut(3000);

    @Override
    public void process(Page page) {
        System.out.println(page.getHtml().get());

        List<String> title=page.getHtml().xpath("/html/body/div[4]/div/div[3]/ul/li/h3/a/text()").all();
        List<String> summarys=page.getHtml().xpath("div[@class='fallsFlow']/ul/li[@class='item']/h5/text()").all();
        List<String> imgs=page.getHtml().xpath("div[@class='fallsFlow']/ul/li[@class='item']/a/img/@src").all();
        System.out.println("新闻标题："+title);
        System.out.println("新闻摘要："+summarys);
        System.out.println("新闻图片："+imgs);

    }

    @Override
    public Site getSite() {
        return site;
    }
}
