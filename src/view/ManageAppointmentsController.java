/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.appointment;
import utilities.Query;

/**
 * FXML Controller class
 *
 * @author bradharr
 */
public class ManageAppointmentsController implements Initializable {
    
    //Sets Stage and Screen
    Stage stage;
    Parent scene;
    
    //FXML ID Data
    @FXML
    private TableView<appointment> apptTableView;

    @FXML
    private TableColumn<appointment, Integer> apptId_col;

    @FXML
    private TableColumn<appointment, String> custName_col;

    @FXML
    private TableColumn<appointment, String> providerName_col;

    @FXML
    private TableColumn<appointment, String> apptType_col;

    @FXML
    private TableColumn<appointment, String> apptStart_col;

    @FXML
    private TableColumn<appointment, String> apptStop_col;
    
    @FXML
    private RadioButton all_rb;

    @FXML
    private ToggleGroup calendarView_tg;

    @FXML
    private RadioButton month_rb;

    @FXML
    private RadioButton week_rb;
    
    //Sets TableView data for the All Radio Button
    @FXML
    void selectAll(ActionEvent event) {
        
        ObservableList<appointment> apptData = Query.getAllAppointments();
        
        apptId_col.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        custName_col.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        providerName_col.setCellValueFactory(new PropertyValueFactory<>("userName"));
        apptType_col.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStart_col.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptStop_col.setCellValueFactory(new PropertyValueFactory<>("end"));
        
        apptTableView.setItems(apptData);

    }
    //Sets TableView data fro Month Radio Button
    @FXML
    void selectMonth(ActionEvent event) {
        
        
        ObservableList<appointment> apptData = Query.getAllAppointments();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime month = now.plusMonths(1);
        
        /*First Lambda Expression, used to cycle through a filtered ObservableList 
        to filter TableView to a Month View of Appointments*/
        FilteredList<appointment> filter = new FilteredList<>(apptData);
        filter.setPredicate(row -> {

            LocalDateTime startDT = LocalDateTime.parse(row.getStart(), format);

            return startDT.isAfter(now.minusDays(1)) && startDT.isBefore(month);
        });
        
        apptTableView.setItems(filter);
    }
    //Sets TableView Data for Week Radio Button
    @FXML
    void selectWeek(ActionEvent event) {

        ObservableList<appointment> apptData = Query.getAllAppointments();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime week = now.plusWeeks(1);
        
        /*Second Lambda Expression, used to cycle through a filtered ObservableList
        to filter TableView to a Week View of Appointments*/
        FilteredList<appointment> filter = new FilteredList<>(apptData);
        filter.setPredicate(row -> {

            LocalDateTime startDT = LocalDateTime.parse(row.getStart(), format);

            return startDT.isAfter(now.minusDays(1)) && startDT.isBefore(week);
        });
        
        apptTableView.setItems(filter);
    }
    
    @FXML
    private void chooseMonth(ActionEvent event) {

                }

    @FXML
    void chooseWeek(ActionEvent event) {

    }
    //FXML ID Data
    @FXML
    private Button addCustomer_btn;

    @FXML
    private Button updateCustomer_btn;

    @FXML
    private Button deleteCustomer_btn;

    @FXML
    private Button mainMenu_btn;
    
    //Date Formatter Variable
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    //Takes User to Add Appointment Screen
    @FXML
    void AddNewAppt(ActionEvent event) throws IOException {
         
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/AddAppointment.fxml"));
        loader.load();

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
        

    }
    //Deletes Selected Appointment
    @FXML
    void DeleteAppt(ActionEvent event) {
        
        int selectedAppointmentId = apptTableView.getSelectionModel().getSelectedItem().getAppointmentId();
        
        Query.deleteAppointment(selectedAppointmentId);
        
        ObservableList<appointment> apptData = Query.getAllAppointments();
        
        apptId_col.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        custName_col.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        providerName_col.setCellValueFactory(new PropertyValueFactory<>("userName"));
        apptType_col.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStart_col.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptStop_col.setCellValueFactory(new PropertyValueFactory<>("end"));
        
        apptTableView.setItems(apptData);

    }
    //Takes User to Update Appointment Screen
    @FXML
    void UpdateAppt(ActionEvent event) throws IOException {
        
        appointment appointment = apptTableView.getSelectionModel().getSelectedItem();
        if(appointment == null)
        return;
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/UpdateAppointment.fxml"));
        loader.load();

        UpdateAppointmentController AppointmentController = loader.getController();
        AppointmentController.retrieveAppointments(appointment);

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }
    //Takes User back to the Main Menu
    @FXML
    void returnMainMenu(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/MainMenu.fxml"));
        loader.load();

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Sets Initial TableView Data
        ObservableList<appointment> apptData = Query.getAllAppointments();
        
        apptId_col.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        custName_col.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        providerName_col.setCellValueFactory(new PropertyValueFactory<>("userName"));
        apptType_col.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStart_col.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptStop_col.setCellValueFactory(new PropertyValueFactory<>("end"));
        
        apptTableView.setItems(apptData);

    }    
}
