/**************************************************
 *    											  *
 *    Background.java						      *
 *    from Kilobolt Studios under MIT License     *
 *    adapted by Zhibin Zhang			          *
 *												  *
 **************************************************/
package BirdSimPackage;

public class Background {
	private int bgX, bgY, speedX;

	// Constructor
	public Background(int x, int y) {
		bgX = x;
		bgY = y;
		speedX = 0;
	}

	// Setters
	public int getBgX() {
		return bgX;
	}

	public int getBgY() {
		return bgY;
	}

	public int getSpeedX() {
		return speedX;
	}

	// Getters
	public void setBgX(int x) {
		this.bgX = x;
	}

	public void setBgY(int y) {
		this.bgY = y;
	}

	public void setSpeedX(int speed) {
		this.speedX = speed;
	}

	// Update function
	public void update() {
		bgX += speedX;

		if (bgX <= -900) 
			bgX += 1800;
		
		if (bgX >= 900)
			bgX += -1800;
	}
}