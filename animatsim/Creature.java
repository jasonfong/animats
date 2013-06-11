/*
 * Creature.java
 *
 * Class representing the creatures
 *
 * Created on October 30, 2004, 2:41 AM
 */

package animatsim;

import java.awt.Color;
import java.util.*;


public class Creature extends Placeable {
    
    /** Creates a new instance of Creature */
    public Creature(World w, Color c) {
        super(0, 0, w, c);
        genes = new Genome(5, 6, 4, 8, 8, 7, 8, 0.1, 1000);  
        controller = new CreatureNeuralNet(genes, this, w);
        alive = true;
        interactList = new LinkedList<Creature>();
    }
    
    public Creature(World w, Color c, Genome g) {
        super(0, 0, w, c);
        genes = g;
        controller = new CreatureNeuralNet(genes, this, w);
        alive = true;
        interactList = new LinkedList<Creature>();
    }
    

    public void moveTo(int x, int y) {
        if (x < 0) {
            x = 0;
        }
        else if (x > World.worldSizeX - 1) {
            x = World.worldSizeX - 1;
        }
        
        if (y < 0) {
            y = 0;
        }
        else if (y > World.worldSizeY - 1) {
            y = World.worldSizeY - 1;
        }
        
        //System.out.println("Moving from " + xcoord + "," + ycoord + " to " + x + "," + y);
        
        
        world.moveCreature(xcoord, ycoord, x, y);
        xcoord = x;
        ycoord = y;
    }
    
    public void move(Direction d) {
        int dir = 0;

        if (d == Direction.NONE) {
            return;
        }
        else if (d == Direction.FORWARD) {
            //System.out.println("moving forward");
            dir = 1;
        }
        else if (d == Direction.BACKWARD) {
            //System.out.println("moving backward");
            dir = -1;
        }
        else {
            System.out.println("unrecognized direction: " + d);
            return;
        }


        switch (heading) {
            case 0:
                moveTo(xcoord + dir, ycoord);
                break;
            case 45:
                moveTo(xcoord + dir, ycoord + dir);
                break;
            case 90:
                moveTo(xcoord, ycoord + dir);
                break;
            case 135:
                moveTo(xcoord - dir, ycoord + dir);
                break;
            case 180:
                moveTo(xcoord - dir, ycoord);
                break;
            case 225:
                moveTo(xcoord - dir, ycoord - dir);
                break;
            case 270:
                moveTo(xcoord, ycoord - dir);
                break;
            case 315:
                moveTo(xcoord + dir, ycoord - dir);
                break;

            default:
                System.out.println("unrecognized heading: " + heading);
                break;
        }
    }

    public void turn(Direction d) {
        if (d == Direction.NONE) {
            //System.out.println("turning nowhere");
            return;
        }

        if (d == Direction.LEFT) {
            //System.out.println("turning left");
            if (heading == 315) {
                heading = 0;
            }
            else {
                heading = heading + 45;
            }
    
        }
        else if (d == Direction.RIGHT) {
            //System.out.println("turning right");
            if (heading == 0) {
                heading = 315;
            }
            else {
                heading = heading - 45;
            }
        }
        
    }
    
    public int getHeading() {
        return heading;
    }

    
    public void update() {
        //System.out.println("updating creature");
        
        // nothing to update if dead
        if (! isAlive()) {
            return;
        }
        
        // mark as dead if health <= 0
        if (health <= 0) {
            alive = false;
            return;
        }
        
        if (mateWait > 0) {
            mateWait--;
        }
        
        Creature child;
        Food food;
        LinkedList<Food> flist = world.getFoodList();

        interactList = getCreaturesInRange(interactionRange);
        
        double healAmt;
        
        // heal injuries, rate proportional to energy
//        if (health < maxHealth) {
//            healAmt = baseHealRate * (energy / baseEnergy);
//            health += (healAmt > maxHealRate) ? maxHealRate : healAmt;
//            // cap max health
//            if (health > maxHealth) {
//                health = maxHealth;
//            }
//        }

        age++;
  
        controller.update();
        doAction(controller.chooseAction());

        //System.out.println("checking for food to eat...");
        food = (Food)findNearestInList(flist);
        if (distTo(food) <= 2) {
            addEnergy(food.bite());
        }
        else {
            subEnergy(energyLossRate);
        }
        
        if (energy <= 0) {
            subHealth(starveRate);
        }

        // mate if energy above threshold
        if (energy > mateThreshold) {
            for (Creature c : interactList) {
                if (isWillingToMateWith(c)) {
                    child = mateWith(c);
                    if (child != null) {
                     world.insertChild(child);
                    }
                    break;
                }
            }
        }
        
        if (age > oldAge) {
            subHealth((age - oldAge) * world.r.nextDouble());
        }
        //System.out.println("creature finished updating");
    }
    
