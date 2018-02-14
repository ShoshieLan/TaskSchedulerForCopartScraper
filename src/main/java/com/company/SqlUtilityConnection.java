package com.company;

import java.sql.*;

/**
 * Created by slan on 12/4/2017.
 */
public class SqlUtilityConnection {


    public static Connection getConn() {


        String db_connect_string = "jdbc:jtds:sqlserver://192.168.1.21:1433/AdmireTempData/";
        Connection conn = null;
        try {

            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conn = DriverManager.getConnection(db_connect_string, "yunion", "421kirby#");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void sqlQueryUpdate(String query) {
        Connection conn = null;
        Statement st = null;

        try {
            conn = getConn();
            st = conn.createStatement();
            st.executeUpdate(query);

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


}







