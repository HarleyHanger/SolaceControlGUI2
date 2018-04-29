/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solacecontrolgui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Harley
 */
public class HomePageController implements Initializable {
    
   
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
        
        // sets up the stage
        Parent parent = FXMLLoader.load(getClass().getResource("/solacecontrolgui/MissionControl.fxml"));
        Stage stage = new Stage();
        stage.setMaximized(true);
        //stage.initModality(Modality.APPLICATION_MODAL);
        //stage.initStyle(StageStyle.UNDECORATED);
        
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Mission Control");
        stage.show();

        // Add one second before hiding the Homepage
        TimeUnit.SECONDS.sleep(1);
        ((Node)(event.getSource())).getScene().getWindow().hide();
}

    
    
        @FXML
    private void userGuideBtn(ActionEvent event) throws IOException {
        
        // Run a PDF File
      try {

          File file = new File(System.getProperty("user.dir") +"\\src\\solacecontrolgui\\pdf.pdf");
          System.out.println(file);
		if (file.exists()) {

			Process p = Runtime
			   .getRuntime()
			   .exec("rundll32 url.dll,FileProtocolHandler " + file);
			p.waitFor();
				
		} else {

			System.out.println("PDF File Doesnt exist");

		}

		System.out.println("Done");

  	  } catch (Exception ex) {
		ex.printStackTrace();
	  }

    }
    
       @FXML
    private void aboutBtn(ActionEvent event) throws IOException {
        
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("About");
                    alert.setHeaderText("Solace Control GUI");
                    String s ="Created By Harley Hanger \nVersion 1.0 \n© Copyright 2018 © ";
                    alert.setContentText(s);
                    alert.show();
                    
                    
        
   
    }
    
    @FXML
    private void exitBtn(ActionEvent event) throws Exception{
              Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("GUI Exit Button Pressed");
                    alert.setHeaderText("Close GUI Button Pressed");
                    String s ="Are you sure you want to Exit?";
                    alert.setContentText(s);
                    alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                     System.exit(0);
                      }
                    });
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
 
    }
}
