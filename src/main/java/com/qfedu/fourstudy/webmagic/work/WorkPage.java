package com.qfedu.fourstudy.webmagic.work;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 *@Author feri
 *@Date Created in 2019/6/17 14:43
 */
public class WorkPage implements PageProcessor {
    private Site site=Site.me().setRetryTimes(3).setSleepTime(100).setTimeOut(3000);

    @Override
    public void process(Page page) {

        //List<String> works=page.getHtml().xpath("/html/body/div[3]/div[1]/div[5]/div[1]/div[@class='items']/div[@class='item']/div[1]/h2/a/text()").all();
//        List<String> works=page.getHtml().xpath("ul[@class='list_con']/li[@class='job_item']/div[@class='item_con']/div[@class='job_name']/a/span[@class='name']").all();
        List<String> names=page.getHtml().xpath("//*[@id=\"resultList\"]/div[@class='el']/p/span/a/text()").all();
        List<String> companys=page.getHtml().xpath("//*[@id=\"resultList\"]/div[@class='el']/span[@class='t2']/a/text()").all();
        List<String> address=page.getHtml().xpath("//*[@id=\"resultList\"]/div[@class='el']/span[2]/text()").all();
        List<String> moneys=page.getHtml().xpath("//*[@id=\"resultList\"]/div[@class='el']/span[3]/text()").all();
        System.out.println(names);
        System.out.println(companys);
        System.out.println(address);
        System.out.println(moneys);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
