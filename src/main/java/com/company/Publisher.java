package com.company;


import java.io.IOException;

/**
 * Created by slan on 11/7/2017.
 */
public class Publisher {

    public static void publishToQueue(String routingkey, String job) {
        try {

            connectionrm.getPublisherChannel().basicPublish("amq.direct", routingkey, null, job.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
