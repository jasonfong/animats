/*
 * SensoryBoolNeuron.java
 *
 * sensory neuron with a 0 or 1 output
 *
 * Created on October 16, 2004, 3:04 PM
 */

package animatsim;

import java.util.*;


public class SensoryBoolNeuron extends SensoryNeuron {
    
    /** Creates a new instance of Neuron */
    public SensoryBoolNeuron(String n) {
        super(n);
    }
    
    public SensoryBoolNeuron(String n, double t) {
        super(n, t);
    }
    
    public void update() {
        totalIn = sensoryInput;
        
        if (totalIn >= threshold) {
            AP = 1;
        }
        else {
            AP = 0;
        }
    }
}