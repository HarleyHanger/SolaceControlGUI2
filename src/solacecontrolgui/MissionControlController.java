/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solacecontrolgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.cache.FileBasedLocalCache;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;
import static solacecontrolgui.Sample3.updateWindowTitle;

/**
 *
 * @author Harley
 */
public class MissionControlController implements Initializable {
    
   @FXML 
    private AnchorPane ap;
   
    String fileToLoad;    
    
    //Mission Setup
    
    @FXML
    private Label fileLoadedName;
   
   // start Latitude & longitude Spinner Degree
    @FXML
    private Spinner<Double> startLatSpinner , startLonSpinner;
    
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
    private VBox headerBar;
    
    private ArrayList<String> waypointsLat = new ArrayList<String>();
    private ArrayList<String> waypointsLong = new ArrayList<String>();
    
    private int way1 = 1, way2 = 1, way3 = 1 , way4 = 1;
    
    // Mission Control Page
        
    @FXML
    private TextField active, windSpeed, windApparent, windAbsolute, heading , positionLat, positionLon;
    
    
    // Map Stuff
    @FXML
    private Label arrayHere;
    
    //  allows the map to be added
    JXMapViewer mapViewer = new JXMapViewer();
        
   @FXML 
   private SwingNode swingNode;
   

   
   @FXML
   private TabPane tabPane;
   @FXML 
    private Tab mcTab, mpTab;
   
   // error buttons
   @FXML
    private Button bActiveButton, commsBtn, startMBtn;
   
   private int commsBtnStatus = 0;
   private int bActiveBtnStatus = 0;
   
        double lati ;
        double longi ;
        int zoom;
   
        String lati2;
        String longi2;
        String zoom2;
        
        String lat;// = "50.579884";
        String lon;// = "-1.449383";
   
   List<Double> wayPointsLatMap = new ArrayList<>(Arrays.asList());
   List<Double> wayPointsLonMap = new ArrayList<>(Arrays.asList());
   
   List<Double> boatPositionLat = new ArrayList<>(Arrays.asList());
   List<Double> boatPositionLon = new ArrayList<>(Arrays.asList());

     Set<MyWaypoint> waypoints = new HashSet<MyWaypoint>();
   WaypointPainter<MyWaypoint> waypointPainter = new WaypointPainter<MyWaypoint>();
   
   Set<MyWaypoint> boatPosition = new HashSet<MyWaypoint>();
         WaypointPainter<MyWaypoint> waypointBoatPainter = new WaypointPainter<MyWaypoint>();
   
