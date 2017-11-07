
package com.company;



import org.quartz.Job;
import org.quartz.JobExecutionContext;
import static com.company.Publisher.publishToQueue;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by slan on 11/7/2017.
 */

public class InsertToScraperQue implements Job {

    //private consumer list = new consumer();
    ArrayList<String> list = consumer.getCurrentLotNumbers();



    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
    // publish the lotnumbers in the array to que


        if (list != null){
            System.out.println(list);
            publishToQueue("celery", "lotnumber");
            System.out.println("you made it here");
        }



}

}
