package com.wcs.demo.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectDB2 {

    /**
     * @param args
     */
    public static void main(String[] args) {

        String driver = "com.ibm.db2.jcc.DB2Driver";
        String url = "jdbc:db2://192.168.88.10:50000/test:currentSchema=SKY;";
        String userName = "db2admin";
        String passWord = "123456";

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        String sql = null;
        try {
            Class.forName(driver).newInstance();

            conn = DriverManager.getConnection(url, userName, passWord);

            st = conn.createStatement();

            //st.execute("set current schema SKY");

            sql = new String("SELECT * FROM TEST");
            rs = st.executeQuery(sql);
            while(rs.next()) {
                System.out.println(rs.getString(1));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}