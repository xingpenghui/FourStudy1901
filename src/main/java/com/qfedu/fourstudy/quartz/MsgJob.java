package com.qfedu.fourstudy.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *@Author feri
 *@Date Created in 2019/6/18 14:06
 */
public class MsgJob  implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap=jobExecutionContext.getJobDetail().getJobDataMap();
        System.out.println(dataMap.get("msg")+"---->"+dataMap.get("phone"));

    }
}
