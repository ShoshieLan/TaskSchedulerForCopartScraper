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

/**
 * Created by slan on 10/19/2017.
 */
public class consumer implements Consumer ,Runnable
{
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

        ArrayList<String> CurrentLotNumbers = new ArrayList<>();
        byte[] body = bytes;

        String byteToString = new String(body, "UTF-8");

        JsonNode json = mapJsonToMessage(bytes);


        if (byteToString == "Null"){
            System.out.println("There was a null error in the key " + byteToString);
        }
        else {
            String status = json.get("CopartStatus").asText();
            if (status == "Status-TRANSTART"){
            String lotnumber = json.get("LotNumber").asText();
            if (lotnumber == "NULL"){
                System.out.println("lotnumbe was null " + byteToString);
                //executeJob.func();
            }
            else {
                //start task
                CurrentLotNumbers.add(lotnumber);
                System.out.println(lotnumber + " " + status +" " + "start task");
            }
            }
            else if (status == "Status-TRANSTARTMAN"){
                String lotnumber = json.get("LotNumber").asText();
                if (lotnumber == "NULL"){
                    System.out.println("lotnumbe was null " + byteToString);
                }
                else {
                    CurrentLotNumbers.add(lotnumber);
                    System.out.println(lotnumber + " " + status +" " + "start task");

                }
            }
            else if (status == "Status-SETTLEMENTCMP"){
                String lotnumber = json.get("LotNumber").asText();
                if (lotnumber == "NULL"){
                    System.out.println("lotnumbe was null " + byteToString);
                }
                else {
                    CurrentLotNumbers.remove(lotnumber);
                    System.out.println(lotnumber + " " + status +" " + "stop task");
                }
            }
            else {
                System.out.println(status);
            }

        }









    }

    @Override
    public void run() {
        try {
            connectionrm.getChannel().basicConsume("EVENT_GET_COPART_NOTES", true, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
