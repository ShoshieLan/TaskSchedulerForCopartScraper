package com.company;

import com.fasterxml.jackson.databind.JsonNode;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;


import static com.company.SqlUtilityConnection.getConn;

import static com.company.SqlUtilityConnection.sqlQueryUpdate;
import static com.company.Utilities.formatString;
import static com.company.Utilities.mapJsonToMessage;

/**
 * Created by slan on 1/8/2018.
 */
public class MessageParser {


    public Message parseMessage(byte[] rabbitMessage) {

        String copartStatus = null;
        String copartLotnumber = null;
        try {
            JsonNode json = mapJsonToMessage(rabbitMessage);
            copartStatus = json.get("CopartStatus").toString();
            copartLotnumber = json.get("LotNumber").toString();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            String lotnumber = formatString(copartLotnumber);
            String status = formatString(copartStatus);
            Message message = new Message(lotnumber, status);
            return message;
        }
    }

}
