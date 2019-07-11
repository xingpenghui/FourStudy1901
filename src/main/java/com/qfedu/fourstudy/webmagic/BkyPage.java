package com.qfedu.fourstudy.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 *@Author feri
 *@Date Created in 2019/6/17 10:19
 * 爬取博客园的所有的文章信息
 * 所有分页的数据  分析分页接口
 * pager_top
 * https://www.cnblogs.com/sitehome/p/7
 *
 */
public class BkyPage implements PageProcessor {
    private Site site=Site.me().setRetryTimes(3).setSleepTime(100).setTimeOut(3000);

    //页面处理 如何处理页面 获取想要数据
    @Override
    public void process(Page page) {
        //打印获取的的内容
        List<String> titles=page.getHtml().xpath("div[@class='post_item_body']/h3/a[@class='titlelnk']/text()").all();
        List<String> ids=page.getHtml().xpath("div[@class='post_item_body']/h3/a[@class='titlelnk']/@href").all();
       // List<String> images=page.getHtml().xpath("div[@class='post_item_body']/p[@class='post_item_summary']/a/img/@src").all();
        List<String> summarys=page.getHtml().xpath("div[@class='post_item_body']/p[@class='post_item_summary']/text()").all();
        List<String> authors=page.getHtml().xpath("div[@class='post_item_body']/div[@class='post_item_foot']/a[@class='lightblue']/text()").all();
        List<String> times=page.getHtml().xpath("div[@class='post_item_body']/div[@class='post_item_foot']/text()").all();
        //
        List<BkyEnity> list=new ArrayList<>();
        for(int i=0;i<titles.size();i++){
            String id=ids.get(i);
            id=id.substring(id.lastIndexOf("/")+1,id.lastIndexOf("."));
            int aid=0;
            if(id.matches("[0-9]{2,}")){
                aid=Integer.parseInt(id);
            }
            System.out.println(times.get(i));
            String time=times.get(i);
            time=time.substring(time.indexOf("于")+2).trim();
            list.add(new BkyEnity(aid,titles.get(i),summarys.get(i),ids.get(i),authors.get(i),time));
        }
        System.out.println("抓取到的数据："+list.size());
        if(page.getUrl().get().equals("https://www.cnblogs.com/")){
            //说明当前是第一页
            //获取总页数
            List<String> ps=page.getHtml().xpath("div[@class='pager']/a/text()").all();
            int lastPage=Integer.parseInt(ps.get(ps.size()-2));
            List<String> targetUrl=new ArrayList<>();
            //拼接所有分页的链接
            for(int i=2;i<=lastPage;i++){
                targetUrl.add("https://www.cnblogs.com/sitehome/p/"+i);
            }
            //设置要爬取的链接
            page.addTargetRequests(targetUrl);

        }
        //传递
        page.putField("data",list);
    }

    //返回站点信息  可以设置请求消息头、超时时间等
    @Override
    public Site getSite() {
        return site;
    }
}
