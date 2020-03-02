/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.Query;

/**
 * FXML Controller class
 *
 * @author bradharr
 */
public class AddUserController implements Initializable {

    //Sets Stage and Scene
    Stage stage;
    Parent scene;
    
    //FXML ID Data
    @FXML
    private TextField userName_txt;

    @FXML
    private TextField password_txt;

    @FXML
    private CheckBox active_ckbx;

    @FXML
    private Button save_btn;

    @FXML
    private Button cancel_btn;
    
    //Returns user to Manage Users Screen
    @FXML
    void cancel(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ManageUsers.fxml"));
            loader.load();

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
    }

    //Saves new User to Database
    @FXML
    void saveUser(ActionEvent event) throws IOException {

        int activeUser;
        if (active_ckbx.isSelected()) {
            activeUser = 1;
        }
        else {
            activeUser = 0;
        }
        
        Query.insertNewUser(userName_txt.getText(), password_txt.getText(), activeUser, null, LoginController.currentUser, LoginController.currentUser);
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ManageUsers.fxml"));
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
    }    
}
