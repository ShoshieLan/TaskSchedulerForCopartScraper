package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import static com.company.SqlUtilityConnection.getConn;

/**
 * Created by slan on 1/26/2018.
 */
public class LotAndTime {

    private String lotnumber;
    private Date lastCheck;



    public String getLotnumber(){ return  lotnumber;}
    public Date getLastCheck(){return  lastCheck;}

    public static ArrayList<LotAndTime> getCurrentLotNumbers() {
        ArrayList<LotAndTime> list = new ArrayList<>();
        Connection conn = null;
        Statement st = null;
        try {
            String query = "Select distinct * from ArrayListBackupForCopartNotes";
            conn = getConn();
            st = conn.createStatement();
            ResultSet s = st.executeQuery(query);

            try {
                while (s.next()) {
                    LotAndTime current = new LotAndTime();
                    current.lotnumber = s.getString(1);
                    current.lastCheck = s.getDate(2);
                    list.add(current);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
