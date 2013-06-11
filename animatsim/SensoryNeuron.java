/*
 * SensoryNeuron.java
 *
 * sensory neurons with variable value outputs
 *
 * Created on October 16, 2004, 3:04 PM
 */

package animatsim;

import java.util.*;


public class SensoryNeuron extends Neuron {
    
    /** Creates a new instance of Neuron */
    public SensoryNeuron(String n) {
        threshold = 0;
        name = n;
    }
    
    public SensoryNeuron(String n, double t) {
        threshold = t;
        name = n;
    }
    
    public void update() {
        totalIn = sensoryInput;
        
        if (totalIn >= threshold) {
            AP = totalIn;
        }
        else {
            AP = 0;
        }
    }
    
    public void setSensoryInput(double input) {
        //System.out.println("sense neuron updating with input: " + input);
        sensoryInput = input;
    }
    
    protected double sensoryInput;
}