package com.company;


import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import com.company.consumer.*;


public class Main {

    public static void main(String[] args) {

        consumer consumer = new consumer();
        consumer.run();
        try {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        JobDetail job = newJob(InsertToScraperQue.class)
                .withIdentity("lotnumber", "group1")
                .build();
        Trigger trigger = newTrigger().forJob(job)
                .withIdentity("every 30 min","group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInMinutes(30).repeatForever()).build();


        scheduler.scheduleJob(job,trigger);
        scheduler.start();
    } catch (SchedulerException e) {
        e.printStackTrace();
    }
    }
}
