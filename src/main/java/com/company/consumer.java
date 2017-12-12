package com.company;

import java.lang.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import static com.company.Utilities.mapJsonToMessage;
import static com.company.Publisher.publishToQueue;
import static com.company.SqlUtilityConnection.*;

/**
 * Created by slan on 10/19/2017.
 */
public class consumer implements Consumer, Runnable {

   public  ArrayList<String> list = new ArrayList<>();


    public  ArrayList<String> getCurrentLotNumbers() {
        Connection conn = null;
        Statement st = null;
        try {
            String query = "Select * from ArrayListBackupForCopartNotes";
            conn = getConn();
            st = conn.createStatement();
            ResultSet s = st.executeQuery(query);

            try {
                while (s.next()) {
                    if (!list.contains(s.getString(1))) {
                        list.add(s.getString(1));
                        System.out.println(list);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        //sqlQueryUpdate("Truncate table ArrayListBackupForCopartNotes");
        return list;
    }
   //InsertToScraperQue array = new InsertToScraperQue();


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
        System.out.println(body);
        //String byteToString = new String(body, "UTF-8");

        JsonNode json = mapJsonToMessage(body);
        System.out.println(json.toString());



        if (json.isNull()) {
            System.out.println("There was a null error in the key " + json.toString());
        } else {
            String status = json.get("CopartStatus").toString();
            //System.out.println(status.getClass().getName());
            System.out.println(status);
            if (status.equals("\"Status-TRANSTART\"")) {
                String lotnumber = json.get("LotNumber").toString();
                if (lotnumber.equals("\"Null\"")) {
                    System.out.println("lotnumber was null " + json.toString());
                } else {
                    if(!list.contains(lotnumber)){
                        list.add(lotnumber);
                        sqlQueryUpdate("INSERT INTO ArrayListBackupForCopartNotes  Values('"+ lotnumber +"')");
                        System.out.println(lotnumber + " " + status + " " + "start task");
                    }
                }
            } else if (status.equals("\"Status-TRANSTARTMAN\"")) {
                String lotnumber = json.get("LotNumber").toString();
                if (lotnumber.equals("\"NULL\"")) {
                    System.out.println("lotnumber was null " + json.toString());
                } else {
                    if(!list.contains(lotnumber)){
                        list.add(lotnumber);
                        sqlQueryUpdate("INSERT INTO ArrayListBackupForCopartNotes  Values('"+ lotnumber +"')");
                        System.out.println(lotnumber + " " + status + " " + "start task");
                    }
                }
            } else if (status.equals("\"Status-SETTLEMENTCMP\"")) {
                String lotnumber = json.get("LotNumber").toString();
                if (lotnumber.equals("\"NULL\"")) {
                    System.out.println("lotnumber was null " + json.toString());
                } else {

                    Iterator iterator = list.iterator();
                    while(iterator.hasNext()){
                        Object arraylot=iterator.next();
                        if(arraylot.equals(lotnumber)){
                            iterator.remove();
                            sqlQueryUpdate("Delete from ArrayListBackupForCopartNotes where lotnumber = " + lotnumber);
                            System.out.println(lotnumber + " " + status + " " + "stop task");
                        }
                    }
                }
            } else {
                System.out.println(status + " after if else");
            }

        }

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
