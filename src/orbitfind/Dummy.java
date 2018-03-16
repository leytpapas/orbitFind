/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orbitfind;

import java.util.ArrayList;
import org.w3c.dom.Node;

/**
 *
 * @author Lefos
 */
public class Dummy {
    
    private ArrayList<Node> nodes;
    private ArrayList<String> points; 
    
    
    
    public Dummy(Node node,String point){
        this.nodes = new ArrayList<>();
        this.nodes.add(node);
        this.points = new ArrayList<>();
        this.points.add(point);
    }
    
    public Dummy(){
    
    }
    
    public void addInfo(Node node,String point){
        this.nodes.add(node);
        this.points.add(point);
    }
    
    public void addNode(Node node){
        this.nodes.add(node);
    }
    
    public void addNode(String point){
        this.points.add(point);    
    }
    
    public ArrayList<Node> getNodes(){
        return(nodes);
    }
    
    public ArrayList<String> getPoints(){
        return points;
    }
    
}
