package com.company;

/**
 * Created by slan on 1/8/2018.
 */
public class Message {


    public Message(String status, String lotnumber){
        this.lotnumber = lotnumber;
        this.status = status;

    }


    private String status;
    private String lotnumber;


    public String getStatus() {return status;}
    public String getLotnumber() {return lotnumber;}

}
