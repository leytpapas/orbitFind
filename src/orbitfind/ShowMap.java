// License: GPL. For details, see Readme.txt file.
package orbitfind;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.JMapViewerTree;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.OsmTileLoader;

import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;
import org.openstreetmap.gui.jmapviewer.interfaces.TileLoader;
import org.openstreetmap.gui.jmapviewer.interfaces.TileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;

/**
 * Demonstrates the usage of {@link JMapViewer}
 *
 * @author Jan Peter Stotz
 *
 */
public class ShowMap extends JDialog implements JMapViewerEventListener {

    private static final long serialVersionUID = 1L;

    private final JMapViewerTree treeMap;

    private final JLabel zoomLabel;
    private final JLabel zoomValue;

    private final JLabel mperpLabelName;
    private final JLabel mperpLabelValue;
    private final List<ICoordinate> coords;
    private final int interval = (int) Toolkit.getDefaultToolkit().getDesktopProperty("awt.multiClickInterval");
    private boolean flag=false;
    //private final MapPolygonImpl polygon1 = new MapPolygonImpl();
    
    
    
    public String coordsToString(){
        String coordsStr="\n";
        
        if((coords.size()==3 && flag) || coords.size()==1){
            return null;/*
            coords.remove(2);*/
        }
        for(ICoordinate coordinates: coords){
            coordsStr=coordsStr+ coordinates.getLon()+ "," + coordinates.getLat() +",0"+" ";
        }
        coordsStr=coordsStr+coords.get(0).getLon()+ "," + coords.get(0).getLat() +",0"+" " +"\n";
        return coordsStr;
    }
    
