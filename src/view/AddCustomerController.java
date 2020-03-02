/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.Query;

/**
 * FXML Controller class
 *
 * @author bradharr
 */
public class AddCustomerController implements Initializable {
    
    //Set Stage and Scene
    Stage stage;
    Parent scene;

    //FXML ID Data
    @FXML
    private TextField customerName_txt;

    @FXML
    private TextField customerPhone_txt;

    @FXML
    private TextField customerAddress_txt;

    @FXML
    private ComboBox<String> city_combo;

    @FXML
    private TextField customerZip_txt;

    @FXML
    private Button save_btn;

    @FXML
    private Button cancel_btn;

    //Return User to Manage Customers Screen
    @FXML
    void cancel(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ManageCustomers.fxml"));
        loader.load();

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    //Adds new customer to database
    @FXML
    void saveCustomer(ActionEvent event) throws IOException {
        
        if(customerName_txt.getText().isEmpty() || customerPhone_txt.getText().isEmpty()|| customerZip_txt.getText().isEmpty() ||
           customerAddress_txt.getText().isEmpty()) {
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("All Fields are Required.  Please ensure no fields have been left blank.");
            alert.showAndWait();
        }
        else {
        
        int selectedItem = city_combo.getSelectionModel().getSelectedIndex();

        selectedItem = selectedItem + 1;

        Query.insertNewCustomer(0, customerName_txt.getText(), customerPhone_txt.getText(), 
                                0, selectedItem, customerZip_txt.getText(), customerAddress_txt.getText(), 
                                LoginController.currentUser, LoginController.currentUser);
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ManageCustomers.fxml"));
        loader.load();

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //Populates combo box for Add Customer Screen
        ObservableList<String> cityData = Query.getCityList();
        
        city_combo.setItems(cityData);
        
    }    

    
}
