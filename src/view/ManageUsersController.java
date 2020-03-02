/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
import model.user;
import utilities.DBConnection;
import utilities.Query;

/**
 * FXML Controller class
 *
 * @author bradharr
 */
public class ManageUsersController implements Initializable {

    //Sets Stage & Scene
    Stage stage;
    Parent scene;
    
    //FXML ID Data
    @FXML
    private Button addUser_btn;

    @FXML
    private Button updateUser_btn;

    @FXML
    private Button deleteUser_btn;

    @FXML
    private Button mainMenu_btn;

    @FXML
    private TableView<user> userTableView;

    @FXML
    private TableColumn<user, String> userName_col;

    @FXML
    private TableColumn<user, String> password_col;

    @FXML
    private TableColumn<user, Integer> active_col;

    //Takes User to Add User Screen
    @FXML
    void addNewUser(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/AddUser.fxml"));
            loader.load();

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
    }

    //Deletes Selected User
    @FXML
    void deleteUser(ActionEvent event) {
        
        int selectedUserId = 0;
        
        if(userTableView.getSelectionModel().isEmpty()) {
            return;
        }
        else {
            selectedUserId = userTableView.getSelectionModel().getSelectedItem().getUserId();
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning!");
        alert.setHeaderText("This will delete the selected User and all appointments associated with this User.");
        alert.setContentText("Would you like to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Query.deleteAppointmentByUser(selectedUserId);
            Query.deleteUser(selectedUserId, null, null, 0, null, null, null, null);
        }
        
    }
    //Returns User to the Main Menu
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

    //Takes User to the Update User Screen
    @FXML
    void updateUser(ActionEvent event) throws IOException {
       
        user user = userTableView.getSelectionModel().getSelectedItem();
        if(user == null)
        return;
        
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/UpdateUser.fxml"));
            loader.load();
            
            UpdateUserController UserController = loader.getController();
            UserController.retrieveUser(user);
 
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        
    }
    
    
    //ObservableList for User
    private ObservableList<user>data;

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Sets initial TableView data
        try {
        DBConnection.startConnection();
        data = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM user";
        Query.makeQuery(sqlStatement);
        
        ResultSet result = Query.getResult();
        
        
        while(result.next()) {
            
           data.add(new user(result.getInt("userId"), result.getString("userName"), result.getString("password"),
                              result.getInt("active"), result.getString("createDate"), result.getString("createdBy"),
                              result.getString("lastUpdate"), result.getString("lastUpdateBy")));
            
            userName_col.setCellValueFactory(new PropertyValueFactory<>("userName"));
            password_col.setCellValueFactory(new PropertyValueFactory<>("password"));
            active_col.setCellValueFactory(new PropertyValueFactory<>("active"));
            
            userTableView.setItems(null);
            userTableView.setItems(data);
        }
    
    }
    catch(Exception ex) {
     System.out.println(ex.getMessage());
    }
    }
}
