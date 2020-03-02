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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.CustRpt;
import utilities.Query;

/**
 * FXML Controller class
 *
 * @author bradharr
 */
public class Report3Controller implements Initializable {
    
    //Sets Stage & Scene
    Stage stage;
    Parent scene;

    //FXML ID Data
    @FXML
    private Label title_lbl;

    @FXML
    private TableView<CustRpt> report3TableView;

    @FXML
    private TableColumn<CustRpt, String> cust_col;

    @FXML
    private TableColumn<CustRpt, String> start_col;

    @FXML
    private TableColumn<CustRpt, String> end_col;

    @FXML
    private Button back_btn;

    //Returns User to the Reports Screen
    @FXML
    void returnReports(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Reports.fxml"));
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
        //Sets Report 3 TableView Data
        ObservableList<CustRpt> apptRptData = Query.getRpt3();
        
        cust_col.setCellValueFactory(new PropertyValueFactory<>("customer"));
        start_col.setCellValueFactory(new PropertyValueFactory<>("start"));
        end_col.setCellValueFactory(new PropertyValueFactory<>("end"));
        
        
        
        report3TableView.setItems(apptRptData);
        
    }    

    
}
