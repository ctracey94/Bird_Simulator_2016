package Elements;

/*
 * Robot_weak.java
 *
 * Authors:	Conor Tracey
 *
 * This class is a subclass of the Enemy class. It defines a weak
 * enemy NPC of Bird Simulator 2016.
 */

import Elements.Enemy;

public class Robot_weak extends Enemy{
	
    // Constructor for Robot_weak class (calls Entity constructor)
    public Robot_weak(float x, float y, int width, int height, float dX, float dY){
        super(x,y,width,height, dX, dY, 0);
    }

}