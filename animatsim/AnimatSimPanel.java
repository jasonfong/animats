/*
 * AnimatSimPanel.java
 *
 * Panel for displaying the simulation world
 *
 * Created on December 11, 2004, 1:51 PM
 */

package animatsim;

import java.awt.*;
import javax.swing.*;


public class AnimatSimPanel extends JPanel {
    
    /** Creates a new instance of AnimatSimPanel */
    public AnimatSimPanel(World w) {
        super(true);
        world = w;
        setOpaque(true); // we don't paint all our bits
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public Dimension getPreferredSize() {
        return new Dimension(700, 700);
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
 //
//        g.clearRect(0, 0, World.worldSizeX, World.worldSizeY);
        
        for (Creature c : world.getCreatureList()) {
            g.setColor(c.getColor());
            //g.drawRect(c.getX(), c.getY(), 1, 1);
            g.drawString("" + c.getEnergy(), c.getX() - 5, c.getY() - 5);
            g.drawString("" + c.getHealth(), c.getX() - 5, c.getY() + 15);
            g.drawOval(c.getX() - 1, c.getY() - 1, 3, 3);
        }
        
        for (Food f : world.getFoodList()) {
            g.setColor(f.getColor());
            g.drawRect(f.getX() - 2, f.getY() -2, 5, 5);
        }
        
    }

    private World world;
}
