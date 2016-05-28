/**************************************************
 *    											  *
 *    Tile.java						       	      *
 *    from Kilobolt Studios under MIT License 	  *
 *    adapted by Jacob Brown and Conor Tracey     *
 *												  *
 **************************************************/
package Elements;

import java.awt.Rectangle;

import MainFrame.Model;

public class Tile {

	// Private & public variables
	private int tileX, tileY, speedX;
	private int type;
	
	private Background bg = Model.getBg1();
	private Bird bird = Model.getBird();
	
	private Rectangle rect;
	
	// Constructor
	public Tile(int x, int y, int typeInt){
		tileX = x * 30 - (30 * 24);
		tileY = y * 30;
		type = typeInt;
		rect = new Rectangle();
	}
		
	// Update loop
	public void update(){
		if(bird.isAlive()){
			speedX = bg.getSpeedX();
			tileX += speedX;
			rect.setBounds(tileX, tileY, 30, 30);
				
			if (rect.intersects(Bird.headFeet) && type != 0){
				checkVerticalCollision(Bird.rectTop, Bird.rectBottom);
				checkSideCollision(Bird.rectLeft, Bird.rectRight);
			}
		}
	}
		
	// Getters & setters
	public int getTileX(){
		return tileX;
	}
	
	public void setTileX(int tileX){
		this.tileX = tileX;
	}
		
	public int getTileY(){
		return tileY;
	}
	
	public void setTileY(int tileY){
		this.tileY = tileY;
	}
	
	public int getTileType() {
		return type;
	}
	
	// Checks collisions for bird's head and feet
	public void checkVerticalCollision(Rectangle rectTop, Rectangle rectBottom){
		if (rectTop.intersects(rect) && type != 0){
			bird.setSpeedY(0);
			bird.setCenterY(tileY + 42);
		}
		
		if (rectBottom.intersects(rect) && type != 0){
			bird.setJumped(false);
			bird.setSpeedY(0);
			bird.setCenterY(tileY - 44);
			bird.setOnGround(true);
		}
	}
	
	// Checks collisions for bird's sides
	public void checkSideCollision(Rectangle rectLeft, Rectangle rectRight){
		if (rectLeft.intersects(rect) && type != 0){
			bird.setSpeedX(0);
			bird.setCenterX(tileX + 16);
		}
		
		if (rectRight.intersects(rect) && type != 0){
			bird.setSpeedX(0);
			bird.setCenterX(tileX - 47);
		}
	}
}