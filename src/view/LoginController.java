/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utilities.DBConnection;
import utilities.Query;

/**
 *
 * @author bradharr
 */
public class LoginController implements Initializable {
    
    //Sets Stage and Scene
    Stage stage;
    Parent scene;
    
    //Variable to hold Current LoggedIn User Data
    public static String currentUser = null;
    
    //FXML ID Data
    @FXML
    private Label title_lbl;

    @FXML
    private Label username_lbl;

    @FXML
    private TextField username_txt;

    @FXML
    private Label password_lbl;

    @FXML
    private TextField password_txt;

    @FXML
    private Button login_btn;

    @FXML
    private Button cancel_btn;
    
    @FXML
    private Label error_lbl;
    
    @FXML
    private Label error_userpw_lbl;
    
    @FXML
    private Label error_title_lbl;
    
    //Exits the Program
    @FXML
    void cancel(ActionEvent event) {
        System.exit(0);
    }
    //Logs User into system after checking exception control
    @FXML
    void login(ActionEvent event) throws IOException, SQLException {
            
            ResourceBundle resource = ResourceBundle.getBundle("resources/Nat", Locale.getDefault());
            
            if(username_txt.getText().length() == 0 && password_txt.getText().length() == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(resource.getString(error_title_lbl.getId()));
                alert.setContentText(resource.getString(error_lbl.getId()));
                alert.showAndWait();
                return;
            }
            
            DBConnection.startConnection();
            String sqlStatement = "SELECT * FROM user";
            Query.makeQuery(sqlStatement);

            ResultSet result = Query.getResult();
            
            //Date object
            Date date= new Date();
            //getTime() returns current time in milliseconds
            long time = date.getTime();
            //Passed the milliseconds to constructor of Timestamp class 
            Timestamp ts = new Timestamp(time);
        
        try {
        while(result.next()) {
        
        
        if(username_txt.getText().equals(result.getString("userName"))) {
                if(password_txt.getText().equals(result.getString("password"))) {
                    if(result.getInt("active") == 1) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/view/MainMenu.fxml"));
                    loader.load();

                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    Parent scene = loader.getRoot();
                    stage.setScene(new Scene(scene));
                    stage.show();
                    currentUser = username_txt.getText();
                    Query.checkAppointments();
                    FileWriter fw = new FileWriter("loginaudit.txt", true);
                    fw.write(System.getProperty( "line.separator"));
                    fw.write(LoginController.currentUser + ", " + ts);
                    fw.close();
                    return;
            }
            }
        }
    }
        
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(error_title_lbl.getId());
        alert.setContentText(resource.getString(error_userpw_lbl.getId()));
        alert.showAndWait();
        
    }
        catch(Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    //Set Application Login Screen Language based on User's System Language (English or Spanish Only)
    public void setLanguage() {
        
        ResourceBundle resource = ResourceBundle.getBundle("resources/Nat", Locale.getDefault());
        
        if(Locale.getDefault().getLanguage().equals("en") ||
           Locale.getDefault().getLanguage().equals("es")) {
        title_lbl.setText(resource.getString(title_lbl.getId()));
        username_lbl.setText(resource.getString(username_lbl.getId()));
        password_lbl.setText(resource.getString(password_lbl.getId()));
        login_btn.setText(resource.getString(login_btn.getId()));
        cancel_btn.setText(resource.getString(cancel_btn.getId()));
        }
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO    
        //Calls setLanguage() method
        setLanguage();
  
    }    
    
}
