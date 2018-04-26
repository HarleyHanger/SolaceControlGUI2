/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solacecontrolgui;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import org.json.simple.parser.JSONParser;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;


/**
 * FXML Controller class
 *
 * @author Harley
 */
public class MappingTestController implements Initializable {

    JXMapViewer mapViewer = new JXMapViewer();
    
    @FXML
    private TextField active, windSpeed, windApparent, windAbsolute, heading , positionLat, positionLon;
    
    @FXML
    private Label arrayHere;
   JSONParser parser = new JSONParser();
   
   @FXML 
    private SwingNode swingNode;
   

 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       // Stage stage = (Stage) swingNode.getScene().getWindow();
        
        

        // Create a TileFactoryInfo for OpenStreetMap
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        // Use 8 threads in parallel to load the tiles
        tileFactory.setThreadPoolSize(8);

        // Set the focus
        GeoPosition frankfurt = new GeoPosition(50.11, 8.68);

        mapViewer.setZoom(7);
        mapViewer.setAddressLocation(frankfurt);

        
         createAndSetSwingMap(swingNode);
         
         final String dir = System.getProperty("user.dir");
                System.out.println("current dir = " + dir);
        
    
      
        
        
       
        

     
        
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
                
                JSONArray jsonarray = new JSONArray("[" +inputLine + "]");   
                for (int i = 0; i < jsonarray.length(); i++){
                    JSONObject obj = jsonarray.getJSONObject(i);
                    
                    String activeOutput = obj.getString("active");
                    String speedOutput = obj.getString("wind");
                    String[] wind = speedOutput.split(":");
                    String speed = wind[2]; 
                    String apparent = wind[3];
                    String absolute = wind[1];
                     
                   // String aparentOutput = obj.getString("apparent");
                    //String absoluteOutput = obj.getString("absolute");
                    String headingOutput = obj.getString("heading");
                    String positionOutput = obj.getString("position");
                    String[] position = positionOutput.split(",");
                    String lat = position[0]; 
                    String lon = position[1];
                    
                    
                    //System.out.println(activeOutput);
                   // System.out.println(speedOutput);
                   // System.out.println(aparentOutput);
                   // System.out.println(absoluteOutput);
                    System.out.println("speed" + speedOutput);
                    
                    
                    System.out.println("lat" + lat + "long"+ lon);
                   
                    
                    
                    
                    
                      
                // Replace Chars from latitude and longitude
               lat = lat.replace("[", "");  lon = lon.replace("]", "");
               // Remove Extras charecters from the speed
               speed = speed.replace("apparent", ""); speed = speed.replace(",", ""); speed = speed.replace("\"", "");
               // Remove from apparent
               apparent = apparent.replace ("}","");
               // Remove from absolute
               absolute = absolute.replace (",",""); absolute = absolute.replace ("speed",""); absolute = absolute.replace ("\"",""); 
               
                                
                active.setText(activeOutput);
                windSpeed.setText(speed);
                windApparent.setText(apparent);
                windAbsolute.setText(absolute);
                heading.setText(headingOutput);
                positionLat.setText(lat);
                positionLon.setText(lon);
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
    public void createAndSetSwingMap(final SwingNode swingNode) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                swingNode.setContent(mapViewer);
            }
        });
    }
}
