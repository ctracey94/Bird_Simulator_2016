import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class View extends Canvas implements Runnable {
	private Controller controller;
	private Model model;
	private int delay = 17;
	private boolean isRunning=true, isAlive=true;
	private Graphics graphics;
	Image img;
	
	public void setController(Controller c) {
		controller = c;
	}
	
	public void setModel(Model m) {
		model = m;
	}
	
	public void setDelay(int d) {
		delay = d;
	}
	
	public void setRunning(boolean b) {
		isRunning = b;
	}
	
	public void setAlive(boolean b) {
		isAlive = b;
	}
	
	public int getDelay() {
		return delay;
	}

	@Override
	public void run() {
		System.out.println(delay);
		while(isAlive) {
			
			if(isRunning) {
				
				paint(graphics);
				repaint();
			}
			
			// control the speed of execution
			try {Thread.sleep(delay);}
			catch (InterruptedException e) {e.printStackTrace();}
		}
		
	}
	
	public void update(Graphics g) {
		if (img == null) {
			img = createImage(900, 600);
			graphics = img.getGraphics();
		}
		paint(graphics);
		g.drawImage(img, 0, 0, this);
	}
	
	public void paint(Graphics g) {
		if(isRunning) {
			g.setColor(Color.MAGENTA);
			g.fillRect(0, 0, 900, 600);
		}
		
		else {
			g.setColor(Color.BLUE);
			g.fillRect(0, 0, 900, 600);
			
		}
	}
}
