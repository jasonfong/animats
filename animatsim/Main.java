/*
 * Main.java
 *
 * Main class
 * 
 * Created on October 16, 2004, 3:04 PM
 */

package animatsim;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int i;
        Creature c, child;
        Food f;
        
        Random r = new Random();
        Wait wait = new Wait();
        World w = new World();
        
        JFrame frame = new JFrame("AnimatSim");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        Container panel = new AnimatSimPanel(w);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        
        Graphics g = panel.getGraphics();

        
        for (i = 0; i < World.initialCreatures; i++) {
            w.insertCreature(new Creature(w, Color.RED), r.nextInt(699), r.nextInt(699));
        }
        
        while(true) {
            //System.out.println("time step");
            w.update();
            panel.update(panel.getGraphics());
//            wait.manySec(0.001);
        }
    } 
}
