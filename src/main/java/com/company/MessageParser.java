package com.company;

import com.fasterxml.jackson.databind.JsonNode;

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
            System.out.println(json);
            if (!json.isNull()) {
                copartStatus = json.get("CopartStatus").toString();
                copartLotnumber = json.get("LotNumber").toString();
                String lotnumber = formatString(copartLotnumber);
                String status = formatString(copartStatus);
                Message message = new Message(status, lotnumber);
                return message;
            }

        } catch (Exception e) {
            System.out.println("parseMessage");
            e.printStackTrace();
        }
        return null;
    }
}