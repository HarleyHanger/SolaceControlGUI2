
package solacecontrolgui;

import com.bbn.openmap.layer.shape.ShapeLayer;
import java.util.Properties;


public class MapFrame extends javax.swing.JFrame {

    /**     * Creates new form MapFrame     */
    public MapFrame() {
        super("Simple Map");

        initComponents();
  
         initMap();

    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mapBean = new com.bbn.openmap.MapBean();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().add(mapBean, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initMap() {

      Properties shapeLayerProps = new Properties();
      shapeLayerProps.put("prettyName", "Political Solid");
      shapeLayerProps.put("lineColor", "000000");
      shapeLayerProps.put("fillColor", "BDDE83");
      shapeLayerProps.put("shapeFile", "C:\\Users\\Harley\\Documents\\SolentGUI\\SolaceGUI\\resources\\map\\shape\\dcwpo-browse.shp");
      shapeLayerProps.put("spatialIndex", "C:\\Users\\Harley\\Documents\\SolentGUI\\SolaceGUI\\resources\\map\\shape\\dcwpo-browse.ssx");

      ShapeLayer shapeLayer = new ShapeLayer();
      shapeLayer.setProperties(shapeLayerProps);

      // Add the political layer to the map
      mapBean.add(shapeLayer);

}

    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MapFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MapFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MapFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MapFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                
                new MapFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.bbn.openmap.MapBean mapBean;
    // End of variables declaration//GEN-END:variables
}


