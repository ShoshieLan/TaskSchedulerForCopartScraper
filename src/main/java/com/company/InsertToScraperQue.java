
package com.company;

import org.quartz.Job;
import org.quartz.JobExecutionContext;


/**
 * Created by slan on 11/7/2017.
 */

public class InsertToScraperQue implements Job {


    public void execute(JobExecutionContext context) {
    // publish the lotnumbers in the array to que
    System.out.println("you made it here");
}
}
