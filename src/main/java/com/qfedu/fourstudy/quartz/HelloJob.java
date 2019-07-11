package com.qfedu.fourstudy.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *@Author feri
 *@Date Created in 2019/6/18 09:42
 */
public class HelloJob implements Job {
    //执行 重复执行的事情
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        System.out.println("醒醒，上课怎么能睡觉呢？："+System.currentTimeMillis()/1000);
    }
}
