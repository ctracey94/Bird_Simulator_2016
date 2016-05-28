package Elements;

/**************************************************
 *    											  *
 *    Bird.java						      		  *
 *    by Conor Tracey, Jacob Brown, Zhibin Zhang  *
 *												  *
 **************************************************/

import java.awt.Rectangle;

import Elements.Entity;
import MainFrame.Model;

public class Bird extends Entity{
	
	// An enum to capture the movement state of the bird
	enum MovStateX {
		MovingRight, MovingLeft, Static
	}
    
    // Private variables for bird movement
    private float speed = 5;
    private float jumpStrength = -9f;
    private boolean	jumped = true;
    private int jumps = 0;
    private boolean gliding = false;
    private boolean glided = false;
    private boolean diveBombing = false;
    private boolean facingRight = true;
    private boolean isFalling = false;
    private boolean onGround = false;
    private MovStateX movStateX = MovStateX.Static;
    private boolean alive = true;
    
    public static Rectangle rectLeft = new Rectangle(0, 0, 0, 0);
    public static Rectangle rectTop = new Rectangle(0, 0, 0, 0);
    public static Rectangle rectRight = new Rectangle(0, 0, 0, 0);
    public static Rectangle rectBottom = new Rectangle(0, 0, 0, 0);
    public static Rectangle headFeet = new Rectangle(0, 0, 0, 0);
    
    private static Background bg1 = Model.getBg1();
    private static Background bg2 = Model.getBg2();
    
    // Constructor for Bird class (calls Entity constructor)
    public Bird(float x, float y, int width, int height){
        super(x,y,width,height);
    }
    
    public void update(){
        // update position based on velocity
        super.update_position();
        
        // If the bird is on the ground, reset jumped boolean
        // If the bird is gliding, nullify x velocity
        if(isOnGround() || onGround){
        	
        	//if the bird was gliding, end his glide
        	if(gliding || glided){
        		
        		gliding = false;
        		glided = false;
        		
        		//stop bird if no movement key is being pressed, otherwise 
        		//transition back into normal x-axis movement
        		if(movStateX == MovStateX.Static){
        			super.xStop();
        		} else if(movStateX == MovStateX.MovingRight){
        			this.velocityX = speed;	
        		} else if(movStateX == MovStateX.MovingLeft){
        			this.velocityX = -speed;
        		}

        	}
        	
        	//reset jump ability
            jumped = false;
            jumps = 0;
            
            //reset diveBomb ability
            diveBombing = false;
        }
        
        // Gravity logic
        // if the bird is gliding, he isn't as affected by gravity
        if(gliding){
        	this.velocityY += (Environment.GRAVITY*0.3);
        } else {
        	this.velocityY += Environment.GRAVITY;
        }
        
        if(diveBombing){
        	this.velocityY += Environment.GRAVITY*4;
        }
        
        
        // Background Scrolling Information
        if (alive){
	        if (super.velocityX == 0 || super.velocityX < 0){
	        	bg1.setSpeedX(0);
	        	bg2.setSpeedX(0);
	        }
	        
	        if (super.velocityX > 0 && super.positionX > 250){
	        	bg1.setSpeedX((int)-super.velocityX);
	        	bg2.setSpeedX((int)-super.velocityX);
	        	super.positionX = 250;
	        } else if (super.velocityX < 0 && super.positionX <= 200){
	        	bg1.setSpeedX((int)-super.velocityX);
	        	bg2.setSpeedX((int)-super.velocityX);
	        	super.positionX = 200;
	        }
        } else {
        	bg1.setSpeedX(0);
        	bg2.setSpeedX(0);
        }
        
        if (jumped && super.velocityY > 0.4f){
        	isFalling = true;
        } else {
        	isFalling = false;
        }
        
        // Rectangle Information
        rectLeft.setRect(super.positionX + 16, super.positionY + 20, 8, 14);
        rectTop.setRect(super.positionX + 27, super.positionY + 12, 14, 8);
        rectRight.setRect(super.positionX + 42, super.positionY + 20, 8, 14);
        rectBottom.setRect(super.positionX + 27, super.positionY + 38, 14, 8);
        headFeet.setRect(super.positionX + 0, super.positionY + 0, 61, 44);

        
    }
    
