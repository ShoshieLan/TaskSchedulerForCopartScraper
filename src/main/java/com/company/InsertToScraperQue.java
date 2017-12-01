package com.company;

import java.sql.*;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

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
        try {
        String db_connect_string = "jdbc:jtds:sqlserver://oorah-admire03:1433/AdmireTempData/";
        //String insert = "INSERT INTO dbo.ArrayListBackupForCopartNotes (Lotnumber) Values(?)";

        Connection conn = null;
        try {

            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conn = DriverManager.getConnection(db_connect_string, "yunion", "421kirby#");

        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        // Connection con = DriverManager.getConnection(host, username, password);
            Statement statement = conn.createStatement();
            if (list == null) {
                //todo grab from table insert to arraylist
                ResultSet rs = statement.executeQuery("Select * from ArrayListBackupForCopartNotes");

                while (rs.next()){

                    list.add(String.valueOf(rs));
                    System.out.println(list);
                }
                statement.executeUpdate("Truncate table ArrayListBackupForCopartNotes");
                System.out.println("truncated the table");
            }
            Iterator i = list.iterator();
            while(i.hasNext()){
                Object element = i.next();
                statement.executeUpdate("INSERT INTO ArrayListBackupForCopartNotes  Values('" + element +"')");
            }

            //for (Iterator<String> i = list.iterator(); i.hasNext(); ) {
             //   System.out.println(i.next().toString());
              //  statement.executeUpdate("INSERT INTO ArrayListBackupForCopartNotes  Values('" + i.next().toString() +"')");

            //}

            conn.close();
        }
        catch(SQLException err){
            System.out.println(err.getMessage());
        }


        System.out.println("thirty min ago " + getCurrentDateMinus30Min());
        if (list != null) {
            System.out.println(list);

            Iterator i = list.iterator();
            while(i.hasNext()){
                Object element = i.next();
            //for (Iterator<String> i = list.iterator(); i.hasNext(); ) {
                publishToQueue("celery", element  + "," + getCurrentDateMinus30Min());
                System.out.println(element + " "  + getCurrentDateMinus30Min());
                System.out.println("you made it here");
            }
        }
    }
}