   File fileOutput;
   

      
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        startDisable(); 
        System.out.println( "Start UP LAT " + wayPointsLatMap);
        System.out.println( "Start UP Lon " + wayPointsLonMap);
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
        
       
        
        
        // Mapsetup
        
// Create a TileFactoryInfo for OpenStreetMap
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);

        // Setup local file cache
        File cacheDir = new File(System.getProperty("user.home") + File.separator + ".jxmapviewer2");
        tileFactory.setLocalCache(new FileBasedLocalCache(cacheDir, false));

        // Setup JXMapViewer
        
        mapViewer.setTileFactory(tileFactory);
        GeoPosition isleOfWight = new GeoPosition(50.579884, -1.449383);
        JLabel latLongLabel = new JLabel ("Lat long here");
        // Set the focus
        mapViewer.setZoom(9);
        mapViewer.setAddressLocation(isleOfWight);
        
        // Add interactions
        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseListener(new CenterMapListener(mapViewer));
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));
        mapViewer.addKeyListener(new PanKeyListener(mapViewer));

        // Add a selection painter
        SelectionAdapter sa = new SelectionAdapter(mapViewer);
        SelectionPainter sp = new SelectionPainter(sa);
        mapViewer.addMouseListener(sa);
        mapViewer.add(latLongLabel);
        mapViewer.addMouseMotionListener(sa);
        mapViewer.setOverlayPainter(sp);

         boatPositionLoop();
    
        
        mapViewer.addMouseListener(new MouseAdapter(){
     public void mouseClicked(MouseEvent e) {
            if(e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON3 && mcTab.isSelected() != true){
                // Disable the Start Button
                startDisable();
                
                java.awt.Point p = e.getPoint();
                GeoPosition geo = mapViewer.convertPointToGeoPosition(p);
                System.out.println("X:"+geo.getLatitude()+",Y:"+geo.getLongitude());
                
                //Add to the array list
                wayPointsLatMap.add(geo.getLatitude());
                wayPointsLonMap.add(geo.getLongitude());
               
              
                        
                // read the array do something everytime you go through the array 
		for (int i = 0; i < wayPointsLatMap.size(); i++) {
			System.out.println(wayPointsLatMap.get(i) + " array list " + i);
                        System.out.println(wayPointsLonMap.get(i) + " array list " + i);
                        
                waypoints.add(
                                  
                new MyWaypoint ( "        " + String.valueOf(i+1), Color.ORANGE, new GeoPosition((wayPointsLatMap.get(i)), (wayPointsLonMap.get(i))))
                //new MyWaypoint ( String.valueOf(i-1), Color.ORANGE, new GeoPosition((wayPointsLatMap.get(i-1)), (wayPointsLonMap.get(i-1))))
             
                );
                        
                       
                        
               
                        
                System.out.println(waypoints.toString());
        // Create a waypoint painter that takes all the waypoints
       
        waypointPainter.setWaypoints(waypoints);
        waypointPainter.setRenderer(new FancyWaypointRenderer());

        mapViewer.setOverlayPainter(waypointPainter);
		}
                     }
            else{
                
                System.out.println(" Mission Control is Selected");
             
                    
                    }
            }
                
     
     // end mouse click

         
                });
        
        // create map inside the SwingNode
        createAndSetSwingMap(swingNode);
        
       bActiveButton.setStyle("-fx-background-color: #008000");
       commsBtn.setStyle("-fx-background-color: #008000");

        
               // change this too allow more files to be loaded
        lstFile= new ArrayList<>();
        lstFile.add("*.json");
    }    

        
    
    @FXML
  private void loadConfig(ActionEvent event) {
        // when the load button is pressed
        fileOutput = null;
        
        // Enable Start Button
        startEnable();
        
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Json Files" , lstFile));
        File f = fc.showOpenDialog(null);
        
        fileOutput = f;
        
        // if file isnt empty
        if (f!= null)
        {
           // try loading the details from the json file 
            try {
                
            String content2 = new Scanner(fileOutput).useDelimiter("\\Z").next();
            System.out.println( "content 2" + content2 );
            String loadInputLine = content2;
            
            
           clearConfig();
           
           updateBoatMarker();
           
           fileLoadedName.setText("File Loaded: '"  + fileOutput + "'");
           

           
                org.json.JSONArray jsonarray = new org.json.JSONArray("[" + loadInputLine + "]");   
                for (int i = 0; i < jsonarray.length(); i++){
                    org.json.JSONObject obj = jsonarray.getJSONObject(i);
                    System.out.println(" input line " + loadInputLine);
                    
                    String waypointOutput = obj.getString("waypoints");
                    String[] position = waypointOutput.split(",");
                    int positionLength = position.length;
                    
                    for( int l=0; l < positionLength -1; l = l + 2){
                          // Used for boat position
                    String loadLat = position[l]; 
                    String loadLon = position[l+1];
                    loadLat = loadLat.replace("[", "");   loadLat = loadLat.replace("]", "");  
                    loadLon = loadLon.replace("]", ""); loadLon = loadLon.replace("[", "");
                    
                    wayPointsLatMap.add(Double.parseDouble(loadLat));
                    wayPointsLonMap.add(Double.parseDouble(loadLon));

                    System.out.println( "Waypoint Number: " + l + " read in values: " + loadLat + " + "+ loadLon);
                    }
                    for (int w = 0; w < wayPointsLatMap.size(); w++) {
			System.out.println(wayPointsLatMap.get(w) + " array list " + i);
                        System.out.println(wayPointsLonMap.get(w) + " array list " + i);
                        
                        waypoints.add(

                        new MyWaypoint ( "        " + String.valueOf(w), Color.ORANGE, new GeoPosition((wayPointsLatMap.get(w)), (wayPointsLonMap.get(w))))
                        //new MyWaypoint ( String.valueOf(i-1), Color.ORANGE, new GeoPosition((wayPointsLatMap.get(i-1)), (wayPointsLonMap.get(i-1))))

                        );

                        System.out.println(waypoints.toString());
                // Create a waypoint painter that takes all the waypoints

                waypointPainter.setWaypoints(waypoints);
                waypointPainter.setRenderer(new FancyWaypointRenderer());

                mapViewer.setOverlayPainter(waypointPainter);
                      
                             }   
   
            }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }  catch (JSONException ex) {
                Logger.getLogger(MissionControlController.class.getName()).log(Level.SEVERE, null, ex);
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
            // Clear fileOutput
            fileOutput = null;
            // Get the stage
            Node source = (Node) event.getSource();
            Window theStage = source.getScene().getWindow();
            // Create file chooser on the stage
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save File");
            File f = fileChooser.showSaveDialog(theStage);
              
            if (f!= null)
        {
            System.out.println(f.getAbsolutePath() + ".json");
        
        fileOutput = new File(f.getAbsolutePath()+".json");
        
        try{
            FileWriter writer;
            writer = new FileWriter(fileOutput);
            BufferedWriter buf = new BufferedWriter(writer);
            buf.write("{\n");
            buf.write(" \"waypoints\": [\n");
            
            for(int i = 1; i <= wayPointsLatMap.size(); )
            {
                if(i < wayPointsLatMap.size())
                {
                    buf.write("     [" + wayPointsLatMap.get(i-1) + ","  + wayPointsLonMap.get(i-1)+ "],\n");
                    
                    System.out.println(i);
                    i++;
                } 
                else 
                {
                System.out.println("end" + i);
                buf.write("     [" + wayPointsLatMap.get(i-1) + ","  + wayPointsLonMap.get(i-1)+ "] \n" );
                i++;
                }
            }
            buf.write("    ]\n");
            buf.write("}");
            buf.close();
            System.out.println(" file printed ");
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        startEnable();
    }
    }
    
     @FXML
    private void loadCurrentWaypoints(ActionEvent event) {
    
        startDisable();
       try {
           URL boatD = new URL("http://52.232.121.121:3333/waypoints"); // URL to Parse
           URLConnection yc = boatD.openConnection();
           BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
           
           
            String inputLine;
            
            
            while ((inputLine = in.readLine()) != null) {    
                wayPointsLatMap.clear();
                wayPointsLonMap.clear();

                org.json.JSONArray jsonarray = new org.json.JSONArray("[" +inputLine + "]");   
                for (int i = 0; i < jsonarray.length(); i++){
                    org.json.JSONObject obj = jsonarray.getJSONObject(i);
                    
            
                    String waypointOutput = obj.getString("waypoints");
                    String[] position = waypointOutput.split(",");
                    
                    // Used for boat position
                    lat = position[0]; 
                    lon = position[1];
                    
                    
                   
        
                    System.out.println("lat + " + lat + "long + " + lon);
                    String[] waypointsNumber = waypointOutput.split(",");
                    int positionLength = waypointsNumber.length;
                    
                    for( int l=0; l < positionLength -1; l = l + 2){
                          // Used for boat position
                    String loadLat = position[l]; 
                    String loadLon = position[l+1];
                    loadLat = loadLat.replace("[", "");   loadLat = loadLat.replace("]", "");  
                    loadLon = loadLon.replace("]", ""); loadLon = loadLon.replace("[", "");
                    
                    wayPointsLatMap.add(Double.parseDouble(loadLat));
                    wayPointsLonMap.add(Double.parseDouble(loadLon));

                    System.out.println( "Waypoint Number: " + l + " read in values: " + loadLat + " + "+ loadLon);
                    }
                    for (int w = 0; w < wayPointsLatMap.size(); w++) {
			System.out.println(wayPointsLatMap.get(w) + " array list " + i);
                        System.out.println(wayPointsLonMap.get(w) + " array list " + i);
                        
                        waypoints.add(

                        new MyWaypoint ( "        " + String.valueOf(w), Color.ORANGE, new GeoPosition((wayPointsLatMap.get(w)), (wayPointsLonMap.get(w))))
                        //new MyWaypoint ( String.valueOf(i-1), Color.ORANGE, new GeoPosition((wayPointsLatMap.get(i-1)), (wayPointsLonMap.get(i-1))))

                        );

                        System.out.println(waypoints.toString());
                // Create a waypoint painter that takes all the waypoints

                waypointPainter.setWaypoints(waypoints);
                waypointPainter.setRenderer(new FancyWaypointRenderer());

                mapViewer.setOverlayPainter(waypointPainter);
                      
        
    }
            }
    
    
       }
    }  catch (JSONException ex) {
           Logger.getLogger(MissionControlController.class.getName()).log(Level.SEVERE, null, ex);
       } catch (IOException ex) {
           Logger.getLogger(MissionControlController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    
    
    
    
    
 
        
    @FXML
    private void runConfig(ActionEvent event) {
    
        
        
       //try {
           //tabPane.getSelectionModel().select(mcTab);
           // tabPane.getSelectionModel().select(mpTab);
           
           /*
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
           // Clear the arrays
           waypointsLat.clear();
           waypointsLong.clear();
           
           //String postStatement = ("waypoints POST \n" +"                    { \n" + "                        \"waypoints\": [");
           
           String startLatPost = startLatSpinner.getEditor().getText();
           waypointsLat.add(startLatPost);
           String startLongPost = startLonSpinner.getEditor().getText();
           waypointsLong.add(startLongPost);
           System.out.println(" Start Lon + " + startLonSpinner.getEditor().getText());
           
           System.out.println(" Start lat + " + startLatSpinner.getEditor().getText());
           System.out.println(" Start Lon + " + startLonSpinner.getEditor().getText());
           // the following if statements check to see if the waypoints are active or not.
           // waypoint 1
           if(way1 == 1){
           String way1LatPost = wayPointLat1.getEditor().getText();
           waypointsLat.add(way1LatPost);
           String way1LongPost = wayPointLong1.getEditor().getText();
           waypointsLong.add(way1LongPost);
           System.out.println(" WayPoint 1 + " + wayPointLat1.getEditor().getText());
           System.out.println(" Waypoint 1 + " + wayPointLong1.getEditor().getText());
           
           
           }
           // waypoint 2
           if(way2 == 1){
           
           
           String way2LatPost = wayPointLat2.getEditor().getText();
           waypointsLat.add(way2LatPost);
           String way2LongPost = wayPointLong2.getEditor().getText();
           waypointsLong.add(way2LongPost);
           System.out.println(" Waypoint 2  + " + wayPointLat2.getEditor().getText());
           System.out.println(" Waypoint 2  + " + wayPointLong2.getEditor().getText());
           }
           // waypoint 3
           if(way3 == 1){
           
           String way3LatPost = wayPointLat3.getEditor().getText();
           waypointsLat.add(way3LatPost);
           String way3LongPost = wayPointLong3.getEditor().getText();
           waypointsLong.add(way3LongPost);
           
           System.out.println(" Waypoint 3  + " + wayPointLat3.getEditor().getText());
           System.out.println(" Waypoint 3  + " + wayPointLong3.getEditor().getText());
           }
           // waypoint 4
           if(way4 == 1){
           
           String way4LatPost = wayPointLat4.getEditor().getText();
           waypointsLat.add(way4LatPost);
           String way4LongPost = wayPointLong4.getEditor().getText();
           waypointsLong.add(way4LongPost);
           System.out.println(" Waypoint 4  + " + wayPointLat4.getEditor().getText());
           System.out.println(" Waypoint 4  + " + wayPointLong4.getEditor().getText());
           }
           
           String endLatPost = endLatitude.getEditor().getText();
           waypointsLat.add(endLatPost);
           String endLongPost = endLongitude.getEditor().getText();
           waypointsLong.add(endLongPost);
           
           System.out.println(" End Lat  + " + endLatitude.getEditor().getText());
           System.out.println(" End Long  + " + endLongitude.getEditor().getText());
           
           // PRINT OUT JSON
           System.out.println("waypoints POST \n" +"                    { \n" +"                        \"waypoints\": [");
           for (int i = 0; i < waypointsLat.size(); i++)
           System.out.println("                        [" + waypointsLat.get(i) + "," + waypointsLong.get(i) + "]");
           System.out.println("                        ] \n "+ "                    }" );
           
           // JSON PRINT out looks like below
           
           /* waypoints POST
           {
           "waypoints": [
           [startLatPost , startLongPost],
           [way1LatPost , way1LongPost],
           [way2LatPost , way2LongPost],
           [way3LatPost , way3LongPost],
           [way4LatPost , way4LongPost],
           [end , way4LongPost]
           */
           
           // }
           
           
           // Send Waypoints
           
           /*
           String query = "http://52.232.121.121:3333/waypoints";
           String json = "{" +
"  \"waypoints\": [" +
"    [10.0, 20.0]," +
"    [11.0, 21.0]," +
"    [24.0, 23.0]" +
"  ]" +
"}";
           
           URL url = new URL(query);
           HttpURLConnection conn = (HttpURLConnection) url.openConnection();
           conn.setConnectTimeout(5000);
           conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
           conn.setDoOutput(true);
           conn.setDoInput(true);
           conn.setRequestMethod("POST");
           
           OutputStream os = conn.getOutputStream();
           os.write(json.getBytes("UTF-8"));
           os.close();
           
           // read the response
           InputStream in = new BufferedInputStream(conn.getInputStream());
           String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
           JSONObject jsonObject = new JSONObject(result);
           
           
           in.close();
           conn.disconnect();
           
            System.out.println("waypoints sent" + json);
          
       } catch (MalformedURLException ex) {
           Logger.getLogger(MissionControlController.class.getName()).log(Level.SEVERE, null, ex);
       } catch (IOException ex) {
           Logger.getLogger(MissionControlController.class.getName()).log(Level.SEVERE, null, ex);
       }
           */
           
           try {
               
               System.out.println("saveOutput" + fileOutput);
               String content = new Scanner(fileOutput).useDelimiter("\\Z").next();
               System.out.println("content is " + content);

        URL url = new URL("http://52.232.121.121:3333/waypoints");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

      //String input = content;
        
        String input = content; /* "{\n" +
"  \"waypoints\": [\n" +
"    [0.0, 0.0],\n" +
"    [1.0, 1.0],\n" +
"    [2.0, 2.0]\n" +
"  ]\n" +
"}";
                */

        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        
        
        os.flush();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
            throw new RuntimeException("Failed : HTTP error code : "
                + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String output;
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {
            System.out.println(output);
        }

        conn.disconnect();

      } catch (MalformedURLException e) {

        e.printStackTrace();

      } catch (IOException e) {

        e.printStackTrace();

     }
           startDisable();
                  
    }

    private void retriveData (){
        //System.out.println("Searched");
        
        try {         
            URL boatD = new URL("http://52.232.121.121:3333/boat"); // URL to Parse
            URLConnection yc = boatD.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            
             
            String inputLine;
            
            while ((inputLine = in.readLine()) != null) {    
                // Comms Btn green if theres no error
                commsBtn.setStyle("-fx-background-color: #008000");
                commsBtnStatus = 0;
                //System.out.println("input line" + inputLine);
                //arrayHere.setText(inputLine);
                
                org.json.JSONArray jsonarray = new org.json.JSONArray("[" +inputLine + "]");   
                for (int i = 0; i < jsonarray.length(); i++){
                    org.json.JSONObject obj = jsonarray.getJSONObject(i);
                    
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
                    
                    // Used for boat position
                    lat = position[0]; 
                    lon = position[1];
                    
                    
                    //System.out.println(activeOutput);
                   // System.out.println(speedOutput);
                   // System.out.println(aparentOutput);
                   // System.out.println(absoluteOutput);
                    //System.out.println("speed" + speedOutput);
                   
                
                    
                    //System.out.println("lat" + lat + "long"+ lon);

                // Replace Chars from latitude and longitude
               lat = lat.replace("[", "");  lon = lon.replace("]", "");
               // Remove Extras charecters from the speed
               speed = speed.replace("apparent", ""); speed = speed.replace(",", ""); speed = speed.replace("\"", ""); //speed = speed.substring(3);
               // Remove from apparent
               apparent = apparent.replace ("}","");
               // Remove from absolute
               absolute = absolute.replace (",",""); absolute = absolute.replace ("speed",""); absolute = absolute.replace ("\"",""); 
               
               // If the user wishes to show more decimal places on the heading , absolute and speed add on 0's to the decimal format
               NumberFormat formatter = new DecimalFormat("#0.0");  
               Double speedDouble = Double.parseDouble(speed);
               Double absolouteDouble = Double.parseDouble(absolute);
               Double headingDouble = Double.parseDouble(headingOutput);
               
               // New strings are made so that the data printed to file will be more accurate
               String speed1 = formatter.format(speedDouble);
               String absolute1 = formatter.format(absolouteDouble);
               String headingOutput1 = formatter.format(headingDouble);
             
               // active.setText(activeOutput);
                windSpeed.setText(speed1);
                windApparent.setText(apparent);
                windAbsolute.setText(absolute1);
                heading.setText(headingOutput1);
                positionLat.setText(lat);
                positionLon.setText(lon);
                //active.setText(activeOutput);
                
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
                //get current date time with Date()
                Date date = new Date();
                System.out.println(dateFormat.format(date));

                //get current date time with Calendar()
                Calendar cal = Calendar.getInstance();
                System.out.println(dateFormat.format(cal.getTime()));
                
                dateFormat.format(cal.getTime());
          // Print all data recieved to file
                try{
               final String dir = System.getProperty("user.dir");
                FileWriter writer2;
                File recordedData = new File(dir+"\\RecordedData\\RunData.csv");
                // true allows the file writer to append files
                writer2 = new FileWriter(recordedData, true);
                BufferedWriter buf = new BufferedWriter(writer2);
                //w = csv.writer(f, delimiter = ',')          
                
                if(recordedData.exists() && recordedData.length() == 0 ){
                buf.write("Time and Date, Wind Speed , Wind Apparent , Absolute , Heading , Latitude , Longitude \n" );
                buf.write(dateFormat.format(cal.getTime()) + "," + speed + "," + apparent + "," + absolute + "," + headingOutput + "," + lat + "," + lon +"\n");
                buf.close();
                System.out.println(" Printed Stored Data ");
                }
                else if(recordedData.exists()) {
                 
                buf.write(dateFormat.format(cal.getTime()) + "," + speed + "," + apparent + "," + absolute + "," + headingOutput + "," + lat + "," + lon +"\n");
                buf.close();
                System.out.println(" Printed Stored Data ");    
                }
                } catch (IOException ex) {
           Logger.getLogger(MissionControlController.class.getName()).log(Level.SEVERE, null, ex);
       }
                
                if(mcTab.isSelected() == true)
                updateBoatMarker();
                
                    
                }

    }
 }     catch (MalformedURLException ex) {
           Logger.getLogger(MappingTestController.class.getName()).log(Level.SEVERE, null, ex);
           
           
       } catch (IOException ex) {
           commsBtnStatus = 1;
           commsBtn.setStyle("-fx-background-color: #FF0000");
           System.out.println("Is the Server running?");
           //Logger.getLogger(MappingTestController.class.getName()).log(Level.SEVERE, null, ex);
            
                    // Make an error get saved here 
          
       } catch (JSONException ex) {
             System.out.println("error3");
            Logger.getLogger(MissionControlController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("Loop Finished");
    }
    
       public void createAndSetSwingMap(final SwingNode swingNode) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                swingNode.setContent(mapViewer);
            
            }
        });
        

    }
       
       @FXML
    private void eStop(ActionEvent event){

        //Disable Start button
        startDisable();
        String EmergencyLat = boatPositionLat.toString().replace("]","");
        String EmergencyLon = boatPositionLon.toString().replace("[","");
       
        String inputString = "{\n" + "  \"waypoints\": [\n" +"    " + EmergencyLat + "," + EmergencyLon + "\n" +"  ]\n" + "}";
        clearConfig();
          try {

        URL url1 = new URL("http://52.232.121.121:3333/waypoints");
        HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
        conn1.setDoOutput(true);
        conn1.setRequestMethod("POST");
        conn1.setRequestProperty("Content-Type", "application/json");
   
        System.out.println("input2 " + inputString);

        OutputStream os = conn1.getOutputStream();
        os.write(inputString.getBytes());
            
        os.flush();

        if (conn1.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
            throw new RuntimeException("HTTP error code : "
                    + conn1.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn1.getInputStream())));

        String output;
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {
            System.out.println(output);
        }

        conn1.disconnect();

      } catch (MalformedURLException e) {

        e.printStackTrace();

      } catch (IOException e) {

        e.printStackTrace();

     }
         
         
    }
    @FXML
     private void HomeBtn(ActionEvent event) throws Exception{
        // hides previous window
        ((Node)(event.getSource())).getScene().getWindow().hide();
        // sets up the stage
        Parent parent = FXMLLoader.load(getClass().getResource("/solacecontrolgui/HomePage.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Mission Control");
        stage.show();

}


    // this method is called by the initialze method and runs every three seconds
    private void boatPositionLoop() {
                    //This here updates to the ships position every 3 seconds 
                   Runnable boatCurrentPosition = new Runnable() 
                   {
                        public void run() {
                            retriveData();
                            updateBoatMarker();
                            
                        }
                    };

            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executor.scheduleAtFixedRate(boatCurrentPosition, 0, 3, TimeUnit.SECONDS);




           }
    
    // this method is used to update the boats marker on screen 
    private void updateBoatMarker(){
         
            if(mcTab.isSelected() == true){
    
               boatPosition.clear();
               boatPositionLat.clear();
               boatPositionLon.clear();
                 
               boatPositionLat.add(Double.parseDouble(lat));
               boatPositionLon.add(Double.parseDouble(lon));
               
               //System.out.println(" printing boat position + ways now ");
                // read the array do something everytime you go through the array 
               
                     
                     boatPosition.add(   
                new MyWaypoint ( "                Solace", Color.RED, new GeoPosition((Double.parseDouble(lat)), (Double.parseDouble(lon))))
                        
                 );
                         //System.out.println(boatPosition.toString());
                // Create a waypoint painter that takes all the waypoints
                
                waypointBoatPainter.setWaypoints(boatPosition);
                waypointBoatPainter.setRenderer(new FancyWaypointRenderer());
                mapViewer.setOverlayPainter(waypointBoatPainter);
            }     
		for (int i = 0; i < wayPointsLatMap.size(); i++) {
                    
               
                  
                    if (wayPointsLatMap.size() != 0){
                boatPosition.add(
                            
                new MyWaypoint ( "        " + String.valueOf(i+1), Color.ORANGE, new GeoPosition((wayPointsLatMap.get(i)), (wayPointsLonMap.get(i))))

                );    
                    }
               
                      
                //System.out.println(boatPosition.toString());
                // Create a waypoint painter that takes all the waypoints
               
                waypointBoatPainter.setWaypoints(boatPosition);
                waypointBoatPainter.setRenderer(new FancyWaypointRenderer());
                mapViewer.setOverlayPainter(waypointBoatPainter);
       
        
		}
            
    }
    @FXML
  private void commsBtnPress(ActionEvent event) {
      
      if(commsBtnStatus == 0){
          
      
       Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Comms are Fine");
                    alert.setHeaderText("Comms are Fine");
                    String s ="Comms are fine, press OK";
                    alert.setContentText(s);
                    alert.show();
                    retriveData();
                    }
      
        if(commsBtnStatus == 1){
          
      
       Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Comms Issue");
                    alert.setHeaderText("Comms issue found");
                    String s ="Is the boatD server connected?";
                    alert.setContentText(s);
                    alert.show();
                    commsBtnStatus = 0;
                    commsBtn.setStyle("-fx-background-color: #008000");
                    retriveData();
                
                    }
  }
  
    @FXML
  private void bActivePressed(ActionEvent event) {
      
      if(bActiveBtnStatus == 0){
          
      
       Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Boat Status is fine");
                    alert.setHeaderText("Boat Status is fine");
                    String s ="Boat Status is fine, Press OK";
                    alert.setContentText(s);
                    alert.show();
                
                    }
      
        if(bActiveBtnStatus == 1){
          
      
       Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Boat Connection Issue");
                    alert.setHeaderText("Boat Connection Issue Found");
                    String s ="Is The Boat Connected?";
                    alert.setContentText(s);
                    alert.show();
                    bActiveBtnStatus = 0;
                    bActiveButton.setStyle("-fx-background-color: #008000");
                
                    }
  }
  
    @FXML
  private void clearConfigBtn(ActionEvent event) {
      clearConfig();
  }
  @FXML
  private void startDisable(){
      System.out.println("mission Button Disabled");
      startMBtn.setDisable(true);
  }
  @FXML
  private void startEnable(){
        System.out.println("mission Button Enabled");
      startMBtn.setDisable(false);
  }

  private void clearConfig() {
      
      System.out.println("Config Cleared");
      
       wayPointsLatMap.clear();
       wayPointsLonMap.clear();
       
       boatPositionLat.clear();
       boatPositionLon.clear();
       
       boatPosition.clear();
       waypoints.clear();
                 
              
               
               //System.out.println(" printing boat position + ways now ");
                // read the array do something everytime you go through the array 
               
                     
                 
                
                waypointBoatPainter.setWaypoints(boatPosition);
                waypointBoatPainter.setRenderer(new FancyWaypointRenderer());
                mapViewer.setOverlayPainter(waypointBoatPainter);
                
                     //System.out.println(boatPosition.toString());
                // Create a waypoint painter that takes all the waypoints
               
                waypointBoatPainter.setWaypoints(boatPosition);
                waypointBoatPainter.setRenderer(new FancyWaypointRenderer());
                mapViewer.setOverlayPainter(waypointBoatPainter);
       
            }     
		
               
                      
           
        
}

  
  


