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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    
    // end Latitude spinner
    @FXML
    private Spinner<Double> endLatSpinner;
    
    // end Longitude Spinner Degree
    @FXML
    private Spinner<Double> endLonSpinner;
    
    // end Longitude Spinner Degree
    @FXML
    private Spinner<Double> vesselLengthSpinner;
    
    @FXML
    private Spinner<Double> vesselWeightSpinner;
    
    
    //for file loader
    List<String> lstFile;
    
    Formatter x;
    
    FileChooser fc = new FileChooser();
    JSONParser parser = new JSONParser();
    @FXML
    private AnchorPane bg;
    @FXML
    private VBox headerBar;

    
    
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
                    
                    //latitude Spinner
                    String latDegree = (String) mission.get("startLatDegree" + "");
                    System.out.println(latDegree);
                    double latDegreeDouble = Double.parseDouble(latDegree);
                    System.out.println(latDegreeDouble);
                    startLatSpinner.getValueFactory().setValue(latDegreeDouble);
                    
                    
                    // Longitude Spinner
                    String lonDegree = (String) mission.get("startLonDegree" + "");
                    System.out.println(lonDegree);
                    double lonDegreeDouble = Double.parseDouble(lonDegree);
                    System.out.println(lonDegreeDouble);
                    startLonSpinner.getValueFactory().setValue(lonDegreeDouble);
                    
                       //end latitude Spinner
                    String endLatDegree = (String) mission.get("endLatDegree" + "");
                    System.out.println(endLatDegree);
                    double endLatDegreeDouble = Double.parseDouble(endLatDegree);
                    System.out.println(endLatDegreeDouble);
                    endLatSpinner.getValueFactory().setValue(endLatDegreeDouble);
                    
                    
                    // Longitude Spinner
                    String endLonDegree = (String) mission.get("endLonDegree" + "");
                    System.out.println(endLonDegree);
                    double endLonDegreeDouble = Double.parseDouble(endLonDegree);
                    System.out.println(endLonDegreeDouble);
                    endLonSpinner.getValueFactory().setValue(endLonDegreeDouble);
                    
                    
                    // Length Spinner
                    
                    String vesselLength = (String) mission.get("vesselLength" + "");
                    System.out.println(vesselLength);
                    double vesselLengthDouble = Double.parseDouble(vesselLength);
                    System.out.println(vesselLengthDouble);
                    vesselLengthSpinner.getValueFactory().setValue(vesselLengthDouble);
                              
                                       
                    // Weight Spinner
                    
                    String vesselWeight = (String) mission.get("vesselWeight" + "");
                    System.out.println(vesselWeight);
                    double vesselWeightDouble = Double.parseDouble(vesselWeight);
                    System.out.println(vesselWeightDouble);
                    vesselWeightSpinner.getValueFactory().setValue(vesselWeightDouble);
                              
                    
                    
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
    private void saveConfig(ActionEvent event){
        File output = new File("output.json");
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
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
               // change this too allow more files to be loaded
        lstFile= new ArrayList<>();
        lstFile.add("*.json");
    }    
   
}