    public ShowMap(JFrame parent,String filename,mainFrame dummy) {
        super(parent,"JMapViewer",ModalityType.APPLICATION_MODAL);
        coords = new ArrayList<ICoordinate>();
        setSize(800, 768);

        treeMap = new JMapViewerTree("Zones");

        // Listen to the map viewer for user operations so components will
        // receive events and update
        map().addJMVListener(this);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        //setExtendedState(JDialog.MAXIMIZED_BOTH);
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelTop = new JPanel();
        JPanel panelBottom = new JPanel();
        JPanel helpPanel = new JPanel();

        mperpLabelName = new JLabel("Meters/Pixels: ");
        mperpLabelValue = new JLabel(String.format("%s", map().getMeterPerPixel()));

        zoomLabel = new JLabel("Zoom: ");
        zoomValue = new JLabel(String.format("%s", map().getZoom()));

        add(panel, BorderLayout.NORTH);
        add(helpPanel, BorderLayout.SOUTH);
        panel.add(panelTop, BorderLayout.NORTH);
        panel.add(panelBottom, BorderLayout.SOUTH);
        JLabel helpLabel = new JLabel("Use right mouse button to move,\n "
                + "left double click or mouse wheel to zoom.");
        helpPanel.add(helpLabel);
        JButton button1 = new JButton("Set display to fit MapPolygons");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map().setDisplayToFitMapPolygons();
            }
        });
        JButton button2 = new JButton("Done");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KML_Creator temp=new KML_Creator();
                temp.addName("Area of Interest");
                String check = coordsToString();
                if(check==null){
                    JOptionPane.showMessageDialog(map(), "No right polygon found");
                    return;
                }
                temp.newPoly("Area of Interest", "#polygon_style", check);
                temp.closeCreator();
                String filepath= System.getProperty("user.dir") + System.getProperty("file.separator") +filename;
                temp.writeToFile(filepath);
                dummy.getTextField().setText(filepath);
                int reply = JOptionPane.showConfirmDialog(map(), "Want to see the generated kml file?\n(Requires Google Earth)", "", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION){
                    
                    if(!Desktop.isDesktopSupported()){
                       System.out.println("Desktop is not supported");
                       return;
                   }
                   Desktop desktop = Desktop.getDesktop();
                   try {
                       desktop.open(new File(filepath));
                   } catch (IOException ex) {
                       Logger.getLogger(ShowMap.class.getName()).log(Level.SEVERE, null, ex);
                   }
                }
                //Write coords to file
                setVisible(false);
                return;
                //System.exit(0);
            }
        });
        JButton button3 = new JButton("Clear");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map().removeAllMapMarkers();
                map().removeAllMapPolygons();
                coords.clear();
            }
        });
        
        JComboBox<TileSource> tileSourceSelector = new JComboBox<>(new TileSource[] {
                new OsmTileSource.Mapnik(),
                new OsmTileSource.CycleMap(),
        });
        tileSourceSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                map().setTileSource((TileSource) e.getItem());
            }
        });
        JComboBox<TileLoader> tileLoaderSelector;
        tileLoaderSelector = new JComboBox<>(new TileLoader[] {new OsmTileLoader(map())});
        tileLoaderSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                map().setTileLoader((TileLoader) e.getItem());
            }
        });
        map().setTileLoader((TileLoader) tileLoaderSelector.getSelectedItem());
        panelTop.add(tileSourceSelector);
        panelTop.add(tileLoaderSelector);
        
        ///
        /*final JCheckBox showToolTip = new JCheckBox("ToolTip visible");
        showToolTip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map().setToolTipText(null);
            }
        });
        panelBottom.add(showToolTip);*/
        ///
       /* final JCheckBox showTileGrid = new JCheckBox("Tile grid visible");
        showTileGrid.setSelected(map().isTileGridVisible());
        showTileGrid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map().setTileGridVisible(showTileGrid.isSelected());
            }
        });
        panelBottom.add(showTileGrid);*/
        final JCheckBox showZoomControls = new JCheckBox("Show zoom controls");
        showZoomControls.setSelected(map().getZoomControlsVisible());
        showZoomControls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map().setZoomContolsVisible(showZoomControls.isSelected());
            }
        });
        panelBottom.add(showZoomControls);
        
        panelBottom.add(button1);
        panelBottom.add(button2);
        panelBottom.add(button3);
        
        panelTop.add(zoomLabel);
        panelTop.add(zoomValue);
        panelTop.add(mperpLabelName);
        panelTop.add(mperpLabelValue);

        add(treeMap, BorderLayout.CENTER);

        map().setTileGridVisible(true);
        
        map().addMouseListener(new ClickListener(){
            public void singleClick(MouseEvent e)
            {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    
                    if(coords.size()==0){
                            coords.add(map().getPosition(e.getPoint()));
                            map().addMapMarker(new MapMarkerDot(coords.get(coords.size()-1).getLat(),coords.get(coords.size()-1).getLon()));
                    }else if(coords.size()==1){
                            map().removeAllMapMarkers();
                            coords.add(map().getPosition(e.getPoint()));
                            coords.add(map().getPosition(e.getPoint()));
                           
                            MapPolygonImpl poly = new MapPolygonImpl(coords);
                            Color color1 = new Color(0x20202020, true);
                            poly.setName("Area of Interest");
                            poly.setColor(Color.ORANGE);
                            poly.setBackColor(color1);
                            poly.setStroke(new BasicStroke(5));
                            map().addMapPolygon(poly);
                            flag=true;
                            
                    }else{
                        if(flag){
                            coords.remove(2);
                            flag=false;
                        }
                            map().removeAllMapPolygons();
                            coords.add(map().getPosition(e.getPoint()));
                            MapPolygonImpl poly = new MapPolygonImpl(coords);
                            Color color = new Color(0x20202020, true);
                            poly.setName("Area of Interest");
                            poly.setColor(Color.ORANGE);
                            poly.setBackColor(color);
                            poly.setStroke(new BasicStroke(5));
                            map().addMapPolygon(poly);
                    
                    }
                    //map().getAttribution().handleAttribution(e.getPoint(), true);
                
                }
            }
        
        });

        map().addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                boolean cursorHand = map().getAttribution().handleAttributionCursor(p);
                if (cursorHand) {
                    map().setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    map().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
                //if (showToolTip.isSelected()) map().setToolTipText(map().getPosition(p).toString());
            }
        });
    }

    private JMapViewer map() {
        return treeMap.getViewer();
    }
    
    private void writeFile(){
    
    }

    private static Coordinate c(double lat, double lon) {
        return new Coordinate(lat, lon);
    }

    /**
     * @param args Main program arguments
     */
    public static void main(String[] args) {
        //new ShowMap().setVisible(true);
    }
    
    public List<ICoordinate> returnCoords(){
        return coords;
    }

    private void updateZoomParameters() {
        if (mperpLabelValue != null)
            mperpLabelValue.setText(String.format("%s", map().getMeterPerPixel()));
        if (zoomValue != null)
            zoomValue.setText(String.format("%s", map().getZoom()));
    }

    @Override
    public void processCommand(JMVCommandEvent command) {
        if (command.getCommand().equals(JMVCommandEvent.COMMAND.ZOOM) ||
                command.getCommand().equals(JMVCommandEvent.COMMAND.MOVE)) {
            updateZoomParameters();
        }
    }
}
