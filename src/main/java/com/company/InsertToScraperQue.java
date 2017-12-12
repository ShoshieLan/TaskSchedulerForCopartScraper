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

    private consumer array = new consumer();

    //private static String date = DateUtils.getCurrDateTimeStr();
    //


    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        ArrayList<String> list = array.getCurrentLotNumbers();
        System.out.println("thirty min ago " + getCurrentDateMinus30Min());
                System.out.println(list);


                    for(int i = 0; i < list.size(); i++){
                    publishToQueue("celery", list.get(i) + "," + getCurrentDateMinus30Min());
                    System.out.println(list.get(i) + " " + getCurrentDateMinus30Min());
                    //System.out.println("you made it here");
                }
            }
        }



