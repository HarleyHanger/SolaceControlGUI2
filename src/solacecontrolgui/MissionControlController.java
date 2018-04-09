/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solacecontrolgui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import static javax.imageio.ImageIO.getCacheDirectory;
import javax.swing.SwingUtilities;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Harley
 */
public class MissionControlController implements Initializable {
    
   
    String fileToLoad;    
    //Home Page 
    
 
   
    @FXML
    private Label fileLoadedName;
   
   // start Latitude Spinner Degree
    @FXML
    private Spinner<Double> startLatSpinner;
    
    // start Longitude Spinner Degree
    @FXML
    private Spinner<Double> startLonSpinner;
    

    

    // Latitude spinners
    @FXML
   private Spinner<Double> wayPointLat1, wayPointLat2, wayPointLat3 , wayPointLat4, endLatitude ; 
    // longitude spinners
    @FXML
    private Spinner<Double> wayPointLong1, wayPointLong2, wayPointLong3 , wayPointLong4 , endLongitude ; 
    
    @FXML
    private Label wayLatLabel2,wayLatLabel3 , wayLatLabel4 ;
    
    @FXML
    private Label wayLongLabel2, wayLongLabel3 , wayLongLabel4 ;
    
    //for file loader
    List<String> lstFile;
    
    Formatter x;
    
    FileChooser fc = new FileChooser();
    JSONParser parser = new JSONParser();
    @FXML
    private AnchorPane bg;
    @FXML
    private VBox headerBar;

 
    
    private int way1 = 1, way2 = 1, way3 = 1 , way4 = 1;
      
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Make spinner manually editable
        startLatSpinner.setEditable(true);
        startLatSpinner.setEditable(true);
        startLonSpinner.setEditable(true);
        wayPointLat1.setEditable(true);
        wayPointLong1.setEditable(true);
        wayPointLat2.setEditable(true);
        wayPointLong2.setEditable(true);
        wayPointLat3.setEditable(true);
        wayPointLong3.setEditable(true);
        wayPointLat4.setEditable(true);
        wayPointLong4.setEditable(true);
        endLatitude.setEditable(true);
        endLongitude.setEditable(true);
        
