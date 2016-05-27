package BirdSimPackage.Enemies;

import java.awt.Rectangle;

import BirdSimPackage.Background;
import BirdSimPackage.Bird;

/*
 * Enemy.java
 *
 * Authors:	Conor Tracey
 *
 * This class is a subclass of the Entity class. It defines an superclass
 * for all the enemy NPCs to inherit from
 * 
 */

import BirdSimPackage.Entity;
import BirdSimPackage.Main;

public class Enemy extends Entity{
	
	// protected movement variables
	// **domainX and domainY refer to the domain within which the enemy can move
	protected float rightDomainX, leftDomainX;
	protected float speedLvl = 3;
	protected float speed;
	protected float realSpeed = speed;
	protected boolean facingRight = true;
	private Background bg = Main.getBg1();
	public Bird bird = Main.getBird();
	private int type;
	private Rectangle rect;
	public boolean alive = true;
	
	// Constructor for Robot_weak class (calls Entity constructor)
    protected Enemy(float x, float y, int width, int height, float dX, float dY, int typeInt){
        super(x,y,width,height);
        rightDomainX = dY;
        leftDomainX = dX;
        
        type = typeInt;
        rect = new Rectangle();
        
        this.velocityX = 0;
        
    	super.setMovePastEdges(true);
    }
    
    public void changeDomain(float dX, float dY){
    	rightDomainX = dY;
    	leftDomainX = dX;
    }
    
    public void update(){
    	

    	
    	super.update_position();
    	
    	if (alive){
	    	// update position based on velocity
	    	
        	this.velocityX = realSpeed;
	    	rect.setBounds((int)this.positionX, (int)this.positionY, 70, 50);
	    	
	    	float bgSpeed = bg.getSpeedX();
	    	
	    	// update domain positions
	    	rightDomainX += bgSpeed;
	    	leftDomainX += bgSpeed;
	    	
	    	// keep enemy within movement domain
	    	if(this.positionX > rightDomainX && alive){
	    		facingRight = false;
	    		this.positionX = rightDomainX;
	    	}
	    	else if(this.positionX < leftDomainX  && alive){
	    		facingRight = true;
	    		this.positionX = leftDomainX;
	    	}
	    	
	    	if(facingRight){
	    		speed = speedLvl;
	    		realSpeed = speed +bgSpeed;
	    	} else {
	    		speed = -speedLvl;
	    		realSpeed = speed + bgSpeed;
	    	}
	    	
	    	if(rect.intersects(Bird.headFeet) && bird.isAlive()){
	    		checkVerticalCollision(Bird.rectBottom);
	    		checkSideCollision(Bird.rectLeft, Bird.rectRight);
	    	}
    	}
	    	
	    	
    	if (!alive && this.positionY < 900){
    		this.velocityY += 1;
    	} else if (!alive && this.positionY >= 900){
    		this.positionX = -50;
    		this.positionY = -50;
    		this.velocityY = 0;
    		this.velocityX = 0;
    		speedLvl = 0;
    		alive = true;
    		
    	}
    	
    	
    	
    	
 
    }
    
    // method for getting directional boolean
    public boolean getFacingRight(){
    	return facingRight;
    }
    
    // method for getting type of Enemy
    public int getType(){
    	return type;
    }
    
    public void kill(){
		bird.setJumped(false);
		bird.setJumps(0);
		bird.setSpeedY(-10);
    	this.alive = false;
    	this.velocityY = -8;
		this.setMovePastEdges(true);
    }
    
	public void checkVerticalCollision(Rectangle rectBottom){
		if (rectBottom.intersects(rect)){
			if(type == 0){
				this.kill();
			} else if (type == 1){
				if(bird.isDivebomb()){
					this.kill();
				} else {
					bird.setJumps(0);
					bird.setSpeedY(-7);
				}
			} else if(type == 2){
				bird.kill();
			}
		}
	}
	
	public void checkSideCollision(Rectangle rectLeft, Rectangle rectRight){
		if (rectLeft.intersects(rect) && alive){
			bird.kill();
			System.out.println("side collision");
		}
		
		if (rectRight.intersects(rect) && alive){
			bird.kill();
			System.out.println("side collision");
		}
	}
}