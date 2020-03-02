/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.customer;
import utilities.Query;

/**
 * FXML Controller class
 *
 * @author bradharr
 */
public class ManageCustomersController implements Initializable {

    //Sets Stage & Scene
    Stage stage;
    Parent scene;
    
    //FXML ID Data
    @FXML
    private TableView<customer> customerTableView;

    @FXML
    private TableColumn<customer, Integer> custId_col;

    @FXML
    private TableColumn<customer, String> custName_col;

    @FXML
    private TableColumn<customer, String> custPhone_col;
    
    @FXML
    private TableColumn<customer, String> custAddId_col;

    @FXML
    private TableColumn<customer, String> custAddress_col;

    @FXML
    private TableColumn<customer, String> custCity_col;

    @FXML
    private TableColumn<customer, String> custZip_col;

    @FXML
    private TableColumn<customer, String> custCountry_col;

    @FXML
    private Button addCustomer_btn;

    @FXML
    private Button updateCustomer_btn;

    @FXML
    private Button deleteCustomer_btn;

    @FXML
    private Button mainMenu_btn;

    //Takes User to Update Customer Screen
    @FXML
    void UpdateCustomer(ActionEvent event) throws IOException {

        customer customer = customerTableView.getSelectionModel().getSelectedItem();
        if(customer == null)
        return;
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/UpdateCustomer.fxml"));
        loader.load();

        UpdateCustomerController CustomerController = loader.getController();
        CustomerController.retrieveCustomer(customer);

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    //Takes User to Add Customer Screen
    @FXML
    void addNewCustomer(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/AddCustomer.fxml"));
            loader.load();

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
    }

    //Deletes selected Customer
    @FXML
    void deleteCustomer(ActionEvent event) {

        int selectedCustomerId = 0;
        int selectedAddressId = 0;
        
        if(customerTableView.getSelectionModel().isEmpty()) {
            return;
        }
        else {
            selectedCustomerId = customerTableView.getSelectionModel().getSelectedItem().getCustomerId();
            selectedAddressId = customerTableView.getSelectionModel().getSelectedItem().getAddressId();
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning!");
        alert.setHeaderText("This will delete the selected Customer and all appointments associated with this Customer.");
        alert.setContentText("Would you like to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Query.deleteAppointmentByCustomer(selectedCustomerId);
            Query.deleteCustomer(selectedCustomerId, selectedAddressId);
        }
        
        
        ObservableList<customer> custData = Query.getAllCustomer();
        

            custId_col.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            custName_col.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            custPhone_col.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
            custAddId_col.setCellValueFactory(new PropertyValueFactory<>("addressId"));
            custAddress_col.setCellValueFactory(new PropertyValueFactory<>("address"));
            custCity_col.setCellValueFactory(new PropertyValueFactory<>("city"));
            custCountry_col.setCellValueFactory(new PropertyValueFactory<>("country"));
            custZip_col.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            
            customerTableView.setItems(custData);
        
    }

    //Returns User to Main Menu
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
            //Sets initial TableView Data
            ObservableList<customer> custData = Query.getAllCustomer();
        

            custId_col.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            custName_col.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            custPhone_col.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
            custAddId_col.setCellValueFactory(new PropertyValueFactory<>("addressId"));
            custAddress_col.setCellValueFactory(new PropertyValueFactory<>("address"));
            custCity_col.setCellValueFactory(new PropertyValueFactory<>("city"));
            custCountry_col.setCellValueFactory(new PropertyValueFactory<>("country"));
            custZip_col.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            
            customerTableView.setItems(custData);
     
        }
    
}    
