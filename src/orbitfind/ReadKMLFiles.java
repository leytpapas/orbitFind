
package orbitfind;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;

public class ReadKMLFiles
{
    private mainFrame frame = null;
    public static double PI = 3.14159265;
    public static double TWOPI = 2 * PI;
    
    public ReadKMLFiles(){}
    
    public ReadKMLFiles(mainFrame frame){
        this.frame = frame;
    }
    
    private String nodeToString(Node node) {
        
        StringWriter sw = new StringWriter();
        try {
          Transformer t = TransformerFactory.newInstance().newTransformer();
          t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
          t.setOutputProperty(OutputKeys.INDENT, "yes");
          t.transform(new DOMSource(node), new StreamResult(sw));
        } catch (TransformerException te) {
          System.out.println("nodeToString Transformer Exception");
        }
        return sw.toString();
    }
    
    public void combineFiles(String output,String Polypath,Map<String,Dummy> passes){
        
        
        
        try {
            
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(Polypath);
            Node root = document.getDocumentElement().getElementsByTagName("Document").item(0);
            
            String uniChar  = "\u00b0"; //Celcius degree
            
            int y=1;
            for(Map.Entry<String,Dummy> entry : passes.entrySet() ){
                    ArrayList<Node> nodes = entry.getValue().getNodes();
                    ArrayList<String> point = entry.getValue().getPoints();
                    for(int i=0;i<nodes.size();i++){
                        
                        Element newElem = document.createElement("Placemark");

                    
                        Element name = document.createElement("name");
                        name.appendChild(document.createTextNode(entry.getKey()+" "+ (i+1) +uniChar));
                        newElem.appendChild(name);

                        Element styleUrl = document.createElement("styleUrl");
                        styleUrl.appendChild(document.createTextNode("#line_label_"+y));
                        newElem.appendChild(styleUrl);
                        
                        Element multigeo = document.createElement("MultiGeometry");
                        Element mark = document.createElement("Point");
                        Element coords = document.createElement("coordinates");
                        coords.appendChild(document.createTextNode(point.get(i)));
                        mark.appendChild(coords);
                        multigeo.appendChild(mark);
                        
                        
                        Node nNode = document.importNode(nodes.get(i), true);
                        multigeo.appendChild(nNode);
                        
                        newElem.appendChild(multigeo);
                        root.appendChild(newElem);
                        
                    }
                    
                if(y==16){
                    y=0;
                }
                y++;
            }
            
            
            DOMSource source = new DOMSource(document);
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer;
            
                transformer = transformerFactory.newTransformer();
            
            File out = new File(output);
            if(!out.exists()){
                out.createNewFile();
            }
            StreamResult result = new StreamResult(output);
            transformer.transform(source, result);
            
        } catch (ParserConfigurationException ex) {
            JOptionPane.showMessageDialog(frame, "Error writing to file");
            Logger.getLogger(ReadKMLFiles.class.getName()).log(Level.SEVERE, null, ex);
        }catch (TransformerConfigurationException ex) {
            JOptionPane.showMessageDialog(frame, "Error writing to file");
                Logger.getLogger(ReadKMLFiles.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            JOptionPane.showMessageDialog(frame, "Error writing to file");
            Logger.getLogger(ReadKMLFiles.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error writing to file");    
            Logger.getLogger(ReadKMLFiles.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            JOptionPane.showMessageDialog(frame, "Error writing to file");
            Logger.getLogger(ReadKMLFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    
    private String coordinate_is_inside_polygon(double latitude, double longitude, ArrayList<Double> lat_array, ArrayList<Double> long_array)
    {
        int i;
        double angle = 0;
        double point1_lat;
        double point1_long;
        double point2_lat;
        double point2_long;
        int n = lat_array.size();

        for (i = 0; i < n; i++)
        {
            point1_lat = lat_array.get(i) - latitude;
            point1_long = long_array.get(i) - longitude;
            point2_lat = lat_array.get((i + 1) % n) - latitude;
            point2_long = long_array.get((i + 1) % n) - longitude;
            angle += Angle2D(point1_lat, point1_long, point2_lat, point2_long);
            if (Math.abs(angle) >= PI){
                
                return String.valueOf(latitude)+","+String.valueOf(longitude)+",0";
            }
        }
        return null;
    }

    public static double Angle2D(double y1, double x1, double y2, double x2)
    {
        double theta1 = Math.atan2(y1, x1);
        double theta2 = Math.atan2(y2, x2);
        double dtheta = theta2 - theta1;

        while (dtheta > PI)
        {
            dtheta -= TWOPI;
        }
        while (dtheta < -PI)
        {
            dtheta += TWOPI;
        }

        return (dtheta);
    }

    public String checkPointDensity(Double[] point1, Double[] point2, Polygon polygon)
    {
        //System.out.print("Close...");

        Double maxLat = polygon.findMaxLat();
        Double maxLong = polygon.findMaxLong();
        Double minLat = polygon.findMinLat();
        Double minLong = polygon.findMinLong();

        Double latDiff = maxLat-minLat;
        Double longDiff = maxLong-minLong;

        Double distance = Math.sqrt((point1[0]-point2[0])*(point1[0]-point2[0])+(point1[1]-point2[1])*(point1[1]-point2[1]));

        if(((distance>latDiff)||(distance>longDiff))&&(latDiff>longDiff))
        {
            int n =  4*(int)(distance/longDiff);
            return fixPointDensity(point1, point2,polygon, n);
        }
        else if(((distance>latDiff)||(distance>longDiff))&&(longDiff>latDiff))
        {
            int n =  4*(int)(distance/latDiff);
            return fixPointDensity(point1, point2, polygon, n);
        }
        else
        {
            return null;
        }
    }

    public String fixPointDensity(Double[] point1,  Double[] point2, Polygon polygon, int n)
    {
        ArrayList<Double> tempLat = polygon.getLat();
        ArrayList<Double> tempLong = polygon.getLong();
        Double[] point = point1;

        Double latStep = (point2[0] - point1[0])/n;
        Double longStep = (point2[1] - point1[1])/n;

        for(int i=0; i<n; i++)
        {
            point[0] = point[0] + latStep;
            point[1] =  point[1] + longStep;
            
            String pointInside = coordinate_is_inside_polygon(point[0], point[1], tempLat, tempLong);
            
            if (pointInside!=null)
            {
                return pointInside;
            }
            else
            {
                continue;
            }
        }

        return null;
    }

    public boolean isCloseToPolygon(Double[] point, Polygon polygon)
    {
        Double maxLat = polygon.findMaxLat();
        Double maxLong = polygon.findMaxLong();
        Double minLat = polygon.findMinLat();
        Double minLong = polygon.findMinLong();

        Double latDiff = polygon.findMaxLat()-polygon.findMinLat();
        Double longDiff = polygon.findMaxLong()-polygon.findMinLong();

        Double latitude = point[0];
        Double longtitude = point[1];

        if((latitude > minLat - latDiff)&&(latitude < maxLat + latDiff)&&(longtitude > minLong - longDiff)&&(longtitude < maxLong + longDiff))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public Polygon createPoly(String fileName)
    {
        ArrayList<Double> lat_array = new ArrayList<Double>();
        ArrayList<Double> long_array = new ArrayList<Double>();

        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            FileInputStream in = new FileInputStream(new File(fileName));
            Document doc = dBuilder.parse(in, "UTF-8");

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();
            NodeList nList;
            
            nList = doc.getElementsByTagName("LinearRing");
                

            for (int i = 0; i < nList.getLength(); i++)
            {
                Node nNode = nList.item(i);
                
                if (nNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eElement = (Element) nNode;
                    String points[];
                    
                    
                    points = (eElement.getElementsByTagName("coordinates").item(0).getTextContent()).split("\\s+");
                   

                    for (int y = 0; y < points.length; y++)
                    {
                        

                        points[y] = points[y].replace(" ", "\n");
                        points[y] = points[y].replace("\t", "\n");

                        if (points[y].equals(""))
                        {
                            continue;
                        }

                        String singlePoint[] = points[y].split(",");
                        lat_array.add(Double.parseDouble(singlePoint[0]));
                        long_array.add(Double.parseDouble(singlePoint[1]));
                        

                        if(y==points.length-2)
                        {
                            break;
                        }
                        
                    }
                }
            }

            Polygon temp = new Polygon(lat_array, long_array);
            return temp;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
    
    
    public Dummy checkCoordinates(String fileName,Polygon polygon,Double degrees)
    {
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            FileInputStream in = new FileInputStream(new File(fileName));
            Document doc = dBuilder.parse(in, "UTF-8");
            
            //optional, but recommended
            doc.getDocumentElement().normalize();
            //ArrayList<Node> nodeList = null ;
            Dummy dummy = null;
            NodeList nList;
            
            nList = doc.getElementsByTagName("LineString");
            
            for (int i = 0; i < nList.getLength(); i++)
            {
                Node nNode = nList.item(i);
                
                if (nNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    ArrayList<Double> lat_array = new ArrayList<>();
                    ArrayList<Double> long_array = new ArrayList<>();
                    Element eElement = (Element) nNode;
                    String points[];
                    
                    points = (eElement.getElementsByTagName("coordinates").item(0).getTextContent()).split("\\s+");
                   
                    for (int y = 0; y < points.length; y++){
                     
                        points[y] = points[y].replace(" ", "\n");
                        points[y] = points[y].replace("\t", "\n");

                        if (points[y].equals("")){
                            continue;
                        }

                        String singlePoint[] = points[y].split(",");
                        lat_array.add(Double.parseDouble(singlePoint[0]));
                        long_array.add(Double.parseDouble(singlePoint[1]));

                    }
                    Polygon temp = new Polygon(lat_array, long_array);
                    
                    if(degrees == 0.0){
                        String pointInside = checkOrbit(polygon, temp);

                        if(pointInside!=null){
                           
                            if(dummy== null){
                                dummy = new Dummy(nNode, pointInside);
                                continue;
                            }
                            dummy.addInfo(nNode, pointInside);
                           
                        }
                    }else{
                        String pointInside = checkAreaInSight(polygon, temp,  degrees);
                        if(pointInside != null){
                           
                            if(dummy== null){
                                dummy = new Dummy(nNode, pointInside);
                                continue;
                            }
                            dummy.addInfo(nNode, pointInside);
                            
                        }
                    }
                    
                }
            }

            return dummy;
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(frame, "Error computing orbits.\nIf this continues to show,open .kml and \"Save as...\" with utf-8 encoding\nOtherwise go fuck yourself");
                        
            e.printStackTrace();
        }

        return null;
    }

    public String checkOrbit(Polygon polygon, Polygon orbit)
    {
        ArrayList<Double> polygonLat = polygon.getLat();
        ArrayList<Double> polygonLong = polygon.getLong();
        ArrayList<Double> orbitLat = orbit.getLat();
        ArrayList<Double> orbitLong = orbit.getLong();
        String pointInside = null;
        Double[] point1 = new Double[2];
        Double[] point2 = new Double[2];

        for(int i=0; i<orbitLat.size(); i++)
        {
            point1[0] = orbitLat.get(i);
            point1[1] = orbitLong.get(i);
            
            pointInside = coordinate_is_inside_polygon(point1[0], point1[1], polygonLat, polygonLong);
            if(pointInside!=null)
            {
                return pointInside;
            }
            else if((isCloseToPolygon(point1, polygon))&&(i<orbitLat.size()-1))
            {
                point2[0] = orbitLat.get(i+1);
                point2[1] = orbitLong.get(i+1);
                pointInside = checkPointDensity(point1, point2, polygon);
                if (pointInside!=null)
                {
                    return pointInside;
                }
                else
                {
                    continue;
                }
            }
            else
            {
                continue;
            }
        }

        return pointInside;

    }

    public String checkAreaInSight(Polygon polygon, Polygon orbit, Double distance)
    {
        ArrayList<Double> tempLat = new ArrayList<Double>();
        ArrayList<Double> tempLong = new ArrayList<Double>();
        ArrayList<Double> polygonLat = polygon.getLat();
        ArrayList<Double> polygonLong = polygon.getLong();
        ArrayList<Double> orbitLat = orbit.getLat();
        ArrayList<Double> orbitLong = orbit.getLong();
        Double[] currentPoint = new Double[2];
        Double[] point1 = new Double[2];
        Double[] point2 = new Double[2];
        Double[] point3 = new Double[2];
        Double[] point4 = new Double[2];
        String pointInside = null;
        for(int i=1; i<orbitLat.size()-1; i++)
        {
            currentPoint[0] = orbitLat.get(i);
            currentPoint[1] = orbitLong.get(i);

            if(isCloseToPolygon(currentPoint,polygon))
            {
                
                
                point1[0] = orbitLat.get(i-1) + distance;
                point1[1] = (orbitLong.get(i-1) + distance) % 180;
                point2[0] = orbitLat.get(i-1) - distance;
                point2[1] = (orbitLong.get(i-1) - distance) % -180;
                point3[0] = orbitLat.get(i+1) + distance;
                point3[1] = (orbitLong.get(i+1) + distance) % 180;
                point4[0] = orbitLat.get(i+1) - distance;
                point4[1] = (orbitLong.get(i+1) - distance) % -180;
                
                if(point1[0]>90)
                {
                    point1[0] = 90.0;
                }
                else if(point1[1]>180)
                {
                    point1[1] = point1[1] - 360;
                }
                else if(point2[0]<-90)
                {
                    point2[0] = -90.0;
                }
                else if(point2[1]<-180)
                {
                    point2[1] = point2[1] + 360;
                }
                else if(point3[0]>90)
                {
                    point1[0] = 90.0;
                }
                else if(point3[1]>180)
                {
                    point1[1] = point1[1] - 360;
                }
                else if(point4[0]<-90)
                {
                    point2[0] = -90.0;
                }
                else if(point4[1]<-180)
                {
                    point2[1] = point2[1] + 360;
                }
                else
                {
                    continue;
                }

                tempLat.add(point1[0]);
                tempLat.add(point2[0]);
                tempLat.add(point3[0]);
                tempLat.add(point4[0]);
                tempLong.add(point1[1]);
                tempLong.add(point2[1]);
                tempLong.add(point3[1]);
                tempLong.add(point4[1]);

                for(int j=0; j<polygonLat.size(); j++)
                {   
                    pointInside = coordinate_is_inside_polygon(polygonLat.get(j),polygonLong.get(j),tempLat,tempLong);
                    if(pointInside != null)
                    {
                        return pointInside;
                    }
                    else
                    {
                        continue;
                    }
                }

                tempLat.clear();
                tempLong.clear();

            }
            else
            {
                continue;
            }
        }
        pointInside = checkOrbit(polygon, orbit);
        return pointInside;
    }
    
}