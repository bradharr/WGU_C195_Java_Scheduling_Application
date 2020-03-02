/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.appointment;
import utilities.Query;

/**
 * FXML Controller class
 *
 * @author bradharr
 */
public class UpdateAppointmentController implements Initializable {
    
    //Sets Stage & Scene
    Stage stage;
    Parent scene;

    //FXML ID Data
    @FXML
    private ComboBox<String> custName_combo;
    @FXML
    private ComboBox<String> provider_combo;
    @FXML
    private TextField apptType_txt;
    @FXML
    private DatePicker startDate_dp;

    @FXML
    private ComboBox<String> start_combo;

    @FXML
    private DatePicker endDate_dp;

    @FXML
    private ComboBox<String> end_combo;
    @FXML
    private Button save_btn;
    @FXML
    private Button cancel_btn;
    @FXML
    private TextField apptId_txt;
    
    //Checks proposed appointment updates for overlaps with existing appointments
    private boolean getOverlap() {
        
        
        ObservableList<appointment> aList = Query.getAllAppointments();
        for(appointment a : aList) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
            LocalDateTime aListStartDT = LocalDateTime.parse(a.getStart(), formatter);
            LocalDateTime aListEndDT = LocalDateTime.parse(a.getEnd(), formatter);
            
            
            LocalDate selectedStartDate = startDate_dp.getValue();
            LocalDate selectedEndDate = endDate_dp.getValue();
            LocalTime selectedStartTime = LocalTime.parse(start_combo.getSelectionModel().getSelectedItem());
            LocalTime selectedEndTime = LocalTime.parse(end_combo.getSelectionModel().getSelectedItem());
            

            LocalDateTime ldtStartDT = LocalDateTime.of(selectedStartDate, selectedStartTime);
            LocalDateTime ldtEndDT = LocalDateTime.of(selectedEndDate, selectedEndTime);
            
            
            int startCompare = ldtStartDT.compareTo(aListStartDT);
            int startEndCompare = ldtStartDT.compareTo(aListEndDT);
            int endCompare = ldtEndDT.compareTo(aListEndDT);
            int endStartCompare = ldtEndDT.compareTo(aListStartDT);
            
            if(startCompare >= 0 && startEndCompare <= 0 || endCompare <= 0 && endStartCompare >= 0)
               return true; 
            }    
    return false;
    }
    
    //Saves Updated appointment after exception controls are checked
    @FXML
    private void saveAppt(ActionEvent event) throws IOException {
        
        DateTimeFormatter formatterUTC = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("UTC"));
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        String selectedCustomerName = custName_combo.getSelectionModel().getSelectedItem();
        String selectedProviderName = provider_combo.getSelectionModel().getSelectedItem();
        String selectedStartDate = String.valueOf(startDate_dp.getValue());
        String selectedStartTime = start_combo.getSelectionModel().getSelectedItem();
        String selectedEndDate = String.valueOf(endDate_dp.getValue());
        String selectedEndTime = end_combo.getSelectionModel().getSelectedItem();
        
        LocalDate startDate = LocalDate.parse(selectedStartDate);
        LocalTime startTime = LocalTime.parse(selectedStartTime);
        LocalDate endDate = LocalDate.parse(selectedEndDate);
        LocalTime endTime = LocalTime.parse(selectedEndTime);
        
        ZonedDateTime localStartDT = ZonedDateTime.of(startDate, startTime, localZoneId);
        ZonedDateTime localEndDT = ZonedDateTime.of(endDate, endTime, localZoneId);
        
        String UTCStartDTString = formatterUTC.format(localStartDT);
        String UTCEndDTString = formatterUTC.format(localEndDT);
        
        int startValue = LocalTime.parse(selectedStartTime).compareTo(LocalTime.parse("08:00:00"));
        int endValue = LocalTime.parse(selectedEndTime).compareTo(LocalTime.parse("17:00:00"));
        int endTimeAfterStart = selectedEndTime.compareTo(selectedStartTime);
        int endDateAfterStart = selectedEndDate.compareTo(selectedStartDate);
        
        if(startValue < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Start time falls outside of Business Hours. Please select a start time between 8 AM and 5 PM Local Time");
            alert.showAndWait();
            return;
        }
        else if(endValue > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("End time falls outside of Business Hours. Please select a start time between 8 AM and 5 PM Local Time");
            alert.showAndWait();
            return;
        }
        else if(endTimeAfterStart <= 0 || endDateAfterStart < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("End Date and Time cannot be before Start Date and Time.");
            alert.showAndWait();
            return;
        }
        else if(getOverlap() == true) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Your Appointment has an overlap with another appointment, please adjust your dates and times and try again.");
            alert.showAndWait();
            return;
        }
        else {
            
        Query.updateAppointment(Integer.parseInt(apptId_txt.getText()), selectedCustomerName, selectedProviderName, apptType_txt.getText(), 
                                UTCStartDTString, UTCEndDTString, LoginController.currentUser);
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ManageAppointments.fxml"));
        loader.load();

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
        
        }  
    }

    //Takes User to Manage Appointments Screen
    @FXML
    private void cancel(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ManageAppointments.fxml"));
        loader.load();

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Initializes the controller class.
     */
    
    //Sets Appointment Data for passing to FXML Form
    public void retrieveAppointments(appointment appointment) {
        
        String apptStartDT = appointment.getStart();
        String apptEndDT = appointment.getEnd();
        
        LocalDate apptStartDate = LocalDate.parse(apptStartDT.substring(0, 10));
        String apptStartTime = apptStartDT.substring(11, 19);
        LocalDate apptEndDate = LocalDate.parse(apptEndDT.substring(0, 10));
        String apptEndTime = apptEndDT.substring(11, 19);       
        
        apptId_txt.setText(Integer.toString(appointment.getAppointmentId()));
        apptType_txt.setText(appointment.getType());
        startDate_dp.setValue(apptStartDate);
        endDate_dp.setValue(apptEndDate);
        start_combo.setValue(apptStartTime);
        end_combo.setValue(apptEndTime);
        custName_combo.setValue(appointment.getCustomerName());
        provider_combo.setValue(appointment.getUserName());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Sets combo box data for Update Appointment Screen
        ObservableList<String> custData = Query.getCustomerList();
        ObservableList<String> providerData = Query.getProviderList();
        ObservableList<String> apptTimes = Query.getApptTimes();
        
        custName_combo.setItems(custData);
        provider_combo.setItems(providerData);
        start_combo.setItems(apptTimes);
        end_combo.setItems(apptTimes);
    }    
    
}
