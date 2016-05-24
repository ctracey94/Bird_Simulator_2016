package testPackage;

/*
 * Bird.java
 *
 * Authors Jacob Brown, Conor Tracey
 *
 * This class is a subclass of the Entity class. It defines the pc
 * (or "player character") of Bird Simulator 2016, who is in fact a bird.
 */



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
    private MovStateX movStateX = MovStateX.Static;
    
    
    // Constructor for Bird class (calls Entity constructor)
    public Bird(float x, float y, int width, int height){
        super(x,y,width,height);
    }
    
    public void update(){
        // update position based on velocity
        super.update_position();
        
        // If the bird is on the ground, reset jumped boolean
        // If the bird is gliding, nullify x velocity
        if(isOnGround()){
        	
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
}