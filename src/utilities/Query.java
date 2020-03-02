/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.ApptRpt;
import model.CustRpt;
import model.ProviderRpt;
import model.appointment;
import model.customer;
import static utilities.DBConnection.conn;
import view.LoginController;

/**
 *
 * @author bradharr
 */

//Method for executing Make and Update Queries
public class Query {
    
    private static String query;
    private static Statement stmt;
    private static ResultSet result;
    
    public static void makeQuery(String q) {
        
        query = q;
        
        try {
            //Create Statement Object
            stmt = conn.createStatement();
            
            //Determine Query Execution
            if(query.toLowerCase().startsWith("select"))
                result = stmt.executeQuery(query);
            
            if(query.toLowerCase().startsWith("delete") || 
               query.toLowerCase().startsWith("insert") || 
               query.toLowerCase().startsWith("update"))
               stmt.executeUpdate(query);
            
        }
        catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
    public static ResultSet getResult() {
        
        return result;
        
    }
    
    //Method for Inserting a New User
    public static String insertNewUser(String userName, String password, int active, 
                  String createDate, String createdBy, String lastUpdateBy) {
        
    try {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        
        String sqlStmt = "INSERT INTO user (userName, password, active, createDate, "
                         + "createdBy, lastUpdateBy) values (?, ?, ?, now(), ?, ?)";
        
        myConn = DBConnection.startConnection();
        myStmt = myConn.prepareStatement(sqlStmt);
        
        myStmt.setString(1, userName);
        myStmt.setString(2, password);
        myStmt.setInt(3, active);
        myStmt.setString(4, createdBy);
        myStmt.setString(5, lastUpdateBy);
        
        
        myStmt.executeUpdate();
        DBConnection.closeConnection();

        }
    catch(Exception ex) {
        System.out.println("Error: " + ex.getMessage());
        }
    return null;
    }   
    
    //Method for Updating a User
    public static String updateUser(int userId, String userName, String password, 
                                    int active, String createDate, String createdBy, 
                                    String lastUpdate, String lastUpdateBy) {
        
        
    try {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        
        String sqlStmt = "UPDATE user SET userName = ?, password = ?, active = ?, "
                          + "lastUpdate = now(), lastUpdateBy = ? WHERE userId = ?";
        
        myConn = DBConnection.startConnection();
        myStmt = myConn.prepareStatement(sqlStmt);
        
        
        myStmt.setString(1, userName);
        myStmt.setString(2, password);
        myStmt.setInt(3, active);
        myStmt.setString(4, lastUpdateBy);
        myStmt.setInt(5, userId);

        
        
        myStmt.executeUpdate();
        DBConnection.closeConnection();

        }
    catch(Exception ex) {
        System.out.println("Error: " + ex.getMessage());
        }
    return null;
    }  
    
    //Method for Deleting a User
    public static String deleteUser(int userId, String userName, String password, 
                                        int active, String createDate, String createdBy, 
                                        String lastUpdate, String lastUpdateBy) {
        
        
    try {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        
        String sqlStmt = "DELETE FROM user WHERE userId = ?";
        
        myConn = DBConnection.startConnection();
        myStmt = myConn.prepareStatement(sqlStmt);
        
        
        myStmt.setInt(1, userId);

        
        
        myStmt.executeUpdate();
        DBConnection.closeConnection();

        }
    catch(Exception ex) {
        System.out.println("Error: " + ex.getMessage());
        }
    return null;
    
    }   
    
    //ObservableList for getting all Customer Records
    public static ObservableList<customer> getAllCustomer() {
 
        ObservableList<customer> data = FXCollections.observableArrayList();
        
    try {
       
        
        Connection myConn = null;
        PreparedStatement myStmt = null;
        
        String sqlStmt = "SELECT customerId, c.addressId, ci.cityId, customerName, phone, address, address2, city, postalCode, country " +
                         "FROM customer c, address a, city ci, country co " +
                         "WHERE c.addressId = a.addressId and a.cityId = ci.cityId and ci.countryId = co.countryId";
        
        myConn = DBConnection.startConnection();
        myStmt = myConn.prepareStatement(sqlStmt);
        ResultSet rs = myStmt.executeQuery();
        
        while(rs.next()) {
            
            int customerId = rs.getInt("customerId");
            String customerName = rs.getString("customerName");
            String customerPhone = rs.getString("phone");
            int addressId = rs.getInt("c.addressId");
            int cityId = rs.getInt("ci.cityId");
            String city = rs.getString("city");
            String country = rs.getString("country");
            String address = rs.getString("address");
            String postalCode = rs.getString("postalCode");

            customer c = new customer(customerId, customerName, customerPhone, addressId, 
                         cityId, city, country, postalCode, address);
            data.add(c);
        }
  
        DBConnection.closeConnection();

        }
    catch(Exception ex) {
        System.out.println("Error: " + ex.getMessage());
        }
    return data;
    
    }
    
    //Method for Inserting a new Customer
    public static String insertNewCustomer(int customerId, String customerName, String customerPhone, int addressId,
                    int cityId, String postalCode, String address, String createdBy, String lastUpdateBy) {
        
    try {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        PreparedStatement myStmt2 = null;
        
        String sqlStmt = "INSERT INTO customer(customerName, addressId, active, createDate, "
                       + "createdBy, lastUpdate, lastUpdateBy) values (?, "
                       + "(select addressId from address where address = ?), 1, now(), ?, now(), ?)";
        String sqlStmt2 = "INSERT INTO address(address, address2, cityId, postalCode, "
                        + "phone, createDate, createdBy, lastUpdate, lastUpdateBy) values(?, 'n/a', "
                        + "?, ?, ?, now(), ?, now(), ?)";
        
        myConn = DBConnection.startConnection();
        myStmt = myConn.prepareStatement(sqlStmt);
        myStmt2 = myConn.prepareStatement(sqlStmt2);
        
        myStmt.setString(1, customerName);
        myStmt.setString(2, address);
        myStmt.setString(3, createdBy);
        myStmt.setString(4, lastUpdateBy);
        
        myStmt2.setString(1, address);
        myStmt2.setInt(2, cityId);
        myStmt2.setString(3, postalCode);
        myStmt2.setString(4, customerPhone);
        myStmt2.setString(5, createdBy);
        myStmt2.setString(6, lastUpdateBy);
        
        myStmt2.executeUpdate();
        myStmt.executeUpdate();
        DBConnection.closeConnection();

        }
    catch(Exception ex) {
        System.out.println("Error: " + ex.getMessage());
        }
    return null;
    }
    
    //ObservableList to Get List of Cities
    public static ObservableList<String> getCityList() {
    
       ObservableList<String> data = FXCollections.observableArrayList();
       
       try {
       
        
        Connection myConn = null;
        PreparedStatement myStmt = null;
        
        String sqlStmt = "SELECT * from city";
        
        myConn = DBConnection.startConnection();
        myStmt = myConn.prepareStatement(sqlStmt);
        ResultSet rs = myStmt.executeQuery();
        
        while(rs.next()) {
            
            String city = rs.getString("city");
            

            String cList = new String(city);
            data.add(cList);
        }
  
        DBConnection.closeConnection();

        }
    catch(Exception ex) {
        System.out.println("Error: " + ex.getMessage());
        }
    return data;
}
    
    //Method for Updating Customer Records
    public static String updateCustomer(int customerId, String customerName, String customerPhone, int addressId,
                    int cityId, String postalCode, String address, String createdBy, String lastUpdateBy) {
        
    try {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        PreparedStatement myStmt2 = null;
        
        String sqlStmt = "UPDATE customer SET customerName = ?, lastUpdate = now(), "
                       + "lastUpdateBy = ?  WHERE customerId = ?";
        String sqlStmt2 = "UPDATE address SET address = ?, cityId = ?, postalCode = ?, "
                        + "phone = ?, lastUpdate = now(), lastUpdateBy = ?  WHERE addressId = ?";
        
        myConn = DBConnection.startConnection();
        myStmt = myConn.prepareStatement(sqlStmt);
        myStmt2 = myConn.prepareStatement(sqlStmt2);
        
        myStmt.setString(1, customerName);
        myStmt.setString(2, lastUpdateBy);
        myStmt.setInt(3, customerId);
        
        myStmt2.setString(1, address);
        myStmt2.setInt(2, cityId);
        myStmt2.setString(3, postalCode);
        myStmt2.setString(4, customerPhone);
        myStmt2.setString(5, lastUpdateBy);
        myStmt2.setInt(6, addressId);
        
        myStmt2.executeUpdate();
        myStmt.executeUpdate();
        DBConnection.closeConnection();

        }
    catch(Exception ex) {
        System.out.println("Error: " + ex.getMessage());
        }
    return null;
    }
        
    //Method for Deleting Customer Records
    public static String deleteCustomer(int customerId, int addressId) {
        
        
    try {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        PreparedStatement myStmt2 = null;
        
        String sqlStmt = "DELETE FROM customer WHERE customerId = ?";
        String sqlStmt2 = "DELETE FROM address WHERE addressId = ?";
        
        myConn = DBConnection.startConnection();
        myStmt = myConn.prepareStatement(sqlStmt);
        myStmt2 = myConn.prepareStatement(sqlStmt2);
        
        
        myStmt.setInt(1, customerId);
        myStmt2.setInt(1, addressId);    

        
        myStmt.executeUpdate();
        myStmt2.executeUpdate();
        DBConnection.closeConnection();

        }
    catch(Exception ex) {
        System.out.println("Error: " + ex.getMessage());
        }
    return null;
    
    }
    
    //ObservableList for getting all appointments
    public static ObservableList<appointment> getAllAppointments() {
 
        ObservableList<appointment> data = FXCollections.observableArrayList();
        
    try {
       
        
        Connection myConn = null;
        PreparedStatement myStmt = null;
        
        String sqlStmt = "SELECT a.appointmentID, a.customerId, a.userId, a.type, "
                       + "a.start, a.end, c.customerName, u.userName "
                       + "FROM appointment a, customer c, user u "
                       + "WHERE a.customerId = c.customerId and a.userId = u.userId";
        
        myConn = DBConnection.startConnection();
        myStmt = myConn.prepareStatement(sqlStmt);
        ResultSet rs = myStmt.executeQuery();
        
        while(rs.next()) {
            ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
            DateTimeFormatter formatterLocal = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(localZoneId);
            
            LocalDate startDate = LocalDate.parse(rs.getString("start").substring(0, 10));
            LocalTime startTime = LocalTime.parse(rs.getString("start").substring(11, 19));
            LocalDate endDate = LocalDate.parse(rs.getString("end").substring(0, 10));
            LocalTime endTime = LocalTime.parse(rs.getString("end").substring(11, 19));
            
            ZonedDateTime UTCStartDT = ZonedDateTime.of(startDate, startTime, ZoneId.of("UTC"));
            ZonedDateTime UTCEndDT = ZonedDateTime.of(endDate, endTime, ZoneId.of("UTC"));
            
            String localStartDT = formatterLocal.format(UTCStartDT);
            String localEndDT = formatterLocal.format(UTCEndDT);
            

            int appointmentId = rs.getInt("appointmentId");
            int customerId = rs.getInt("customerId");
            int userId = rs.getInt("userId");
            String type = rs.getString("type");
            String start = localStartDT;
            String end = localEndDT;
            String userName = rs.getString("userName");
            String customerName = rs.getString("customerName");

     
            appointment a = new appointment(appointmentId, customerId, userId, type, 
                                start, end, userName, customerName);
            data.add(a);
        }
  
        DBConnection.closeConnection();

        }
    catch(Exception ex) {
        System.out.println("Error: " + ex.getMessage());
        }
    return data;
    
    }
    
    //Method for Inserting a New Appointment
    public static String insertNewAppointment(int appointmentId, String customerName, 
                  String providerName, String type, String start, String end, String currentUser) {
        
    try {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        
        String sqlStmt = "INSERT INTO appointment(customerId, userId, title, description, "
                       + "location, contact, type, url, start, end, createDate, createdBy, "
                       + "lastUpdate, lastUpdateBy) values ((select customerId from customer "
                       + "where customerName = ?), (select userId from user where userName = ?), "
                       + "'n/a', 'n/a', 'n/a', 'n/a', ?, 'n/a', ?, ?, now(), ?, now(), ?)";
 
        
        myConn = DBConnection.startConnection();
        myStmt = myConn.prepareStatement(sqlStmt);
        
        myStmt.setString(1, customerName);
        myStmt.setString(2, providerName);
        myStmt.setString(3, type);
        myStmt.setString(4, start);
        myStmt.setString(5, end);
        myStmt.setString(6, currentUser);
        myStmt.setString(7, currentUser);
        

        myStmt.executeUpdate();
        DBConnection.closeConnection();

        }
    catch(Exception ex) {
        System.out.println("Error: " + ex.getMessage());
        }
    return null;
    }
    
    //ObservableList for getting all customer records
    public static ObservableList<String> getCustomerList() {
    
       ObservableList<String> data = FXCollections.observableArrayList();
       
       try {
       
        
        Connection myConn = null;
        PreparedStatement myStmt = null;
        
        String sqlStmt = "SELECT * from customer";
        
        myConn = DBConnection.startConnection();
        myStmt = myConn.prepareStatement(sqlStmt);
        ResultSet rs = myStmt.executeQuery();
        
        while(rs.next()) {
            
            String customerName = rs.getString("customerName");
            

            String cList = new String(customerName);
            data.add(cList);
        }
  
        DBConnection.closeConnection();

        }
    catch(Exception ex) {
        System.out.println("Error: " + ex.getMessage());
        }
    return data;
    }
    
    //ObservableList for getting all Providers(Consultants)
    public static ObservableList<String> getProviderList() {
    
       ObservableList<String> data = FXCollections.observableArrayList();
       
       try {
       
        
        Connection myConn = null;
        PreparedStatement myStmt = null;
        
        String sqlStmt = "SELECT * from user";
        
        myConn = DBConnection.startConnection();
        myStmt = myConn.prepareStatement(sqlStmt);
        ResultSet rs = myStmt.executeQuery();
        
        while(rs.next()) {
            
            String userName = rs.getString("userName");
            

            String cList = new String(userName);
            data.add(cList);
        }
  
        DBConnection.closeConnection();

        }
    catch(Exception ex) {
        System.out.println("Error: " + ex.getMessage());
        }
    return data;
    }
    
    //Method for updating appointments
    public static String updateAppointment(int appointmentId, String customerName, 
                  String providerName, String type, String start, String end, String currentUser) {
        
    try {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        
        String sqlStmt = "UPDATE appointment SET customerId = (select customerId from customer "
                       + "where customerName = ?), userId = (select userId from user where userName = ?), "
                       + "type = ?, start = ?, end = ?, lastUpdate = now(), lastUpdateBy = ? "
                       + "WHERE appointmentId = ?";
 
        
        myConn = DBConnection.startConnection();
        myStmt = myConn.prepareStatement(sqlStmt);
        
        myStmt.setString(1, customerName);
        myStmt.setString(2, providerName);
        myStmt.setString(3, type);
        myStmt.setString(4, start);
        myStmt.setString(5, end);
        myStmt.setString(6, currentUser);
        myStmt.setInt(7, appointmentId);
        

        myStmt.executeUpdate();
        DBConnection.closeConnection();

        }
    catch(Exception ex) {
        System.out.println("Error: " + ex.getMessage());
        }
    return null;
    }
    
    //Method for deleting appointments
    public static String deleteAppointment(int appointmentId) {
        
    try {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        
        String sqlStmt = "DELETE from appointment WHERE appointmentId = ?";
 
        
        myConn = DBConnection.startConnection();
        myStmt = myConn.prepareStatement(sqlStmt);
        
        myStmt.setString(1, Integer.toString(appointmentId));
        

        myStmt.executeUpdate();
        DBConnection.closeConnection();

        }
    catch(Exception ex) {
        System.out.println("Error: " + ex.getMessage());
        }
    return null;
    }
    
    //Method for deleting appointments by user
    public static String deleteAppointmentByUser(int userId) {
        
    try {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        
        String sqlStmt = "DELETE from appointment WHERE userId = ?";
 
        
        myConn = DBConnection.startConnection();
        myStmt = myConn.prepareStatement(sqlStmt);
        
        myStmt.setString(1, Integer.toString(userId));
        

        myStmt.executeUpdate();
        DBConnection.closeConnection();

        }
    catch(Exception ex) {
        System.out.println("Error: " + ex.getMessage());
        }
    return null;
    }
    
    //Method for deleting appointments by customer
    public static String deleteAppointmentByCustomer(int customerId) {
        
    try {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        
        String sqlStmt = "DELETE from appointment WHERE customerId = ?";
 
        
        myConn = DBConnection.startConnection();
        myStmt = myConn.prepareStatement(sqlStmt);
        
        myStmt.setString(1, Integer.toString(customerId));
        

        myStmt.executeUpdate();
        DBConnection.closeConnection();

        }
    catch(Exception ex) {
        System.out.println("Error: " + ex.getMessage());
        }
    return null;
    }
    
    //Method for checking appointments at login for appointments within 15 minutes
    public static void checkAppointments() {
        
    try {
       
        
        Connection myConn = null;
        PreparedStatement myStmt = null;
        
        String sqlStmt = "SELECT a.appointmentID, a.customerId, a.userId, a.type, "
                       + "a.start, a.end, c.customerName, u.userName "
                       + "FROM appointment a, customer c, user u "
                       + "WHERE a.customerId = c.customerId and a.userId = u.userId";
        
        String currentUser = LoginController.currentUser;
        myConn = DBConnection.startConnection();
        myStmt = myConn.prepareStatement(sqlStmt);
        ResultSet rs = myStmt.executeQuery();
        
        while(rs.next()) {
            ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
            DateTimeFormatter formatterLocalDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(localZoneId);
            DateTimeFormatter formatterLocalTime = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(localZoneId);
            
            LocalDate startDate = LocalDate.parse(rs.getString("start").substring(0, 10));
            LocalTime startTime = LocalTime.parse(rs.getString("start").substring(11, 19));
            
            ZonedDateTime UTCStartDT = ZonedDateTime.of(startDate, startTime, ZoneId.of("UTC"));
            
            String localStartDate= formatterLocalDate.format(UTCStartDT);
            LocalDate ltStartDate = LocalDate.parse(localStartDate);
            String localStartTime= formatterLocalTime.format(UTCStartDT);
            LocalTime ltStartTime = LocalTime.parse(localStartTime);
            
            LocalDate currentDate = LocalDate.now();
            LocalTime currentTime = LocalTime.now();
            
            Long timeDifference = ChronoUnit.MINUTES.between(currentTime, ltStartTime);
            long elapsedTime = timeDifference + 1;

            
            if(currentUser == null ? rs.getString("u.userName") == null : currentUser.equals(rs.getString("u.userName"))) {
                if(currentDate.equals(ltStartDate)) {
                    if(elapsedTime >= 0 && elapsedTime <= 15) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Appointment Reminder");
                        alert.setContentText("Hello " + rs.getString("u.userName") + "! You have an appointment in approximately " + elapsedTime + " minute(s) with " + rs.getString("c.customerName"));
                        alert.showAndWait();
                        return;
                    }
                }
            }
        }
  
        DBConnection.closeConnection();

        }
    catch(Exception ex) {
        System.out.println("Error: " + ex.getMessage());
        }  
    }
    
