package com.company;

import org.quartz.Job;
import org.quartz.JobExecutionContext;


import static com.company.DateUtils.getCurrentDateTime;
import static com.company.LotAndTime.getCurrentLotNumbers;
import static com.company.Publisher.publishToQueue;
import static com.company.SqlUtilityConnection.sqlQueryUpdate;

import java.util.ArrayList;


/**
 * Created by slan on 11/7/2017.
 */

public class InsertToScraperQue implements Job {

    private ArrayList<LotAndTime> list = getCurrentLotNumbers();


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws NullPointerException {


        try {

            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    publishToQueue("WORK_GET_COPART_NOTES", list.get(i).getLotnumber() + "," + list.get(i).getLastCheck());
                    String query = "Update ArrayListBackUpForCopartNotes set LastCheck = " + getCurrentDateTime() + "where Lotnumber = " + list.get(i).getLotnumber();
                    sqlQueryUpdate(query);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}



