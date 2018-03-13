/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solacecontrolgui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Harley
 */
public class FXMLDocumentController implements Initializable {
    
    
  
    String fileToLoad;    
    //Home Page 
    
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
    
    
    // Page 2
   @FXML
    private Label breadcrumb2;
   
   @FXML
    private Label fileLoadedName;
   
    //for file loader
    List<String> lstFile;
    
    
    
   

    
   @FXML
    private void missionControlBtn(ActionEvent event) {
        try {
                
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MissionControl.fxml"));
            
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            //set what you want on your stage
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Report Page");
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
}
      @FXML
    private void loadConfig(ActionEvent event) {
        
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new ExtensionFilter("Word Files" , lstFile));
        File f = fc.showOpenDialog(null);
        
        if (f!= null)
        {
           System.out.println("loadedfile" + f.getAbsolutePath()); 
           fileToLoad = f.getAbsolutePath(); 
           
           fileLoadedName.setText("file Loaded:"  + fileToLoad);
        }
        else {
             fileLoadedName.setText("file was not selected or is invalid");
        }
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // change this too allow more files to be loaded
        lstFile= new ArrayList<>();
        lstFile.add("*.json");
    }    
    
}
