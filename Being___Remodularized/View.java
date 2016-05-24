package testPackage;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.net.URL;


public class View extends Applet implements Runnable {
	private Controller controller;
	private Model model;
	private int delay = 17;
	private boolean isRunning=true, isAlive=true;
	private Graphics graphics;

	
	private Bird bird = Model.getBird();
	private Image img, test_bird;
	private URL base;

	
	public void setController(Controller c){
		controller = c;
		
		base = getClass().getResource("/resources/test_bird.png");
		
		try {
			test_bird = createImage((ImageProducer)base.getContent());
		} catch (Exception e){
			System.out.println("not working");
		}
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
				
				repaint();
			}
			
			// control the speed of execution
			try {Thread.sleep(delay);}
			catch (InterruptedException e) {e.printStackTrace();}
		}
		
	}
	
	@Override
	public void update(Graphics g) {
		if (img == null) {
			img = createImage(900, 600);
			graphics = img.getGraphics();
		}
		paint(graphics);
		g.drawImage(img, 0, 0, this);

	}
	
	@Override
	public void paint(Graphics g) {
		if(isRunning) {
			g.setColor(Color.MAGENTA);
			g.fillRect(0, 0, 900, 600);
			
			g.drawImage(test_bird, 0, 0, this);
		}
		
		else {
			g.setColor(Color.BLUE);
			g.fillRect(0, 0, 900, 600);
			
		}
	}
}