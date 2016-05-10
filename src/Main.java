// Based on Kilobolt Java game tutorial framework and modified

package BirdGamePackage;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import BirdGamePackage.Bird;

public class Main extends Applet implements Runnable, KeyListener {

	// These different GameStates allow the update and rendering
	// functions to know what to update and what you render
	enum GameState {
		Title, Running, Dead
	}

	// Automatically sets the state to Running
	GameState state = GameState.Running;

	// Declares a Bird object (main character) and
	// and a picture to go along with that object
	private static Bird bird;
	private Image image, test_bird;

	private Graphics graphics;
	
	// Used to get test_bird.png from the "resources" folder
	private URL base;

	// First called to initialize the applet
	@Override
	public void init() {
		
		int gameWidth = 900;
		int gameHeight = 600;

		// Sets the window's dimensions, background, and info. Note that
		// we'll display our own background in the game. If we didn't,
		// any sprite that moved would have a weird strobing effect.
		setSize(gameWidth, gameHeight);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Bird Challenge 2016");
		
		try {
			base = getDocumentBase();
		} catch (Exception e) {
			// Insert exception here
		}

		// Any assets would be setup here (sprites, backgrounds, etc)
		// At some point, I think it would be a good idea to create
		// an Asset Initializer class that does this work for us.
		test_bird = getImage(base, "resources/test_bird.png");
		
	}

	// Start function creates any new objects, tiles, and start
	// the thread.
	@Override
	public void start() {
		
		// Creates Bird object and assigns him to the top-left
		// corner of the screen (0, 0). The coordinate system
		// starts up there. His width and height are set to 32.
		bird = new Bird(0, 0, 32, 32);
		// TODO Tile initialization
		//try {
		//	loadMap("resources/map1.txt");
		//} catch (IOException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}

		Thread thread = new Thread(this);
		thread.start();
	}

	// Load map function for the tiles
	private void loadMap(String filename) throws IOException {
		// TODO you know do this
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	// This is the game loop - as the GameState is set to
	// Running, it'll update the Bird object, animate anything,
	// and render/repaint the screen. The Thread.sleep(17) is
	// to steady the framerate
	public void run() {
		if (state == GameState.Running) {

			while (true) {
				bird.update();
			
				animate();
				repaint();
				try {
					Thread.sleep(17);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// Used for animation
	public void animate() {
		// TODO
	}

	// Update function - ignore the image/g.drawImage stuff for now.
	// The important thing is that the paint function is called to
	// render everything on-screen.
	@Override
	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			graphics = image.getGraphics();
		}
		
		paint(graphics);
		g.drawImage(image, 0, 0, this);
	}

	// While the GameState is set to Running, it'll draw a big
	// magenta rectangle first, then draw the bird on top of it.
	// This is how rendering will work - anything that you want
	// drawn first (like backgrounds), draw it earlier in this function.
	// Then draw the sprites afterwards so they'll appear in front.
	@Override
	public void paint(Graphics g) {
		if (state == GameState.Running) {
			g.setColor(Color.MAGENTA);
			g.fillRect(0, 0, 900, 600);
			
			// First argument is the image, then the Bird object's X and
			// Y coordinates.
			g.drawImage(test_bird, (int)bird.getX(), (int)bird.getY(), this);
			
		} else if (state == GameState.Dead) {
			g.setColor(Color.BLUE);
			g.fillRect(0, 0, 900, 600);
		}
	}

	// keyPressed checks to see if any of these are pressed and acts
	// accordingly
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		
		// Right now, W is jump. I think it would be good to have
		// W be the jump, the double-jump, and the triple-jump, and
		// have K be the glide button.
		case KeyEvent.VK_W:
			bird.jump();
			System.out.println("Jumped");
			break;

		// Move Left
		case KeyEvent.VK_A:
			bird.moveLeft();
			System.out.println("Moving left");
			break;

		// Insert divebomb stuff here
		case KeyEvent.VK_S:
			break;

		// Move right
		case KeyEvent.VK_D:
			bird.moveRight();
			System.out.println("Moving right");
			break;

		// Possibly insert glide function here
		case KeyEvent.VK_K:
			break;

		}

	}

	// keyReleased checks if a previously pressed key is released.
	// Usually used to stop movement (like moving left and right)
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			break;

		case KeyEvent.VK_A:
			bird.stop();
			break;
			
		case KeyEvent.VK_S:
			break;

		case KeyEvent.VK_D:
			bird.stop();
			break;

		case KeyEvent.VK_K:
			break;

		}

	}

	// Autogenerated - ignore (for now, at least)
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

}