    public void doAction(Creature.Action action) {
        if (action == Creature.Action.GO_FORWARD) {
                move(Direction.FORWARD);
        }
        else if (action == Creature.Action.GO_BACKWARD) {
                move(Direction.BACKWARD);
        }
        else if (action == Creature.Action.TURN_LEFT) {
                turn(Direction.LEFT);
        }
        else if (action == Creature.Action.TURN_RIGHT) {
                turn(Direction.RIGHT);
        }
        else if (action == Creature.Action.IDLE) {
        }
        else {
            System.out.println("unrecognized action: " + action);
        }
    }

//    public void approach(Placeable target) {
//        if (bearingTo(target) > 22.5 && bearingTo(target) <= 180) {
//            turn(Creature.Direction.LEFT);
//        }
//        else if (bearingTo(target) > 180 && bearingTo(target) < 337.5) {
//            turn(Creature.Direction.RIGHT);
//        }
//        else {
//            move(Creature.Direction.FORWARD);
//        }
//    }
    
    public double getEnergy() {
        return energy;
    }
    
    public double getHealth() {
        return health;
    }
    
    public void addEnergy(double e) {
        energy += e;
        if (energy > maxEnergy) {
            energy = maxEnergy;
        }
    }
    
    public double subEnergy(double e) {
        double subAmt;
        
        if (energy < e) {
            subAmt = energy;
            energy = 0;
        }
        else {
            subAmt = e;
            energy -= e;
        }
        
        return subAmt;
    }
    
    public void subHealth(double h) {
        health -= h;
        if (health <= 0) {
            alive = false;
        }
    }
    
    public Genome getGeneCopy() {
        return genes.clone();
    }
    
    public void addAge(long a) {
        age += a;
    }
    
    public long getAge() {
        return age;
    }
    
    public Creature mateWith(Creature partner) {
        Creature child;
        
        if (mateWait != 0) {
            return null;
        }
        
        subEnergy(mateCost);
        
        if (partner.isWillingToMateWith(this)) {
            Genome childGenes = genes.crossoverWithAndMutate(partner.genes);
            
            mateWait = mateDelay;
            partner.mateWait = mateDelay;
            partner.subEnergy(mateCost);
            
            child = new Creature(world, color, childGenes);
            return child;
        }
        else {
            return null;
        }
    }

    public double bearingTo(Placeable p) {
        int xdist, ydist;
        double angle;
        double bearing;

        if (p == null) {
            return 0;
        }

        xdist = p.getX() - xcoord;
        ydist = p.getY() - ycoord;

        angle = Math.atan(((double)ydist)/xdist);
        angle = angle * 180 / Math.PI;

        if (angle < 0) {
            if (xdist < 0) {
                angle = 180 + angle;  // 360 + angle - 180
            }
            else {
                angle = 360 + angle;
            }
        }

        else if (xdist < 0) {
            angle = 180 + angle;
        }

        if (angle > heading) {
            bearing = angle - heading;
        }

        else if (angle == heading) {
            bearing = 0;
        }
        else {
            bearing = angle + 360 - heading;
        }

        return bearing;
    }
    
    public boolean isWillingToMateWith(Creature partner) {
        return true;
    }

    public CreatureNeuralNet getController() {
        return controller;
    }

    public boolean isAlive() {
        return alive;
    }
    
    public LinkedList<Creature> getCreaturesInRange(int range) {
        LinkedList<Creature> inRange = new LinkedList<Creature>();
        
        for (Creature c : world.getCreatureList()) {
            if ((c != this) && (distTo(c) <= range)) {
                inRange.add(c);
            }
        }
        
        return inRange;
    }
    
    
    public static double baseEnergy = 1000;
    public static double maxEnergy = 10000;
    public static double energyLossRate = 100;
    
    public static double maxHealth = 1000;
    public static double baseHealRate = 1;
    public static double maxHealRate = 100;
    public static double starveRate = 10;
    
    public static double mateThreshold = 1000;
    public static double mateCost = 500;
    public static int mateDelay = 100;
    
    public static int interactionRange = 25;
    public static int oldAge = 1000;

    private int heading = 0;
    private double energy = baseEnergy;
    private double health = maxHealth;
    private long age = 0;
    private int mateWait = 100;
    private boolean alive;
    private CreatureNeuralNet controller;
    private Genome genes;
    private LinkedList<Creature> interactList;
    
    public enum Direction { LEFT, RIGHT, FORWARD, BACKWARD, NONE };
    public enum Action { GO_FORWARD, GO_BACKWARD, TURN_LEFT, TURN_RIGHT, IDLE };
}
