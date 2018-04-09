/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solacecontrolgui;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author Harley
 */
public class HomePageController implements Initializable {
    
    @FXML
    private Label timer;
    // Home button 1 , 2 ,3 
    @FXML
    private Button btn1;
    
    @FXML
    private Button btn2;
       
    @FXML
    private Button btn3;
    
    @FXML
    private ImageView homeBtn;
    
    @FXML
    private Label breadcrumb;
    
    private int minute;
    private int hour;
    private int second;
    
       
    @FXML
    private void missionControlBtn(ActionEvent event) throws Exception{
        // hides previous window
        ((Node)(event.getSource())).getScene().getWindow().hide();
        // sets up the stage
        Parent parent = FXMLLoader.load(getClass().getResource("/solacecontrolgui/MissionControl.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Mission Control");
        stage.show();

}

        @FXML
    private void TestBtn(ActionEvent event) throws Exception{
        // hides previous window
        ((Node)(event.getSource())).getScene().getWindow().hide();
        // sets up the stage
        Parent parent = FXMLLoader.load(getClass().getResource("/solacecontrolgui/MappingTest.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Mission Control");
        stage.show();

}
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
      DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd - HH:mm:ss");
    //get current date time with Date()
    Date date = new Date();
    System.out.println(dateFormat.format(date));
 
    //get current date time with Calendar()
    Calendar cal = Calendar.getInstance();
    System.out.println(dateFormat.format(cal.getTime()));
    timer.setText(dateFormat.format(cal.getTime()));
    
     
        
    }
}
