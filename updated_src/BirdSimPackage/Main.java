// Based on Kilobolt Java game tutorial framework and modified

package BirdSimPackage;

// standard lib imports
import java.applet.Applet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

// BirdSimPackage local imports
import BirdSimPackage.Bird;
import BirdSimPackage.Environment;
import BirdSimPackage.Background;
import BirdSimPackage.Enemies.Enemy;
import BirdSimPackage.Enemies.Robot_weak;
import BirdSimPackage.Enemies.Robot_helmet;
import BirdSimPackage.Enemies.Robot_fire;

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
    private static ArrayList<Enemy> enemies;
    private Image image, bg, test_enemy, currentSprite;
    private Image bird_stand_right, bird_walk_right_1, bird_walk_right_2;
    private Image bird_stand_left, bird_walk_left_1, bird_walk_left_2;
    private Image bird_glide_left_1, bird_glide_left_2, bird_glide_right_1, bird_glide_right_2;
    private Animation birdWalkRight, birdWalkLeft, birdGlideRight, birdGlideLeft;
    private static Background bg1, bg2;
    
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

        bird_stand_right = getImage(base, "resources/bird/walkcycle/bird_walk_right_2.png");
        bird_walk_right_1 = getImage(base, "resources/bird/walkcycle/bird_walk_right_1.png");
        bird_walk_right_2 = getImage(base, "resources/bird/walkcycle/bird_walk_right_2.png");
        bird_stand_left = getImage(base, "resources/bird/walkcycle/bird_walk_left_2.png");
        bird_walk_left_1 = getImage(base, "resources/bird/walkcycle/bird_walk_left_1.png");
        bird_walk_left_2 = getImage(base, "resources/bird/walkcycle/bird_walk_left_2.png");
        
        bird_glide_left_1 = getImage(base, "resources/bird/glide/bird_glide_left_1.png");
        bird_glide_left_2 = getImage(base, "resources/bird/glide/bird_glide_left_2.png");
        bird_glide_right_1 = getImage(base, "resources/bird/glide/bird_glide_right_1.png");
        bird_glide_right_2 = getImage(base, "resources/bird/glide/bird_glide_right_2.png");
        
        birdWalkRight = new Animation();
        birdWalkRight.addFrame(bird_walk_right_1, 50);
        birdWalkRight.addFrame(bird_walk_right_2, 50);
        
        birdWalkLeft = new Animation();
        birdWalkLeft.addFrame(bird_walk_left_1, 50);
        birdWalkLeft.addFrame(bird_walk_left_2, 50);
        
        birdGlideRight = new Animation();
        birdGlideRight.addFrame(bird_glide_right_1, 50);
        birdGlideRight.addFrame(bird_glide_right_2, 50);
        
        birdGlideLeft = new Animation();
        birdGlideLeft.addFrame(bird_glide_left_1, 50);
        birdGlideLeft.addFrame(bird_glide_left_2, 50);
        
        currentSprite = bird_stand_right;
        bg = getImage(base, "resources/background.png");
        test_enemy = getImage(base, "resources/robot_weak_walk0000.png");
        
    }
    
    // Start function creates any new objects, tiles, and start
    // the thread.
    @Override
    public void start() {
        
        // Creates Bird object and assigns him to the top-left
        // corner of the screen (0, 0). The coordinate system
        // starts up there. His width and height are set to 32.
        bg1 = new Background(0, 0);
        bg2 = new Background(900, 0);
        bird = new Bird(0, 0, 61, 44);
        enemies = new ArrayList<Enemy>();						//  CCT: make arrayList for all enemies
        enemies.add(new Robot_weak(450, 550, 30, 30, 300, 600)); // CCT: add enemy instantiations here, for now they will all be represented by weak_robots image
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
         // not sure of format of intended txt input file
        int[][] tileMap = new int[20][30]; // int[row][col] are changable
        
        String line = " ";
        int row_counter = 0;
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader bf = new BufferedReader(fr);
            
            while((line = bf.readLine()) != null) {
                String[] vals = line.trim().split("\\s+");

                for (int i = 0; i < 30; i++ )
                    tileMap[row_counter][i] = Integer.parseInt(vals[i]);

                row_counter++;
            }
            
        }
        catch(FileNotFoundException e) {e.printStackTrace();}
        catch(IOException e) {e.printStackTrace();}
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
                
                for(int i=0; i<enemies.size(); i++){		// CCT
                	enemies.get(i).update();				// CCT: update all enemies
                }
                
                if (bird.isMovingRight() && !bird.isGliding()){
                	currentSprite = birdWalkRight.getImage();
                } else if (bird.isMovingLeft() && !bird.isGliding()){
                	currentSprite = birdWalkLeft.getImage();
                } else if (bird.isGliding() && bird.isFacingRight()){
                	currentSprite = birdGlideRight.getImage();
                } else if (bird.isGliding() && !bird.isFacingRight()){
                	currentSprite = birdGlideLeft.getImage();
                } else if (!bird.isGliding() && bird.isFacingRight()){
                	currentSprite = bird_stand_right;
                } else if (!bird.isGliding() && !bird.isFacingRight()){
                	currentSprite = bird_stand_left;
                }
                
                bg1.update();
                bg2.update();

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
        birdWalkRight.update(17);
        birdWalkLeft.update(17);
        birdGlideRight.update(20);
        birdGlideLeft.update(20);
        //System.out.println(birdWalkRight.curFrame());
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
            g.drawImage(bg, bg1.getBgX(), bg1.getBgY(), this);
            g.drawImage(bg, bg2.getBgX(), bg2.getBgY(), this);
            
            for(int i=0; i<enemies.size(); i++){														// CCT: 
            	g.drawImage(test_enemy, (int)enemies.get(i).getX(), (int)enemies.get(i).getY(), this);	// CCT: draw all enemies
            }
            
            g.drawImage(currentSprite, (int)bird.getX(), (int)bird.getY(), this);
            
        } else if (state == GameState.Dead) {
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, 900, 600);
        }
    }
    
    // keyPressed checks to see if any of these are pressed and acts
    // accordingly
    public void keyPressed(KeyEvent e) {
        
        switch (e.getKeyCode()) {
                
                // Jump / Double Jump
            case KeyEvent.VK_W:
                bird.jump();
                System.out.println("Jumped");
                break;
                
                // Move Left
            case KeyEvent.VK_A:
                bird.moveLeft();
                bird.setFacingRight(false);
                System.out.println("Moving left");           		
	

                break;
                
            case KeyEvent.VK_S:
            	bird.diveBomb();
                break;
                
                // Move right
            case KeyEvent.VK_D:
            	bird.moveRight();
            	bird.setFacingRight(true);
                System.out.println("Moving right");	
	
            	
                break;
                
                // Glide
            case KeyEvent.VK_K:
            	bird.glide();
            	System.out.println("Gliding");
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
            	if(bird.isMovingLeft()){
            		bird.stop();     
            	}
                break;
                
            case KeyEvent.VK_S:
                break;
                
            case KeyEvent.VK_D:
            	if(bird.isMovingRight()){
            		bird.stop();       
            	}
                break;
                
            case KeyEvent.VK_K:
                break;
                
        }
        
    }
    
    // Autogenerated - ignore (for now, at least)
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }
    
    public static Background getBg1(){
    	return bg1;
    }
    
    public static Background getBg2(){
    	return bg2;
    }
    
}