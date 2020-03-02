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
import model.user;
import utilities.Query;

/**
 * FXML Controller class
 *
 * @author bradharr
 */
public class UpdateUserController implements Initializable {
    
    //Sets Stage & Scene
    Stage stage;
    Parent scene;

    //FXML ID Data
    @FXML
    private TextField userId_txt;
    
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
    
    //Saves Updated User
    @FXML
    private void saveUser(ActionEvent event) throws IOException {
        
       int activeUser;
       if(active_ckbx.isSelected()) {
           activeUser = 1;
       }
       else {
           activeUser = 0;
       }
       
       
       Query.updateUser(Integer.parseInt(userId_txt.getText()), userName_txt.getText(), password_txt.getText(), activeUser, null, null, null, LoginController.currentUser);
       
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ManageUsers.fxml"));
        loader.load();

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
        
    }
    //Takes User back to Manage Users screen
    @FXML
    private void cancel(ActionEvent event) throws IOException {
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
     * @param user
     */
    
    //Sets data for the FXML Form
    public void retrieveUser(user user) {
        
        userId_txt.setText(Integer.toString(user.getUserId()));
        userName_txt.setText(user.getUserName());
        password_txt.setText(user.getPassword());
        
        if(user.getActive() == 1) {
        active_ckbx.setSelected(true);
        }
        else {
        active_ckbx.setSelected(false);
        }
        
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
    }    

    
}
