package com.company;

import java.sql.*;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import static com.company.SqlUtilityConnection.*;
import static com.company.DateUtils.getCurrentDateMinus30Min;
import static com.company.Publisher.publishToQueue;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by slan on 11/7/2017.
 */

public class InsertToScraperQue implements Job {


    //private static String date = DateUtils.getCurrDateTimeStr();
    //private consumer list = new consumer();
    ArrayList<String> list = consumer.getCurrentLotNumbers();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {

        sqlGetResult("Truncate table ArrayListBackupForCopartNotes");
        System.out.println("thirty min ago " + getCurrentDateMinus30Min());
        if (list != null) {
            System.out.println(list);
            Iterator i = list.iterator();
            while (i.hasNext()) {
                Object element = i.next();
                //for (Iterator<String> i = list.iterator(); i.hasNext(); ) {
                publishToQueue("celery", element + "," + getCurrentDateMinus30Min());
                System.out.println(element + " " + getCurrentDateMinus30Min());
                System.out.println("you made it here");
            }
        }
    }
}
