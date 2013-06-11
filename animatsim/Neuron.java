/*
 * Neuron.java
 *
 * abstract class for all neuron types
 *
 * Created on October 16, 2004, 3:04 PM
 */

package animatsim;

import java.util.*;


public abstract class Neuron {
    
    public Neuron() {
        AP = 0;
        threshold = 0;
        inputs = new LinkedList<Neuron>();
        weights = new LinkedList<Double>();
        output = null;
        name = "anonymous";
        totalIn = 0;
    }
    
    public abstract void update();
    
    public void setThreshold(double t) {
        threshold = t;
    }
    
    public double getAP() {
        return AP;
    }
     
    public void setOutput(Neuron n) {
        output = n;
    }
    
    public void addInput(Neuron n, double weight) {
        inputs.add(n);
        weights.add(weight);
    }
    
    public String toString() {
        String s;
        
        s = name + "(" + threshold + "): " + totalIn + " -> " + AP;
        return s;
    }

    protected double AP;
    protected double threshold;
    protected LinkedList<Neuron> inputs;
    protected LinkedList<Double> weights;
    protected Neuron output;   
    protected String name;
    protected double totalIn;
}