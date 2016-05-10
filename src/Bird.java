package BirdGamePackage;

public class Bird {

	// Variables for Bird object declared here.
	// PositionX and PositionY are defined by
	// the top-left corner of the Bird sprite.
	// Remember, the Y-coordinate system is flipped
	// so to jump means going 5 pixels upward (for now)
	private float positionX, positionY;
	private float velocityX, velocityY;
	private float jumpStrength = -5f;
	private int width, height;
	private boolean jumped = true;
	
	public Bird(float x, float y, int width, int height){
		positionX = x;
		positionY = y;
		this.width = width;
		this.height = height;
	}
	
	public void update(){
		// Updates positions based on any changed
		// speeds
		positionX += velocityX;
		positionY += velocityY;
		
		// If the "jumped" boolean is true, gravity
		// takes effect and pulls him to Earth
		if (jumped){
			velocityY += 0.1f;
		}
		
		// If Bird's velocityY is greater than 3,
		// this sets the "jumped" boolean to true
		if (velocityY > 3){
			jumped = true;
		}
		
		// Just some simple collision for the window -
		// If the Bird's positionY is 600 minus his height,
		// he'll stay put and the jumped variable will be false.
		if (positionY >= 600 - height){
			jumped = false;
			velocityY = 0;
			positionY = 600 - height;
		}
	}
	
	// Used (for now) by the rendering function in main.java
	// to see where to draw the bird picture
	public float getX(){
		return positionX;
	}
	
	// Used (for now) by the rendering function in main.java
	// to see where to draw the bird picture
	public float getY(){
		return positionY;
	}
	
	// If this function is invoked, the bird's speed will
	// be set to -5 pixels, meaning he'll move 5 pixels to the
	// left for every update
	public void moveLeft(){
		velocityX = -5;
	}
	
	// If this function is invoked, the bird's speed will
	// be set to 5 pixels, meaning he'll move 5 pixels to the
	// right for every update
	public void moveRight(){
		velocityX = 5;
	}
	
	// If the bird is colliding with an object or a move key
	// is released, he'll stop on the X axis
	public void stop(){
		velocityX = 0;
	}
	
	// If this function is invoked, if he's not already jumping,
	// he'll move upward whatever his jumpStrength is for each
	// update and jumped will be set to true (so he can't jump again - for now)
	public void jump(){
		if (!jumped){
			velocityY = jumpStrength;
			jumped = true;
		}
	}
}
