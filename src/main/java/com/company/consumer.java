package com.company;

import java.lang.*;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

import java.io.IOException;


/**
 * Created by slan on 10/19/2017.
 */
public class consumer implements Consumer, Runnable {


    public void handleConsumeOk(String s) {

    }

    public void handleCancelOk(String s) {

    }

    public void handleCancel(String s) throws IOException {

    }

    public void handleShutdownSignal(String s, ShutdownSignalException e) {
        e.printStackTrace();

    }

    public void handleRecoverOk(String s) {

    }

    public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {


        long deliveryTag = envelope.getDeliveryTag();
        byte[] body = bytes;
        MessageParser parser = new MessageParser();
        Message message = parser.parseMessage(body);
        Decider decider = new Decider(message);
        decider.decide();
        connectionrm.getConsumerChannel().basicAck(deliveryTag, false);

    }


    @Override
    public void run() {
        Consume();
    }

    public void Consume() {
        try {
            connectionrm.getConsumerChannel().basicConsume("EVENT_GET_COPART_NOTES", true, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
