package com.company;


import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;


public class Main {

    public static void main(String[] args) {

        consumer consumer = new consumer();
        ExecutorService executor = Executors.newFixedThreadPool(20);
        executor.execute(consumer);

        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobDetail job = newJob(InsertToScraperQue.class)
                    .withIdentity("lotnumber", "group1")
                    .build();
            Trigger trigger = newTrigger().forJob(job)
                    .withIdentity("every hour", "group1")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInMinutes(60).repeatForever()).build();


            scheduler.scheduleJob(job, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
