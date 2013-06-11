/*
 * MotorNeuron.java
 *
 * motor neurons
 *
 * Created on October 16, 2004, 3:04 PM
 */

package animatsim;

import java.util.*;


public class MotorNeuron extends Neuron {
    
    /** Creates a new instance of Neuron */
    public MotorNeuron(String n) {
        threshold = 0;
        name = n;
    }
    
    public MotorNeuron(String n, double t) {
        threshold = t;
        name = n;
    }
    
    public void update() {
        Neuron n;
        double w;
        
        totalIn = 0;
        
        ListIterator<Neuron> itrn = inputs.listIterator();
        ListIterator<Double> itrw = weights.listIterator();
        
        while (itrn.hasNext()) {
            n = itrn.next();
            w = itrw.next();
            totalIn += n.getAP() * w;
        }
        
        if (totalIn >= threshold) {
            AP = totalIn;
        }
        else {
            AP = 0;
        }
    }    
}