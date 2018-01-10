package com.company;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import static com.company.DateUtils.getCurrentDateMinus60Min;
import static com.company.Publisher.publishToQueue;
import static com.company.SqlUtilityConnection.getConn;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 * Created by slan on 11/7/2017.
 */

public class InsertToScraperQue implements Job {


    ArrayList<String> list = getCurrentLotNumbers();

    public ArrayList<String> getCurrentLotNumbers() {
        ArrayList<String> list = new ArrayList<String >();
        Connection conn = null;
        Statement st = null;
        try {
            String query = "Select distinct * from ArrayListBackupForCopartNotes";
            conn = getConn();
            st = conn.createStatement();
            ResultSet s = st.executeQuery(query);

            try {
                while (s.next()) {
                    if (!list.contains(s.getString(1))) {
                        list.add(s.getString(1));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws NullPointerException {


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



