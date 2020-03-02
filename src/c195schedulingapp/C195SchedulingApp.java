/*
 *Bradley Harr
 *Student ID: 000931061
 *C195 Software II
 *Scheduling Application - Java
 */
package c195schedulingapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author bradharr
 */
public class C195SchedulingApp extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        //Show Login Screen
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
