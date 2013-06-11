/*
 * World.java
 *
 * class representing the simulation world
 *
 * Created on October 30, 2004, 1:05 AM
 */

package animatsim;

import java.awt.*;
import java.util.*;


public class World {
    
    /** Creates a new instance of World */
    public World() {
        int i, j;
        r = new Random();
        
        trackme = new Placeable();
        
        // = g;
        
        tiles = new WorldTile[worldSizeX][worldSizeY];
        creatures = new LinkedList<Creature>();
        foods = new LinkedList<Food>();
        addList = new LinkedList<Creature>();
        
        for (i = 0; i < worldSizeX; i++) {
            for (j = 0; j < worldSizeY; j++) {
                tiles[i][j] = new WorldTile(i, j);
            }
        }
        
        for (i = 0; i < maxFood; i++) {
            insertRandomFood();
        }
    }
    
    public void insertCreature(Creature c, int x, int y) {
        creatures.add(c);
        c.setX(x);
        c.setY(y);
        tiles[x][y].hasCreature = true;
    }
    
    public Food insertFood(int x, int y) {
        Food f = new Food(this, x, y)   ;
        foods.add(f);
        return f;
    }

    public Food insertRandomFood() {
        return this.insertFood(r.nextInt(worldSizeX - 1), r.nextInt(worldSizeY - 1));
    }
    
    public void moveCreature(int fromX, int fromY, int toX, int toY) {
        tiles[fromX][fromY].hasCreature = false;
        tiles[toX][toY].hasCreature = true;
    }
    
    public LinkedList<Creature> getCreatureList() {
        return creatures;
    }
    
    public LinkedList<Food> getFoodList() {
        return foods;
    }
    
    public void update() {
        Creature c, child;
        Food f;
        int i;

        LinkedList<Creature> deadCreatures = new LinkedList<Creature>();

        ListIterator<Creature> itr = creatures.listIterator();
        addList.clear();
        
        while (itr.hasNext()) {
            //System.out.println("beginning creature analysis");
            c = itr.next();
         
            //System.out.println("calling creature update");
            c.update();
        }
     
        //System.out.println("checking for dead creatures...");
        itr = creatures.listIterator();
        Creature targetCreature = null;
        while (itr.hasNext()) {
            c = itr.next();
            if (! c.isAlive()) {
                deadCreatures.add(c);
            }
            else if (c.getEnergy() >= Creature.mateThreshold) {
                if (targetCreature == null) {
                    targetCreature = c;
                }
                else {
                    child = c.mateWith(targetCreature);
                    if (child != null) {
                        addList.add(child);
                        targetCreature = null;
                    }
                    else {
                        targetCreature = c;
                    }
                }
            }
        }

        for (Creature dead : deadCreatures) {
            creatures.remove(dead);
        }
        
        //System.out.println("processing addList...");
        for (Creature addC : addList) {
            if (creatures.size() < maxCreatures) {
                //System.out.println("\tadding creature from addList");
                insertCreature(addC, r.nextInt(worldSizeX - 1), r.nextInt(worldSizeY - 1));
            }
            else {
                break;
            }
        }
        //System.out.println("finished processing addList");
        
        if (creatures.size() < minCreatures) {
            while (creatures.size() < initialCreatures) {
                insertCreature(new Creature(this, Color.RED), r.nextInt(699), r.nextInt(699));
            }
        }
        
        ListIterator<Food> foodItr = foods.listIterator();
        
        while (foodItr.hasNext()) {
            f = foodItr.next();
            if (f.getAmount() == 0) {
                foodItr.remove();
                foodItr.add(new Food(this, r.nextInt(World.worldSizeX - 1),
                        r.nextInt(World.worldSizeY - 1)));
            }
        }
    }
    
    public boolean insertChild(Creature child) {
        //System.out.println("inserting child");
        if (creatures.size() < maxCreatures) {
            addList.add(child);
            //System.out.println("\tchild added to addList");
            return true;
        }
        else {
            //System.out.println("\ttoo many creatures!");
            return false;
        }
    }
    
    
    private WorldTile[][] tiles;
    private LinkedList<Creature> creatures;
    private LinkedList<Creature> addList;
    private LinkedList<Food> foods;
    
    public Random r;
    
    public static final int maxCreatures = 500;
    public static final int worldSizeX = 700;
    public static final int worldSizeY = 700;
    public static final int maxFood = 20;
    public static final int initialCreatures = 20;
    public static final int minCreatures = 5;
    
    public static Placeable trackme;
    
    public Graphics drawer;
}
