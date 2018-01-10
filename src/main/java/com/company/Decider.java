package com.company;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import static com.company.SqlUtilityConnection.getConn;
import static com.company.SqlUtilityConnection.sqlQueryUpdate;

/**
 * Created by slan on 1/8/2018.
 */
public class Decider {

    public Decider() {

    }

    public Decider(Message message) {
        this.message = message;

    }

    private Message message;

    private ArrayList<String> list;

    public ArrayList<String> getList() {
        return this.list;
    }


    public String isCurrentLotNumber() {
        Connection conn = null;
        Statement st = null;
        try {
            String query = "Select top 1 * from ArrayListBackupForCopartNotes where Lotnumber = " + message.getLotnumber();
            conn = getConn();
            st = conn.createStatement();
            ResultSet s = st.executeQuery(query);
            while (s.next()) {
                String lotnumber = s.getString("LotNumber");
                return lotnumber;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }



    public void decide() {
        System.out.println(message.getStatus());
        if ((message.getStatus().equals("Status-TRANSTART") || message.getStatus().equals("Status-TRANSTARTMAN")) && isCurrentLotNumber() == null) {
            //list.add(message.getLotnumber());
            String query = "INSERT INTO ArrayListBackupForCopartNotes Values('" + message.getLotnumber() + "')";
            //System.out.println(query);
            sqlQueryUpdate(query);
            System.out.println("insert");
        } else if (message.getStatus().equals("Status-SETTLEMENTCMP")) {
            //Iterator iterator = list.iterator();
            //while (iterator.hasNext()) {
            //  Object arraylot = iterator.next();
            //  if (arraylot.equals(message.getLotnumber())) {
            //    iterator.remove();
            sqlQueryUpdate("Delete from ArrayListBackupForCopartNotes where lotnumber = " + message.getLotnumber());
            System.out.println("delete");
        }
    }
}
