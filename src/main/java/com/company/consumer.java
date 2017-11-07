package com.company;

import java.lang.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

import java.io.IOException;
import java.util.ArrayList;

import static com.company.Utilities.mapJsonToMessage;
import static com.company.Publisher.publishToQueue;

/**
 * Created by slan on 10/19/2017.
 */
public class consumer implements Consumer, Runnable {
    public static ArrayList<String>  CurrentLotNumbers = new ArrayList<>();

    public void handleConsumeOk(String s) {

    }

    public void handleCancelOk(String s) {

    }

    public void handleCancel(String s) throws IOException {

    }

    public void handleShutdownSignal(String s, ShutdownSignalException e) {

    }

    public void handleRecoverOk(String s) {

    }

    public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {


        byte[] body = bytes;

        //String byteToString = new String(body, "UTF-8");

        JsonNode json = mapJsonToMessage(body);
        //System.out.println(json);

        if (json.toString().equals("Null")) {
            System.out.println("There was a null error in the key " + json.toString());
        } else {
            String status = json.get("CopartStatus").asText();
            //System.out.println(status.getClass().getName());
            System.out.println(status);
            if (status.equals("Status-TRANSTART")) {
                String lotnumber = json.get("LotNumber").toString();
                if (lotnumber.equals("Null")) {
                    System.out.println("lotnumber was null " + json.toString());
                } else {
                    CurrentLotNumbers.add(lotnumber);
                    System.out.println(lotnumber + " " + status + " " + "start task");
                }
            } else if (status.equals("Status-TRANSTARTMAN")) {
                String lotnumber = json.get("LotNumber").toString();
                if (lotnumber.equals("NULL")) {
                    System.out.println("lotnumber was null " + json.toString());
                } else {
                    CurrentLotNumbers.add(lotnumber);
                    System.out.println(lotnumber + " " + status + " " + "start task");
                }
            } else if (status.equals("Status-SETTLEMENTCMP")) {
                String lotnumber = json.get("LotNumber").toString();
                if (lotnumber.equals("NULL")) {
                    System.out.println("lotnumber was null " + json.toString());
                } else {
                    CurrentLotNumbers.remove(lotnumber);
                    System.out.println(lotnumber + " " + status + " " + "stop task");
                }
            } else {
                System.out.println(status + " after if else");
            }

        }

    }

    public static ArrayList<String> getCurrentLotNumbers() {
        return CurrentLotNumbers;
    }

    @Override
    public void run() {
        Consume();
    }

    public void Consume() {
        try {
            connectionrm.getChannel().basicConsume("EVENT_GET_COPART_NOTES", true, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
