package BirdGamePackage;

/*
 * Entity.java
 *
 * Authors:	Conor Tracey, Jacob Brown
 *
 * This class is a superclass for all creatures (pc and npc), as well as
 * objects that the pc will interact with (obstacles, environment, etc)
 * This class is intended to capture all the basic variables and methods
 * that all in-game entities will need (such as coordinates, hitbox, etc)
 */

import BirdGamePackage.Environment;

public class Entity {
    
    // Location | Movement Variables
    //**Note: positionX & positionY coordinates of top left corner of Entity
    //**Note: Y-coordinate system is flipped (a negative y indicates up)
    protected float positionX, positionY;
    protected float velocityX, velocityY;
    protected int width, height;
    
    protected boolean alive = true;
    
    // Constructor
    protected Entity(float x, float y, int width, int height){
        this.positionX = x;
        this.positionY = y;
        this.width = width;
        this.height = height;
    }
    
    // A method for updating position based on velocity
    protected void update_position(){
        this.positionX += this.velocityX;
        this.positionY += this.velocityY;
        
        // collision logic for floor of game environment
        if ((this.positionY >= Environment.FLOOR - this.height) && alive){
            this.velocityY = 0;
            this.positionY = Environment.FLOOR - this.height;
        }
        
        // collision logic for right & left edges of game
        if (this.positionX >= Environment.RIGHT_EDGE - this.width){
            this.velocityX = 0;
            this.positionX = Environment.RIGHT_EDGE - this.width;
        }
        
        if (this.positionX < Environment.LEFT_EDGE){
            this.velocityX = 0;
            this.positionX = Environment.LEFT_EDGE;
        }
        
        
    }
    
    // A method for moving an Entity left
    protected void moveLeft(float speed){
        this.velocityX = -speed;
    }
    
    // A method for moving an Entity right
    protected void moveRight(float speed){
        this.velocityX = speed;
    }
    
    // A method for stopping an Entity's x-axis movement
    protected void xStop(){
        this.velocityX = 0;
    }
    
    // A method for stopping an Entity's y-axis movement
    protected void yStop(){
        this.velocityY = 0;
    }
    
    // A method for completely stopping
    protected void completeStop(){
    	this.velocityX = 0;
    	this.velocityY = 0;
    }
    
    // A method for determining if an Entity is on the ground
    protected boolean isOnGround(){
        if (this.positionY >= Environment.FLOOR - this.height){
            return true;
        }
        return false;
    }
    
    // A method for getting positionX of Entity
    public float getX(){
        return this.positionX;
    }
    
    // A method for getting positionX of Entity
    public float getY(){
        return this.positionY;
    }
    
    // A method to kill an Entity
    public void kill(){
    	alive = false;
    }
    
    
    
}
