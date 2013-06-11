/*
 * Genome.java
 *
 * The creatures' genetic code
 *
 * Created on November 18, 2004, 5:48 PM
 */

package animatsim;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;


public class Genome {
    
    /** Creates a new instance of Genome */
    public Genome(int numSense_action, int numSense_interaction, int numSense_shared,
                  int numInter_action, int numInter_interaction,
                  int numMotor_action, int numMotor_interaction,
                  double min, double max) {
        int i;
        
        int length = numSense_action + numSense_interaction + numSense_shared +
                     numInter_action + numInter_interaction +
                     numMotor_action + numMotor_interaction +
                     (numSense_action + numSense_shared) * numInter_action +
                     numInter_action * numMotor_action +
                     (numSense_interaction + numSense_shared) * numInter_interaction +
                     numInter_interaction * numMotor_interaction;
        
        r = new Random();
        geneSeq = new LinkedList<Double>();
        
        for (i = 0; i < length; i++) {
            geneSeq.add((r.nextDouble() * (max - min) + min) * (r.nextBoolean() ? 1 : -1));
        }
        
        iter = geneSeq.listIterator();
    }
    
    private Genome() {
        geneSeq = new LinkedList<Double>();
        iter = geneSeq.listIterator();
        r = new Random();
    }
    
    public void reset() {
        iter = geneSeq.listIterator();
    }
    
    public boolean hasNext() {
        return iter.hasNext();
    }
    
    public Double next() {
        return iter.next();
    }
    
    public void mutate(double prob, double amount) {
        ListIterator<Double> itr = geneSeq.listIterator();
        Double val;
        
        LinkedList<Double> mutatedGenes = new LinkedList<Double>();
        
        if (prob == 0.0) {
            return;
        }
        
        while (itr.hasNext()) {
            val = itr.next();
            
            if (r.nextDouble() <= prob) {
                val += ((r.nextInt(2) == 0) ? 1 : -1) * r.nextDouble() * amount; 
            }
//            if (val < 0) {
//                val = 0.0;
//            }
            mutatedGenes.add(val);
        }
        
        geneSeq = mutatedGenes;
        iter = geneSeq.listIterator();
    }
    
    public Genome clone() {
        Genome copy;
        
        copy = new Genome();
        
        copy.geneSeq.clear();
        
        for (Double gene : this.geneSeq) {
            copy.geneSeq.add(gene);
        }
        
        copy.iter = copy.geneSeq.listIterator();
        
        return copy;
    }
    
    public Genome crossoverWith(Genome other) {
        Genome crossed = new Genome();
        
        double myGene, otherGene;
        
        ListIterator<Double> myGeneItr = geneSeq.listIterator();
        ListIterator<Double> otherGeneItr = other.geneSeq.listIterator();
        
        while (myGeneItr.hasNext()) {
            myGene = myGeneItr.next();
            otherGene = otherGeneItr.next();
            
            if (r.nextBoolean()) {
                crossed.geneSeq.add(myGene);
            }
            else {
                crossed.geneSeq.add(otherGene);
            }
        }
        
        crossed.iter = crossed.geneSeq.listIterator();
        
        return crossed;
    }
    
    public Genome crossoverWithAndMutate(Genome other) {
        Genome crossedAndMutated = this.crossoverWith(other);
        crossedAndMutated.mutate(mutateProb, mutateAmount);
        return crossedAndMutated;
    }
    
    private LinkedList<Double> geneSeq;
    private ListIterator<Double> iter;
    private Random r;
    
    public static double mutateProb = 0.1;
    public static double mutateAmount = 100;
}
