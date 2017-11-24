package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import static com.company.Publisher.publishToQueue;

import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by slan on 11/7/2017.
 */

public class InsertToScraperQue implements Job {



        //private consumer list = new consumer();
        ArrayList<String> list = consumer.getCurrentLotNumbers();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
        String db_connect_string = "jdbc:jtds:sqlserver://oorah-admire03:1433/AdmireTempData/";

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

            statement.executeUpdate("Truncate table ArrayListBackupForCopartNotes");

            for (Iterator<String> i = list.iterator(); i.hasNext(); ) {
                statement.executeUpdate(
                        "INSERT INTO dbo.ArrayListBackupForCopartNotes(Lotnumber) SELECT" + ' ' + i.next());
            }
            conn.close();
        }
        catch(SQLException err){
            System.out.println(err.getMessage());
        }

        // publish the lotnumbers in the array to que
//truncate db table add lot numbers
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        if (list != null) {
            System.out.println(list);
            for (Iterator<String> i = list.iterator(); i.hasNext(); ) {
                publishToQueue("celery", i.next() + ',' +timeStamp);
                System.out.println(i.next() + ' ' + timeStamp);
                System.out.println("you made it here");
            }

        }


    }

}