    //ObservableList to get appointment times
    public static ObservableList<String> getApptTimes() {
    ObservableList<String> apptTimes = FXCollections.observableArrayList();

    try {
            
            File file = new File("apptTimes.txt");
            Scanner inputFile = new Scanner(file);
            
            while(inputFile.hasNext()) {
                
                apptTimes.add(inputFile.nextLine());
                
            }
            
        }
        catch(IOException e) {
            
            System.out.println("Error!");           
        }
        return apptTimes;
    }
    
    //ObservableList to get Report 1 Data
    public static ObservableList<ApptRpt> getRpt1() {
 
        ObservableList<ApptRpt> data = FXCollections.observableArrayList();
        
    try {
       
        
        Connection myConn = null;
        PreparedStatement myStmt = null;
        
        String sqlStmt = "select monthname(start) as Month, type, count(appointmentId) as Total " +
                         "from appointment " +
                         "group by monthname(start), type " +
                         "order by start, type";
        
        myConn = DBConnection.startConnection();
        myStmt = myConn.prepareStatement(sqlStmt);
        ResultSet rs = myStmt.executeQuery();
        
        while(rs.next()) {

            String month = rs.getString("Month");
            String type = rs.getString("type");
            String total = rs.getString("Total");
            

            ApptRpt a = new ApptRpt(month, type, total);
            data.add(a);
        }
  
        DBConnection.closeConnection();

        }
    catch(Exception ex) {
        System.out.println("Error: " + ex.getMessage());
        }
    return data;
    
    }
    
