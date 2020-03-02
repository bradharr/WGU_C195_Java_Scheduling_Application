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
import model.customer;
import utilities.Query;

/**
 * FXML Controller class
 *
 * @author bradharr
 */
public class UpdateCustomerController implements Initializable {
    
    //Sets Stage & Scene
    Stage stage;
    Parent scene;

    //FXML ID Data
    @FXML
    private TextField customerId_txt;

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
    private TextField customerCountry_txt;
    
    @FXML
    private TextField customerAddressId_txt;

    @FXML
    private Button save_btn;

    @FXML
    private Button cancel_btn;

    //Takes User to Manage Customers Screen
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
    //Saved Updated Customer after checking exception control
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

        Query.updateCustomer(Integer.parseInt(customerId_txt.getText()), customerName_txt.getText(), customerPhone_txt.getText(), Integer.parseInt(customerAddressId_txt.getText()), selectedItem, customerZip_txt.getText(), customerAddress_txt.getText(), LoginController.currentUser, LoginController.currentUser);
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ManageCustomers.fxml"));
        loader.load();

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
        }
    }
    
    //Sets data for passing into FXML Form
    public void retrieveCustomer(customer customer) {
        
           
        customerId_txt.setText(Integer.toString(customer.getCustomerId()));
        customerName_txt.setText(customer.getCustomerName());
        customerAddress_txt.setText(customer.getAddress());
        customerPhone_txt.setText(customer.getCustomerPhone());
        city_combo.setValue(customer.getCity());
        customerZip_txt.setText(customer.getPostalCode());
        customerCountry_txt.setText(customer.getCountry());
        customerAddressId_txt.setText(Integer.toString(customer.getAddressId()));
        
    }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Sets combo box data for the Update Customer Screen
        ObservableList<String> cityData = Query.getCityList();
        
        city_combo.setItems(cityData);
    }    

    
}
