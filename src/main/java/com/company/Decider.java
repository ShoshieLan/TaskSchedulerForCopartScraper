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
        this.list = getCurrentLotNumbers();
    }

    private Message message;

    private ArrayList<String> list; 

    public ArrayList<String> getList() {
        return this.list;
    }

    public ArrayList<String> getCurrentLotNumbers() {
        Connection conn = null;
        Statement st = null;
        try {
            String query = "Select distinct * from ArrayListBackupForCopartNotes";
            conn = getConn();
            st = conn.createStatement();
            ResultSet s = st.executeQuery(query);

            try {
                while (s.next()) {
                    if (!list.contains(s.getString(1))) {
                        list.add(s.getString(1));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void decide() {
        if ((message.getStatus().equals("Status-TRANSTART") || message.getStatus().equals("Status-TRANSTARTMAN")) && !list.contains(message.getLotnumber())) {
            list.add(message.getLotnumber());
            sqlQueryUpdate("INSERT INTO ArrayListBackupForCopartNotes Values('" + message.getLotnumber() + "')");
        } else if (message.getStatus().equals("Status-SETTLEMENTCMP")) {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Object arraylot = iterator.next();
                if (arraylot.equals(message.getLotnumber())) {
                    iterator.remove();
                    sqlQueryUpdate("Delete from ArrayListBackupForCopartNotes where lotnumber = " + message.getLotnumber());
                }
            }
        }
    }
}
