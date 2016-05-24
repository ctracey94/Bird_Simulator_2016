package BirdGamePackage.Enemies;

/*
 * Enemy.java
 *
 * Authors:	Conor Tracey
 *
 * This class is a subclass of the Entity class. It defines an superclass
 * for all the enemy NPCs to inherit from
 * 
 */

import BirdGamePackage.Entity;

public class Enemy extends Entity{
	
	// protected movement variables
	// **domainX and domainY refer to the domain within which the enemy can move
	protected float rightDomainX, leftDomainX;
	protected float speed = 5;
	protected boolean facingRight = true;
	
	// Constructor for Robot_weak class (calls Entity constructor)
    protected Enemy(float x, float y, int width, int height, float dX, float dY){
        super(x,y,width,height);
        rightDomainX = dY;
        leftDomainX = dX;
        this.velocityX = speed;
    }
    
    public void update(){
    	// update position based on velocity
    	super.update_position();
    	
    	// keep enemy within movement domain
    	if(facingRight && this.positionX > rightDomainX){
    		facingRight = false;
    		this.velocityX = -speed;
    		this.positionX = rightDomainX;
    	}
    	else if(!facingRight && this.positionX < leftDomainX){
    		facingRight = true;
    		this.velocityX = speed;
    		this.positionX = leftDomainX;
    	}
    }
}
