/*
 * WorldTile.java
 *
 * class representing a single tile in the world
 *
 * Created on October 30, 2004, 12:29 AM
 */

package animatsim;


public class WorldTile {
    
    /** Creates a new instance of WorldTile */
    public WorldTile(int x, int y) {
        xcoord = x;
        ycoord = y;
        hasCreature = false;
        hasFood = false;
    }
    
    public boolean hasCreature;
    public boolean hasFood;
    
    
    private int xcoord;
    private int ycoord;
}
