package BirdSimPackage;

import java.awt.Image;
import java.awt.Rectangle;

public class Tile {

	private int tileX, tileY, speedX;
	private int type;
	
	public Image tileImage;
	
	private Background bg = Main.getBg1();
	private Bird bird = Main.getBird();
	
	private Rectangle rect;
	
	public Tile(int x, int y, int typeInt){
		tileX = x * 30 - (30 * 24);
		tileY = y * 30;
		type = typeInt;
		rect = new Rectangle();
		
		if (type == 1){
			tileImage = Main.tile01;
		} else if (type == 2){
			tileImage = Main.tile02;
		} else if (type == 3){
			tileImage = Main.tile03;
		} else if (type == 4){
			tileImage = Main.tile04;
		} else {
			type = 0;
		}
	}
		
		
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
	
	public Image getTileImage(){
		return tileImage;
	}
	
	public void setTileImage(Image tileImage){
		this.tileImage = tileImage;
	}
	
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