        // Set spinners values to blank
        startLatSpinner.getEditor().setText("");
        startLonSpinner.getEditor().setText("");
        wayPointLat1.getEditor().setText("");
        wayPointLong1.getEditor().setText("");
        wayPointLat2.getEditor().setText("");
        wayPointLong2.getEditor().setText("");
        wayPointLat3.getEditor().setText("");
        wayPointLong3.getEditor().setText("");
        wayPointLat4.getEditor().setText("");
        wayPointLong4.getEditor().setText("");
        endLatitude.getEditor().setText("");
        endLongitude.getEditor().setText("");
        
        
        

        
               // change this too allow more files to be loaded
        lstFile= new ArrayList<>();
        lstFile.add("*.json");
    }    
    
    @FXML
  private void loadConfig(ActionEvent event) {
        // when the load button is pressed
        
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Json Files" , lstFile));
        File f = fc.showOpenDialog(null);
        // if file isnt empty
        if (f!= null)
        {
           // try loading the details from the json file into 
            try {
                System.out.println("loadedfile" + f.getAbsolutePath());
                fileToLoad = f.getAbsolutePath();
                
                fileLoadedName.setText("file Loaded:"  + fileToLoad);
                
                JSONArray a = (JSONArray) parser.parse(new FileReader(fileToLoad));
               
                for (Object o : a)
                {
                    JSONObject mission = (JSONObject) o;
                    
                    //Start latitude Spinner
                    String latDegree = (String) mission.get("startLatitude" + "");
                    System.out.println(latDegree);
                    startLatSpinner.getEditor().setText(latDegree);
                    
                    
                    
                    //Start Longitude Spinner
                    String lonDegree = (String) mission.get("startLongitude" + "");
                    System.out.println(lonDegree);
                    startLonSpinner.getEditor().setText(lonDegree);
                    
                    // waypoint 1 Longitude Spinner
                    String waypoint1LatDegree = (String) mission.get("wayPointLat1" + "");
                    System.out.println(waypoint1LatDegree);
                    wayPointLat1.getEditor().setText(waypoint1LatDegree);
                    
                    
                    // waypoint 1 Longitude Spinner
                    String waypoint1LongDegree = (String) mission.get("wayPointLong1" + "");
                    System.out.println(waypoint1LongDegree);
                    wayPointLong1.getEditor().setText(waypoint1LongDegree);
                    
                    //waypoint2 latitude Spinner
                    String waypoint2LatDegree = (String) mission.get("wayPointLat2" + "");
                    System.out.println(waypoint2LatDegree);
                    wayPointLat2.getEditor().setText(waypoint2LatDegree);
                    
                    
                    //waypoint2 latitude Spinner
                    String waypoint2LongDegree = (String) mission.get("wayPointLong2" + "");
                    System.out.println(waypoint2LongDegree);
                    wayPointLong2.getEditor().setText(waypoint2LongDegree);
                    
                    
                    //waypoint3 latitude Spinner
                    String waypoint3LatDegree = (String) mission.get("wayPointLat3" + "");
                    System.out.println(waypoint3LatDegree);
                    wayPointLat3.getEditor().setText(waypoint3LatDegree);
                    
                    
                   //waypoint3 longitude Spinner
                    String waypoint3LongDegree = (String) mission.get("wayPointLong3" + "");
                    System.out.println(waypoint3LongDegree);
                    wayPointLong3.getEditor().setText(waypoint3LongDegree);
                      
                
                    //waypoint4 latitude Spinner
                    String waypoint4LatDegree = (String) mission.get("wayPointLat4" + "");
                    System.out.println(waypoint4LatDegree);
                    wayPointLat4.getEditor().setText(waypoint4LatDegree);
                    
                    
                    //waypoint4 longitude Spinner
                    String waypoint4LongDegree = (String) mission.get("wayPointLong4" + "");
                    System.out.println(waypoint4LongDegree);
                    wayPointLong4.getEditor().setText(waypoint4LongDegree);
                      
                
                     //end latitude Spinner
                    String endLatDegree = (String) mission.get("endLatitude" + "");
                    System.out.println(endLatDegree);
                    endLatitude.getEditor().setText(endLatDegree);
                    
                    
                      //end latitude Spinner
                    String endLongDegree = (String) mission.get("endLongitude" + "");
                    System.out.println(endLongDegree);
                    endLongitude.getEditor().setText(endLongDegree);
                      
                    
                   
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    //int latMinutes = (int) mission.get("startLatMinutes");
                    //System.out.println(latMinutes);
                    
                    //int latDirection = (int) mission.get("startLatDirection");
                    //System.out.println(latDirection);
                    
                    
                }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
           
           
        }
        else {
             fileLoadedName.setText("file was not selected or is invalid");
        }
        
    }
  
   
    @FXML
  private void setEndValue(ActionEvent event) {
       System.out.println(" Start lat + " + startLatSpinner.getEditor().getText());
       System.out.println(" Start Lon + " + startLonSpinner.getEditor().getText());
       
       String startLat = startLatSpinner.getEditor().getText();
       String startLong = startLonSpinner.getEditor().getText();
       
       endLatitude.getEditor().setText(startLat);
       endLongitude.getEditor().setText(startLong);
       
      
  
  }
     @FXML
  private void way1Disable(ActionEvent event) {

      
       switch (way1) {
            case 1:  wayPointLat1.setDisable(true);
                     wayPointLong1.setDisable(true);
                     way1 = 2;
                     System.out.println("Way point 1 Disabled");
                     break;
            case 2:  wayPointLat1.setDisable(false);
                     wayPointLong1.setDisable(false);
                     way1 = 1;
                     wayPointLat1.getEditor().setText("");
                     wayPointLong1.getEditor().setText("");
                     System.out.println("Way point 1 Enabled");
                     break;
                              
      }          
  }
      
    @FXML
  private void way2Disable(ActionEvent event) {
             switch (way2) {
            case 1:  wayPointLat2.setDisable(true);
                     wayPointLong2.setDisable(true);
                     way2 = 2;
                     System.out.println("Way point 2 Disabled");
                     break;
            case 2:  wayPointLat2.setDisable(false);
                     wayPointLong2.setDisable(false);
                     way2 = 1;
                     System.out.println("Way point 2 Enabled");
                      wayPointLat2.getEditor().setText("");
                      wayPointLong2.getEditor().setText("");
                     break;
                              
      }          
       
    
  }
      
          @FXML
  private void way3Disable(ActionEvent event) {
              switch (way3) {
            case 1:  wayPointLat3.setDisable(true);
                     wayPointLong3.setDisable(true);
                     way3 = 2;
                     System.out.println("Way point 3 Disabled");
                     break;
            case 2:  wayPointLat3.setDisable(false);
                     wayPointLong3.setDisable(false);
                     way3 = 1;
                     System.out.println("Way point 3 Enabled");
                     wayPointLat3.getEditor().setText("");
                     wayPointLong3.getEditor().setText("");
                     break;
                              
      }          
    
  }
      
          @FXML
  private void way4Disable(ActionEvent event) {
       
            switch (way4) {
            case 1:  wayPointLat4.setDisable(true);
                     wayPointLong4.setDisable(true);
                     way4 = 2;
                     System.out.println("Way point 4 Disabled");
                     break;
            case 2:  wayPointLat4.setDisable(false);
                     wayPointLong4.setDisable(false);
                     way4 = 1;
                     System.out.println("Way point 4 Enabled");
                     wayPointLat4.getEditor().setText("");
                     wayPointLong4.getEditor().setText("");
                     break;
                              
      }          
      
       
      
  
  }
  @FXML
    private void saveConfig(ActionEvent event){
               
         Node source = (Node) event.getSource();
         Window theStage = source.getScene().getWindow();
   
// ...
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Image");
            System.out.println("saving Image");
            File f = fileChooser.showSaveDialog(theStage);
              
            if (f!= null)
        {
            System.out.println(f.getAbsolutePath() + ".json");
        
        File output = new File(f.getAbsolutePath()+".json");
        FileWriter writer;
        try{
            writer = new FileWriter(output);
            BufferedWriter buf = new BufferedWriter(writer);
            
            buf.write("working file writer !\n");
            buf.write("the file path is :" + output.getAbsolutePath());
            
            buf.close();
            System.out.println(" file printed ");
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    }
    
 
        
    @FXML
    private void runConfig(ActionEvent event) {
        
        System.out.println("Run config Pressed" );
        
        // the if statement below reads the text values from the spinners and checks to see if they are blank.
        // if these values are blank and the way point is still active an error will be prompted to the user 
        if(startLatSpinner.getEditor().getText().equals("") 
                //checking the waypoints for latitude here
                || wayPointLat1.getEditor().getText().equals("") && way1 == 1
                || wayPointLat2.getEditor().getText().equals("") && way2 == 1
                || wayPointLat3.getEditor().getText().equals("") && way3 == 1
                || wayPointLat4.getEditor().getText().equals("") && way4 == 1
                // end of checking waypoints for lat
                || endLatitude.getEditor().getText().equals("")
                || startLonSpinner.getEditor().getText().equals("") 
                // check the way points for longtitude
                || wayPointLong1.getEditor().getText().equals("") && way1 == 1
                || wayPointLong2.getEditor().getText().equals("") && way2 == 1
                || wayPointLong3.getEditor().getText().equals("") && way3 == 1
                || wayPointLong4.getEditor().getText().equals("") && way4 == 1
                // end of checking waypoints for long
                || endLongitude.getEditor().getText().equals("")
                // end of if statement
                ){
                    // Alert telling the user that a field has been missed
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Missing Field");
                    alert.setHeaderText("A Field is Empty");
                    String s ="Please Enter Missing Field";
                    alert.setContentText(s);
                    alert.show();
                    System.out.println("Box is empty");

                }
                
        // this else if statement runs if there where no previous errors it then checks to see if the values are between 90 & -90 for latitude and if the waypoints are active or not 
        else if(Double.parseDouble(startLatSpinner.getEditor().getText()) > 90 || Double.parseDouble(startLatSpinner.getEditor().getText())< -90
                || way1 == 1 && Double.parseDouble(wayPointLat1.getEditor().getText()) > 90 || way1 == 1 && Double.parseDouble(wayPointLat1.getEditor().getText())< -90
                || way2 == 1 && Double.parseDouble(wayPointLat2.getEditor().getText()) > 90 || way2 == 1 && Double.parseDouble(wayPointLat2.getEditor().getText())< -90
                || way3 == 1 && Double.parseDouble(wayPointLat3.getEditor().getText()) > 90 || way3 == 1 && Double.parseDouble(wayPointLat3.getEditor().getText())< -90
                || way4 == 1 && Double.parseDouble(wayPointLat4.getEditor().getText()) > 90 || way4 == 1 && Double.parseDouble(wayPointLat4.getEditor().getText())< -90
                || Double.parseDouble(endLatitude.getEditor().getText()) > 90 || Double.parseDouble(endLatitude.getEditor().getText())< -90 
                ){
                    //Alert telling the user if there was a latitude error
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Latitude Invalid");
                    alert.setHeaderText("Latitude Input Invalid Error");
                    String s ="One or more of the latitudes did not range between 90  and - 90";
                    alert.setContentText(s);
                    alert.show();

                    System.out.println("Lat Failed");
                }
         // this else if statement runs if there where no previous errors it then checks to see if the values are between 180 & -180 for the longitude and if the waypoints are active or not 
        else if(Double.parseDouble(startLonSpinner.getEditor().getText()) > 180 || Double.parseDouble(startLonSpinner.getEditor().getText())< -180
                || way1 == 1 && Double.parseDouble(wayPointLong1.getEditor().getText()) > 180 || way1 == 1 && Double.parseDouble(wayPointLong1.getEditor().getText())< -180
                || way2 == 1 && Double.parseDouble(wayPointLong2.getEditor().getText()) > 180 || way2 == 1 && Double.parseDouble(wayPointLong2.getEditor().getText())< -180
                || way3 == 1 && Double.parseDouble(wayPointLong3.getEditor().getText()) > 180 || way3 == 1 && Double.parseDouble(wayPointLong3.getEditor().getText())< -180
                || way4 == 1 && Double.parseDouble(wayPointLong4.getEditor().getText()) > 180 || way4 == 1 && Double.parseDouble(wayPointLong4.getEditor().getText())< -180
                || Double.parseDouble(endLongitude.getEditor().getText()) > 180 || Double.parseDouble(endLongitude.getEditor().getText())< -180 
                ){
                    // Alert the user if there was an error with the longitude 
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Longitude Invalid");
                    alert.setHeaderText("Longitude Input Invalid Error");
                    String s ="One or more of the Longitudes did not range between 180  and - 180";
                    alert.setContentText(s);
                    alert.show();

                    System.out.println("Long Failed");
            
                }
        // if there are no errors then send the values.
        else{
                System.out.println(" Start lat + " + startLatSpinner.getEditor().getText());
                System.out.println(" Start Lon + " + startLonSpinner.getEditor().getText());
                // the following if statements check to see if the waypoints are active or not.
                // waypoint 1
                if(way1 == 1){
                    System.out.println(" WayPoint 1 + " + wayPointLat1.getEditor().getText());
                    System.out.println(" Waypoint 1 + " + wayPointLong1.getEditor().getText());
                }
                // waypoint 2
                if(way2 == 1){
                    System.out.println(" Waypoint 2  + " + wayPointLat2.getEditor().getText());
                    System.out.println(" Waypoint 2  + " + wayPointLong2.getEditor().getText());
                }
                // waypoint 3
                if(way3 == 1){
                    System.out.println(" Waypoint 3  + " + wayPointLat3.getEditor().getText());
                    System.out.println(" Waypoint 3  + " + wayPointLong3.getEditor().getText());    
                }
                // waypoint 4
                if(way4 == 1){
                    System.out.println(" Waypoint 4  + " + wayPointLat4.getEditor().getText());
                    System.out.println(" Waypoint 4  + " + wayPointLong4.getEditor().getText());    
                }
                System.out.println(" End Lat  + " + endLatitude.getEditor().getText());
                System.out.println(" End Long  + " + endLongitude.getEditor().getText());
            }

        
                  
    }
   
}

