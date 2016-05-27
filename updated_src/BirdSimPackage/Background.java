package BirdSimPackage;

public class Background {
	private int bgX, bgY, speedX;

	public Background(int x, int y) {
		bgX = x;
		bgY = y;
		speedX = 0;
	}

	// setters
	public int getBgX() {
		return bgX;
	}

	public int getBgY() {
		return bgY;
	}

	public int getSpeedX() {
		return speedX;
	}

	// getters
	public void setBgX(int x) {
		this.bgX = x;
	}

	public void setBgY(int y) {
		this.bgY = y;
	}

	public void setSpeedX(int speed) {
		this.speedX = speed;
	}

	public void update() {
		bgX += speedX;

		if (bgX <= -900) 
			bgX += 1800;
		
		if (bgX >= 900)
			bgX += -1800;
	}
}