package com.example.recluse.geziyorum.db.helper;

import com.example.recluse.geziyorum.db.bycrypt.BCrypt;
import java.sql.*;

public class RemoteDbHelper {

    private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://cemaltaskiran.com:3306/admin_geziyorum";

    //  Database credentials
    private final String USER = "admin_geziyorum";
    private final String PASS = "qlkICJUwxhSTE2PY";

    private Connection conn = null;

    public RemoteDbHelper() {
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            //System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            //Handle errors for Class.forName
            System.out.println("hobaaaaa");
            e.printStackTrace();
        }
    }

    public boolean checkPassword(String usernameOrEmail, String password) {

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT password FROM users WHERE username = ? OR email = ?");
            ps.setString(1, usernameOrEmail);
            ps.setString(2, usernameOrEmail);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                //Retrieve by column name
                String pass = rs.getString("password");
                return  BCrypt.checkpw(password, pass);

            }
            rs.close();
        } catch (Exception e) {
            //Handle errors for Class.forName
            System.out.println("-----------------------------------------------------------------------");
            e.printStackTrace();
            return false;
        }
        return false;
    }
}