package com.company;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import static com.company.DateUtils.getCurrentDateMinus60Min;
import static com.company.Publisher.publishToQueue;


import java.util.ArrayList;


/**
 * Created by slan on 11/7/2017.
 */

public class InsertToScraperQue implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws NullPointerException {
        Decider array = new Decider();
        ArrayList<String> list = new ArrayList<>();
        try {
            list = array.getList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    publishToQueue("celery", list.get(i) + "," + getCurrentDateMinus60Min());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}



