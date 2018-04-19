/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solacecontrolgui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



/**
 *
 * @author Harley
 */
public class FXMLDocumentController implements Initializable {
    
    

  public void start(Stage stage) throws Exception{
  Parent root = (Parent) FXMLLoader.load(getClass().getResource("/HomePage.fxml"));
  Scene scene = new Scene(root);
  stage.setScene(scene);
  stage.setTitle("Homepage");
  stage.show();
  }
    
   
  
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
    }    
    
}
