package com.company;


import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;



public class Main {

    public static void main(String[] args) {

        consumer consumer = new consumer();
        Thread thread = new Thread(consumer);
        thread.start();

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
