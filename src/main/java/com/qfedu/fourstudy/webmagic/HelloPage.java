package com.qfedu.fourstudy.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 *@Author feri
 *@Date Created in 2019/6/17 10:19
 */
public class HelloPage implements PageProcessor {
    private Site site=Site.me().setRetryTimes(3).setSleepTime(100).setTimeOut(3000);

    //页面处理 如何处理页面 获取想要数据
    @Override
    public void process(Page page) {
        //打印获取的的内容
       // System.out.println(page.getHtml().get());
        List<String> titles=page.getHtml().xpath("div[@class='post_item_body']/h3/a[@class='titlelnk']/text()").all();
        List<String> ids=page.getHtml().xpath("div[@class='post_item_body']/h3/a[@class='titlelnk']/@href").all();
        List<String> images=page.getHtml().xpath("div[@class='post_item_body']/p[@class='post_item_summary']/a/img/@src").all();
        List<String> summarys=page.getHtml().xpath("div[@class='post_item_body']/p[@class='post_item_summary']/text()").all();
        List<String> authors=page.getHtml().xpath("div[@class='post_item_body']/div[@class='post_item_foot']/a[@class='lightblue']/text()").all();
        List<String> times=page.getHtml().xpath("div[@class='post_item_body']/div[@class='post_item_foot']/text()").all();
        System.out.println("标题："+titles);
        System.out.println("图片："+images);
        System.out.println("摘要："+summarys);
        System.out.println("作者："+authors);
        System.out.println("发布时间："+times);

    }

    //返回站点信息  可以设置请求消息头、超时时间等
    @Override
    public Site getSite() {
        return site;
    }
}
