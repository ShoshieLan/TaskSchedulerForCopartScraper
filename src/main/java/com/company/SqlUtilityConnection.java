package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Created by slan on 12/4/2017.
 */
public class SqlUtilityConnection {



       public static void sqlQueryExecuteNonQuery(String query) {

        Connection conn = null;
        Statement st = null;
        try {
            conn = getConn();
            st = conn.createStatement();
            st.executeQuery(query);
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {

            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


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

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static ResultSet sqlGetResult(String query) {
        ResultSet rs = null;
        Connection conn = null;
        Statement st = null;
        try {
            conn = getConn();

            st = conn.createStatement();
            System.out.println(query);
            rs = st.executeQuery(query);
        }
        catch (SQLException ex) {
           ex.printStackTrace();
        }
        finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return rs;
    }
    public interface executeJob {
        void func(ResultSet resultSet);
    }



    }







