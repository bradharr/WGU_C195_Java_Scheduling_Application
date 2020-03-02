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
import model.ApptRpt;
import utilities.Query;

/**
 * FXML Controller class
 *
 * @author bradharr
 */
public class Report1Controller implements Initializable {
   
    //Sets Stage & Scene
    Stage stage;
    Parent scene;
    
    //FXML ID Data
    @FXML
    private Label title_lbl;

    @FXML
    private TableView<ApptRpt> report1TableView;

    @FXML
    private TableColumn<ApptRpt, String> month_col;

    @FXML
    private TableColumn<ApptRpt, String> type_col;

    @FXML
    private TableColumn<ApptRpt, String> count_col;

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
        //Sets Report 1 TableView data
        ObservableList<ApptRpt> apptRptData = Query.getRpt1();
        
        month_col.setCellValueFactory(new PropertyValueFactory<>("month"));
        type_col.setCellValueFactory(new PropertyValueFactory<>("type"));
        count_col.setCellValueFactory(new PropertyValueFactory<>("total"));
        
        report1TableView.setItems(apptRptData);
        
    }    

}
