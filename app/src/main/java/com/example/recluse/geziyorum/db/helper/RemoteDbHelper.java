package com.example.recluse.geziyorum.db.helper;

import com.example.recluse.geziyorum.db.bycrypt.BCrypt;
import java.sql.*;
import javax.sql.*;

public class RemoteDbHelper {

    private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://cemaltaskiran.com:3306/admin_geziyorum";

    //  Database credentials
    private final String USER = "admin_geziyorum";
    private final String PASS = "qlkICJUwxhSTE2PY";

    private Connection conn = null;
    private Statement stmt = null;

    public RemoteDbHelper() {
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    public boolean checkPassword() {

        try {
            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT password FROM users" +
                    " WHERE email = 'cemaltaskiran@gmail.com'";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                //Retrieve by column name
                String password = rs.getString("password");
                return  BCrypt.checkpw("secrett", password);

            }
            rs.close();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
            return false;
        }
        return false;
    }
}