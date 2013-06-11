/*
 * Placeable.java
 *
 * abstract class for objects in the world
 * (Creature or Food)
 *
 * Created on November 23, 2004, 4:57 PM
 */

package animatsim;

import java.awt.Color;
import java.util.LinkedList;


public class Placeable {
    
    /** Creates a new instance of Placeable */
    public Placeable() {
        xcoord = 0;
        ycoord = 0;
        world = null;
        color = null;
    }
    
    public Placeable(int x, int y, World w, Color c) {
        xcoord = x;
        ycoord = y;
        world = w;
        color = c;
    }
    
    public int getX() {
        return xcoord;
    }
    
    public int getY() {
        return ycoord;
    }
    
    public void setX(int x) {
        xcoord = x;
    }
    
    public void setY(int y) {
        ycoord = y;
    }
    
    public void setXY(int x, int y) {
        xcoord = x;
        ycoord = y;
    }
    
    public void setColor(Color c){
        color = c;
    }
    
    public Color getColor(){
        return color;
    }
    
    
    public double distTo(Placeable p) {
        double dist;
        
        if (p == null) {
            return World.worldSizeX;
        }
        
        dist = Math.sqrt((Math.pow(p.getX()- xcoord, 2)) + (Math.pow(p.getY() - ycoord, 2)));
        return dist;
    }    
    
    public Placeable findNearestInList(LinkedList list) {
        Placeable nearest = null;
        Placeable pobj;
        double shortest = -1;
        
        for (Object obj : list) {
            if (obj == this) {
                continue;
            }
            pobj = (Placeable)obj;
            if ((shortest == -1) || distTo(pobj) < shortest) {
                shortest = distTo(pobj);
                nearest = pobj;
            }
        }
        return nearest;
    }

    protected int xcoord;
    protected int ycoord;
    protected World world;
    protected Color color;
}
