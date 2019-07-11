package com.qfedu.fourstudy.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 *@Author feri
 *@Date Created in 2019/6/18 10:17
 */
public class Quartz_Main2 {
    public static void main(String[] args) throws SchedulerException {
        //1、创建调度器
        Scheduler scheduler=new StdSchedulerFactory().getScheduler();
        //2、创建作业详情
        JobDetail jobDetail=JobBuilder.newJob(HelloJob.class).build();
        //3、创建触发器 --标记什么时候做
        //间隔2秒执行1次 执行到永远 立刻执行
        Trigger trigger=TriggerBuilder.newTrigger().withSchedule(
                CronScheduleBuilder.cronSchedule("0/3 * 10 ? 6 3")).build();
        //4、设置到调度器中
        scheduler.scheduleJob(jobDetail,trigger);
        //5、启动调度器
        scheduler.start();
    }
}
