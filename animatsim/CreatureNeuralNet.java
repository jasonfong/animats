/*
 * CreatureNeuralNet.java
 *
 * The creatures' neural networks
 *
 * Created on November 13, 2004, 1:33 PM
 */

package animatsim;

import java.util.*;


public class CreatureNeuralNet {
    
    /** Creates a new instance of CreatureNeuralNet */
    public CreatureNeuralNet(Genome genome, Creature c, World w) {
        action_sense = new LinkedList<Neuron>();
        action_inter = new LinkedList<Neuron>();
        action_motor = new LinkedList<Neuron>();

//        myEnergy = new SensoryNeuron("myEnergy");
//        myHealth = new SensoryNeuron("myHealth");
        foodDist = new SensoryNeuron("foodDist");
        foodToLeft = new SensoryNeuron("foodToLeft");
        foodToRight = new SensoryNeuron("foodToRight");
        
        goForward = new MotorNeuron("goForward");
        goBackward = new MotorNeuron("goBackward");
        turnLeft = new MotorNeuron("turnLeft");
        turnRight = new MotorNeuron("turnRight");
//        idle = new MotorNeuron("idle");
    
//        action_sense.add(myEnergy);
//        action_sense.add(myHealth);
        action_sense.add(foodDist);
        action_sense.add(foodToLeft);
        action_sense.add(foodToRight);

        action_inter.add(new InterNeuron("action_inter1"));
        action_inter.add(new InterNeuron("action_inter2"));
        action_inter.add(new InterNeuron("action_inter3"));
        action_inter.add(new InterNeuron("action_inter4"));
        action_inter.add(new InterNeuron("action_inter5"));
        action_inter.add(new InterNeuron("action_inter6"));
        action_inter.add(new InterNeuron("action_inter7"));
        action_inter.add(new InterNeuron("action_inter8"));

        action_motor.add(goForward);
        action_motor.add(goBackward);
        action_motor.add(turnLeft);
        action_motor.add(turnRight);
//        action_motor.add(idle);
        
        linkNeuronLists(action_sense, action_inter, genome);
        linkNeuronLists(action_inter, action_motor, genome);
        setNeuronThresholds(action_motor, genome);

        creature = c;
        world = w;
    }
    
    public void update() {
        for (Neuron s : action_sense) {
            ((SensoryNeuron)s).setSensoryInput(0);
        }
        
//        myEnergy.setSensoryInput(creature.getEnergy());

        Food nearestFood = (Food)creature.findNearestInList(world.getFoodList());

        double bearingToFood = creature.bearingTo(nearestFood);
        if (bearingToFood < 180) {
            foodToLeft.setSensoryInput(0);
            foodToRight.setSensoryInput(bearingToFood);
        }
        else {
            foodToLeft.setSensoryInput(360 - bearingToFood);
            foodToRight.setSensoryInput(0);
        }

        foodDist.setSensoryInput(creature.distTo(nearestFood));

        updateNeuronList(action_sense);
        updateNeuronList(action_inter);
        updateNeuronList(action_motor);
    }
    
    public Creature.Action chooseAction() {
        TreeMap<Double, Creature.Action> actions = new TreeMap<Double, Creature.Action>();
        
        actions.put(goForward.getAP(), Creature.Action.GO_FORWARD);
        actions.put(goBackward.getAP(), Creature.Action.GO_BACKWARD);
        actions.put(turnLeft.getAP(), Creature.Action.TURN_LEFT);
        actions.put(turnRight.getAP(), Creature.Action.TURN_RIGHT);
//        actions.put(idle.getAP(), Creature.Action.IDLE);
        
        return actions.get(actions.lastKey());
    }
    
    private void linkNeuronLists(LinkedList<Neuron> fromList, LinkedList<Neuron> toList, Genome g) {
        for (Neuron f : fromList) {
            f.setThreshold(g.next());
            for (Neuron t : toList) {
                t.addInput(f, g.next());
            }
        }
    }
    
    private void setNeuronThresholds(LinkedList<Neuron> list, Genome g) {
        for (Neuron n : list) {
            n.setThreshold(g.next());
        }
    }
    
    private void updateNeuronList(LinkedList<Neuron> list) {
        for (Neuron n : list) {
            n.update();
        }
    }
    
    // senses
//    protected SensoryNeuron myEnergy;
//    protected SensoryNeuron myHealth;
    protected SensoryNeuron foodDist;
    protected SensoryNeuron foodToLeft;
    protected SensoryNeuron foodToRight;
    
    // motor responses
    protected MotorNeuron goForward;
    protected MotorNeuron goBackward;
    protected MotorNeuron turnLeft;
    protected MotorNeuron turnRight;
    protected MotorNeuron idle;
    
    protected LinkedList<Neuron> action_sense;
    protected LinkedList<Neuron> action_inter;
    protected LinkedList<Neuron> action_motor;
    
    protected Creature creature;
    protected World world;
}
