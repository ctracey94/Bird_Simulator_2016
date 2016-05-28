package Elements;

/*
 * Robot_fire.java
 *
 * Authors:	Conor Tracey
 *
 * This class is a subclass of the Enemy class. It defines the strongest
 * enemy in Bird Simulator 2016.
 */

import Elements.Enemy;

public class Robot_fire extends Enemy{
	

	
    // Constructor for Robot_weak class (calls Entity constructor)
    public Robot_fire(float x, float y, int width, int height, float dX, float dY){
        super(x,y,width,height, dX, dY, 2);
    }

}