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



    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        consumer array = new consumer();
        ArrayList<String> list = array.getCurrentLotNumbers();
                    for(int i = 0; i < list.size(); i++){
                    publishToQueue("celery", list.get(i) + "," + getCurrentDateMinus30Min());
                }
            }
        }



