package com.qfedu.fourstudy.webmagic.jd;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 *@Author feri
 *@Date Created in 2019/6/17 11:53
 */
public class JdPage implements PageProcessor {
    private Site site=Site.me().setRetryTimes(3).setSleepTime(100).setTimeOut(3000);

    @Override
    public void process(Page page) {
        List<String> imgs=page.getHtml().xpath("li[@class='gl-item']/div/div[@class='p-img']/a/img/@src").all();
        List<String> prices=page.getHtml().xpath("//*[@id=\"plist\"]/ul/li/div/div[@class='p-price']/strong[2]/i").all();
        List<String> names=page.getHtml().xpath("li[@class='gl-item']/div/div[@class='p-name']/a/em/text()").all();
        List<String> cxs=page.getHtml().xpath("//*[@id=\"plist\"]/ul/li/div/div[3]/a").all();
        List<String> commits=page.getHtml().xpath("//*[@id=\"plist\"]/ul/li/div/div[4]/strong/a").all();
        //ArrayList
        System.out.println("商品图片："+imgs);
        System.out.println("商品价格："+prices);
        System.out.println("商品名称："+names);
        System.out.println("促销信息："+cxs);
        System.out.println("评价数量："+commits);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
