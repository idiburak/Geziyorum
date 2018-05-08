package com.example.recluse.geziyorum.test;

import com.example.recluse.geziyorum.db.bycrypt.BCrypt;
import java.sql.*;


public class RemoteDbTest {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://cemaltaskiran.com:3306/admin_geziyorum";

    //  Database credentials
    static final String USER = "admin_geziyorum";
    static final String PASS = "qlkICJUwxhSTE2PY";

    public static void main(String[] args){
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT password FROM users" +
                    " WHERE email = 'cemaltaskiran@gmail.com'";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                //Retrieve by column name
                String password  = rs.getString("password");
                password = password.replace("$2y$", "$2a$");
                String pw = BCrypt.hashpw("secret", BCrypt.gensalt(10));
                System.out.println(BCrypt.checkpw("secret", password));

                System.out.println("pw: " + pw);
                System.out.println("password: " + password);
            }
            rs.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
    }
}
