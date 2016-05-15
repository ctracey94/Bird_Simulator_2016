package BirdGamePackage;

/*
 * Bird.java
 *
 * Authors Jacob Brown, Conor Tracey
 *
 * This class is a subclass of the Entity class. It defines the pc
 * (or "player character") of Bird Simulator 2016, who is in fact a bird.
 */

import BirdGamePackage.Entity;

public class Bird extends Entity{
    
    // Private variables for bird movement
    private float speed = 5;
    private float jumpStrength = -5f;
    private boolean	jumped = true;
    
    // Constructor for Bird class (calls Entity constructor)
    public Bird(float x, float y, int width, int height){
        super(x,y,width,height);
    }
    
    public void update(){
        // update position based on velocity
        super.update_position();
        
        // If the bird is on the ground, reset jumped boolean
        if(isOnGround()){
            jumped = false;
        }
        
        // If the jumped boolean is true, gravity
        // takes effect and pulls him to Earth
        if (jumped){
            this.velocityY += 0.1f;
        }
    }
    
    // moves bird left at a rate specified by the speed variable
    public void moveLeft(){
        super.moveLeft(speed);
    }
    
    // moves bird right at a rate specified by the speed variable
    public void moveRight(){
        super.moveRight(speed);
    }
    
    // Stops bird on x-axis
    public void stop(){
        super.xStop();
    }
    
    // If this function is invoked, if he's not already jumping,
    // he'll move upward whatever his jumpStrength is for each
    // update and jumped will be set to true (so he can't jump again - for now)
    public void jump(){
        if (!jumped){
            this.velocityY = jumpStrength;
            jumped = true;
        }
    }
}