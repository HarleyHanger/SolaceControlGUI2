/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solacecontrolgui;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * FXML Controller class
 *
 * @author Harley
 */
public class MappingTestController implements Initializable {

    
    @FXML
    private TextField active, windSpeed, windApparent, windAbsolute, heading , position;
    
    @FXML
    private Label arrayHere;
   JSONParser parser = new JSONParser();
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
         
    }
    @FXML
    private void retriveData (ActionEvent ae) throws JSONException {
        System.out.println("Searched");
        
        try {         
            URL boatD = new URL("http://52.232.121.121:3333/boat"); // URL to Parse
            URLConnection yc = boatD.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            
            String inputLine;
            while ((inputLine = in.readLine()) != null) {    
                System.out.println("in" + in);
                System.out.println("il" + inputLine);
                arrayHere.setText(inputLine);
                
                JSONArray jsonarray = new JSONArray(inputLine);   
                for (int i = 0; i < jsonarray.length(); i++){
                    JSONObject obj = jsonarray.getJSONObject(i);
                    
                    String activeOutput = obj.getString("active");
                    String speedOutput = obj.getString("speed");
                    String aparentOutput = obj.getString("apparent");
                    String absoluteOutput = obj.getString("absolute");
                    String headingOutput = obj.getString("heading");
                    String positionOutput = obj.getString("position");
                    
                      
                // if ever needed
                //test = test.replaceAll("\\p{P}","");
                                
                active.setText(activeOutput);
                windSpeed.setText(speedOutput);
                windApparent.setText(aparentOutput);
                windAbsolute.setText(absoluteOutput);
                heading.setText(headingOutput);
                position.setText(positionOutput);
                //active.setText(activeOutput);
                

                
                    
                }
               /*
                JSONParser parser = new JSONParser();
                Object obj  = parser.parse(inputLine);
                JSONArray array = new JSONArray();
                array.add(obj);
                for( int i = 0 < array.length(); i++){
                    JSONObject jsonobject = array.getJSONObject(i);
                
                }
*/
                
              
                

    }
 }     catch (MalformedURLException ex) {
           Logger.getLogger(MappingTestController.class.getName()).log(Level.SEVERE, null, ex);
           
           System.out.println("hi");
       } catch (IOException ex) {
           Logger.getLogger(MappingTestController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("An Exception has be cought");
                    String s ="Is the BoatD Server Running?";
                    alert.setContentText(s);
                    alert.show();
                    // Make an error get saved here 
          
       }
    }
}