    // moves bird left at a rate specified by the speed variable (unless gliding)
    public void moveLeft(){
    	if(!gliding){
    		super.moveLeft(speed);
    		facingRight = false;
    	}
    	
		movStateX = MovStateX.MovingLeft;
    }
    
    // moves bird right at a rate specified by the speed variable (unless gliding)
    public void moveRight(){
    	if(!gliding){
    		super.moveRight(speed);
    		facingRight = true;
    	}
    	
		movStateX = MovStateX.MovingRight;
    }
    
    // Stops bird on x-axis
    public void stop(){
    	if(!gliding){
            super.xStop();
    	}
    	
        movStateX = MovStateX.Static;
    }
    
    // A method allowing the bird to jump.
    // if the bird has already jumped, he can double jump
    // if he's close to the apex of his jump arc, his double jump
    // gives him a little boost.
    public void jump(){
        if (jumps == 0){
        	jumps ++;
            jumped = true;
            gliding = false;
            onGround = false;
        	
            this.velocityY = jumpStrength;

        } else if (jumps == 1){
        	jumps ++;
        	gliding = false;
        	
        	if(this.velocityY >= 0){
        		this.velocityY = jumpStrength + 0.5f;
        	} else if(this.velocityY >= -1){
        		this.velocityY = jumpStrength - 1.25f;
        	} else if(this.velocityY >= -3){
        		this.velocityY = jumpStrength;
        	} else {
        		this.velocityY = jumpStrength - 1f;
        	}
        } else {
        	//do nothing; no more jumps!
        }
    }
    
    // A method that allows the bird to glide in the direction he's facing
    // if he's airborne
    public void glide(){
    	if(!gliding && jumped){
    		gliding = true;
    		glided = true;
    		
    		super.completeStop();		// nullify velocity for motion control
    		
    		if(facingRight){
    			velocityX = speed*2.5f;
    		} else {
    			velocityX = -(speed*2.5f);
    		}
    	}
    }
    
    // A method that allows the bird to divebomb to the ground if he's gliding
    public void diveBomb(){
    	if(gliding){
    		diveBombing = true;
    		this.xStop();
    		movStateX = MovStateX.Static;
    		this.velocityY = speed;
    	}
    }
    
    
    
    // A getter method for the gliding boolean
    // Tells us if the bird is gliding or not
    public boolean isGliding(){
    	return gliding;
    }
    
    public boolean isMovingLeft(){
    	return movStateX == MovStateX.MovingLeft;
    }
    
    public boolean isMovingRight(){
    	return movStateX == MovStateX.MovingRight;
    }
    
    public boolean isStatic(){
    	return movStateX == MovStateX.Static;
    }
    
    public boolean isFacingRight(){
    	return facingRight;
    }
    
    public void setFacingRight(boolean b){
    	facingRight = b;
    }
    
    public boolean isFalling(){
    	return isFalling;
    }
    
    public boolean isJumped(){
    	return jumped;
    }
    
    public boolean isDivebomb(){
    	return diveBombing;
    }
    
    public void setJumped(boolean jumped){
    	this.jumped = jumped;
    }
    
    public void setSpeedY(float speedY){
    	super.velocityY = speedY;
    }
    
    public void setSpeedX(float speedX){
    	super.velocityX = speedX;
    }
    
    public float getSpeedY(){
    	return super.velocityY;
    }
    
    public void setCenterX(float centerX){
    	super.positionX = centerX;
    }
    
    public void setCenterY(float centerY){
    	super.positionY = centerY;
    }
    
    public void setOnGround(boolean onGround){
    	this.onGround = onGround;
    }
    
    // kill function
    public void kill(){
    	alive = false;
    	this.velocityY = -8;
    	jumped = true;
    	jumps = 2;
    	gliding = false;
    	this.movePastEdges = true;
    }
    
    public boolean isAlive(){
    	return alive;
    }
    
    public void setJumps(int njumps){
    	if(njumps >= 0 && njumps <= 2){
        	jumps = njumps;
    	}
    }
    
}