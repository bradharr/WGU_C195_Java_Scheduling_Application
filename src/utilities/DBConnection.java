/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author bradharr
 */
public class DBConnection {
    
    //JDBC URL Parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//3.227.166.251/U05n2p";
    
    private static final String jdbcURL = protocol + vendorName + ipAddress;
    
    //Driver and Connection Interface Reference
    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    public static Connection conn;
    
    //Username and Password
    private static final String userName = "U05n2p";  //UserName
    private static final String password = "53688553641";
    
    //Start Database Connection
    public static Connection startConnection() {
        if(conn != null) {
            return conn;
        }
    
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = (Connection)DriverManager.getConnection(jdbcURL, userName, password);
            System.out.println("Connection Successful!");
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
                }
        catch (SQLException e) {
            System.out.println(e.getMessage());    
        }
        
        return conn;
    
    }
    //Close Database Connection
    public static void closeConnection() {
     
            System.out.println("Connection Closed!");
    }   
}
