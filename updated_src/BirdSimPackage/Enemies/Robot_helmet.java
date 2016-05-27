package BirdSimPackage.Enemies;

/*
 * Robot_helmet.java
 *
 * Authors:	Conor Tracey
 *
 * This class is a subclass of the Enemy class. It defines an
 * enemy NPC of Bird Simulator 2016 which is stronger than Robot_weak.
 */

import BirdSimPackage.Enemies.Enemy;

public class Robot_helmet extends Enemy{
	
    // Constructor for Robot_weak class (calls Entity constructor)
    public Robot_helmet(float x, float y, int width, int height, float dX, float dY){
        super(x,y,width,height, dX, dY, 1);
    }

}
