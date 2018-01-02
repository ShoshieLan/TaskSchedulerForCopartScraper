package com.company;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by slan on 10/19/2017.
 */
public class connectionrm {


    private static Connection publisherConnection = null;
    private static Channel publisherChannel = null;
    private static Connection consumerConnection = null;
    private static Channel consumerChannel = null;

    static {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("yunion");
        factory.setPassword("421kirby#");
        factory.setHost("192.168.1.41");
        factory.setPort(5672);

        try {
            consumerConnection = factory.newConnection();
            consumerChannel = consumerConnection.createChannel();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();

        }

    }


    static {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("yunion");
        factory.setPassword("421kirby#");
        factory.setHost("192.168.1.41");
        factory.setPort(5672);

        try {
            publisherConnection = factory.newConnection();
            publisherChannel = publisherConnection.createChannel();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();

        }

    }
    public static Channel getPublisherChannel() {

        return publisherChannel;
    }

    public static Channel getConsumerChannel() {

        return consumerChannel;
    }
}


