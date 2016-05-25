package BirdSimPackage;

import java.util.ArrayList;

import BirdSimPackage.Enemies.Enemy;
import BirdSimPackage.Enemies.Robot_weak;

/*
 * Environment.java
 *
 * Author: Conor Tracey
 *
 * This class is intended to house information relating to the Environment of
 * Bird Simulator 2016.
 */

public class Environment {
    //global variable for the bottom most point on the map
    public static int FLOOR = 600;
    public static int CEILING = 0;
    public static int RIGHT_EDGE = 900;
    public static int LEFT_EDGE = 0;
    public static float GRAVITY = .4f;
    public static ArrayList<Enemy> ENEMIES;
    
    // Sets the level of the environment
    public Environment(String level){
    	if(level == "demo"){
    		
    		// Enemy list for demo level
    		ENEMIES = new ArrayList<Enemy>();
    	
    		ENEMIES.add(new Robot_weak(450, 550, 30, 30, 300, 600));
    		
    		// Object list for demo level
    		// TODO: make objects and make a list of them here
    	} else {
    		System.out.println("no existing level specified");
    	}
    }
     
}
