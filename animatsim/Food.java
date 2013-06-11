/*
 * Food.java
 *
 * class representing food
 *
 * Created on November 25, 2004, 4:12 PM
 */

package animatsim;

import java.awt.Color;


public class Food extends Placeable {
    
    /** Creates a new instance of Food */
    public Food(World w, int x, int y) {
        super(x, y, w, Color.blue);
        amount = 10000;
    }
    
    public int bite() {
        int tmp;
        
        if (amount < biteSize) {
            tmp = amount;
            amount = 0;
            return tmp;
        }
        else {
            amount -= biteSize;
            return biteSize;
        }
    }
    
    public int getAmount() {
        return amount;
    }
    
    public void setAmount(int a) {
        amount = a;
    }
    
    private int amount;
    
    public static final int biteSize = 10;
}
