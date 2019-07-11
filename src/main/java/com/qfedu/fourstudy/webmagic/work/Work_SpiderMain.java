package com.qfedu.fourstudy.webmagic.work;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

/**
 *@Author feri
 *@Date Created in 2019/6/17 10:21
 */
public class Work_SpiderMain {
    public static void main(String[] args) {
        //http://auto.huanqiu.com/globalnews/?agt=15438
        //https://zz.58.com/jianzhi/1/?combination_id=1&utm_source=market&spm=u-2d2yxv86y3v43nkddh1.BDPCPZ_BT&PGTID=0d100000-0015-626f-019e-21e08fbb4148&ClickID=2  郑州兼职
//https://zz.58.com/job/?key=java%E5%BC%80%E5%8F%91&classpolicy=main_null,job_A&final=1&jump=1
        //前程无忧 https://search.51job.com/list/170200,000000,0000,00,9,99,Java,2,1.html?lang=c&stype=&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=

        new Spider(new WorkPage()).addUrl("https://search.51job.com/list/170200,000000,0000,00,9,99,Java,2,1.html?lang=c&stype=&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=").addPipeline(new ConsolePipeline()).thread(1).run();
    }
}