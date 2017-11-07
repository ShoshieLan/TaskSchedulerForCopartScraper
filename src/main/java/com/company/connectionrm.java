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


        private static Connection connection = null;
        private static Channel channel = null;

        static {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUsername("yunion");
            factory.setPassword("421kirby#");
            factory.setHost("192.168.1.41");
            factory.setPort(5672);

            try {
                connection = factory.newConnection();
                channel = connection.createChannel();
            }
            catch (IOException | TimeoutException e) {
                e.printStackTrace();

            }

        }

        public static Channel getChannel() {

            return  channel;
        }
    }