    //ObservableList to get Report 2 Data
    public static ObservableList<ProviderRpt> getRpt2() {
 
        ObservableList<ProviderRpt> data = FXCollections.observableArrayList();
        
    try {
       
        
        Connection myConn = null;
        PreparedStatement myStmt = null;
        
        String sqlStmt = "SELECT u.username, a.start, a.end, c.customerName " +
                         "FROM appointment a, customer c, user u " +
                         "WHERE a.customerId = c.customerId and a.userId = u.userId " +
                         "order by userName, start";
        
        myConn = DBConnection.startConnection();
        myStmt = myConn.prepareStatement(sqlStmt);
        ResultSet rs = myStmt.executeQuery();
        
        while(rs.next()) {

            String provider = rs.getString("u.username");
            String start = rs.getString("a.start");
            String end = rs.getString("a.end");
            String customer = rs.getString("c.customerName");
            

            ProviderRpt a = new ProviderRpt(provider, start, end, customer);
            data.add(a);
        }
  
        DBConnection.closeConnection();

        }
    catch(Exception ex) {
        System.out.println("Error: " + ex.getMessage());
        }
    return data;
    
    }
    
    //ObservableList for Report 3 Data
    public static ObservableList<CustRpt> getRpt3() {
 
        ObservableList<CustRpt> data = FXCollections.observableArrayList();
        
    try {
       
        
        Connection myConn = null;
        PreparedStatement myStmt = null;
        
        String sqlStmt = "SELECT c.customerName, a.start, a.end " +
"                       FROM appointment a, customer c " +
"                       WHERE a.customerId = c.customerId " +
"                       order by customerName, start";
        
        myConn = DBConnection.startConnection();
        myStmt = myConn.prepareStatement(sqlStmt);
        ResultSet rs = myStmt.executeQuery();
        
        while(rs.next()) {

            String customer = rs.getString("c.customerName");
            String start = rs.getString("a.start");
            String end = rs.getString("a.end");
            

            CustRpt a = new CustRpt(customer, start, end);
            data.add(a);
        }
  
        DBConnection.closeConnection();

        }
    catch(Exception ex) {
        System.out.println("Error: " + ex.getMessage());
        }
    return data;
    
    }
 
}
