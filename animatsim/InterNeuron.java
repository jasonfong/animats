/*
 * InterNeuron.java
 *
 * hidden layer neurons
 *
 * Created on October 16, 2004, 3:04 PM
 */

package animatsim;

import java.util.*;


public class InterNeuron extends Neuron {
    
    /** Creates a new instance of Neuron */
    public InterNeuron(String n) {
        threshold = 0;
        name = n;
    }
    
    public InterNeuron(String n, double t) {
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
            AP = 1;
        }
        else {
            AP = 0;
        }
    }    
}