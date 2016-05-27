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

import BirdSimPackage.Background;
import BirdSimPackage.Bird;
import BirdSimPackage.Environment;
import BirdSimPackage.Enemies.Enemy;
import BirdSimPackage.Enemies.Robot_fire;
import BirdSimPackage.Enemies.Robot_helmet;
import BirdSimPackage.Enemies.Robot_weak;

public class Main extends Applet implements Runnable, KeyListener {
    
    // These different GameStates allow the update and rendering
    // functions to know what to update and what you render
    enum GameState {
        Title, Running, Dead
    }
    
    // Automatically sets the state to Running
    GameState state = GameState.Title;
    
    // Declares a Bird object (main character) and
    // and a picture to go along with that object
    private static Bird bird;
    private static Robot_fire robotFire;
    private static Robot_helmet robotHelmet;
    private static Robot_weak robotWeak;
    private static ArrayList<Enemy> enemies;
    private Image image, bg, currentSprite;
    private Image bird_stand_right, bird_walk_right_1, bird_walk_right_2;
    private Image bird_stand_left, bird_walk_left_1, bird_walk_left_2;
    private Image bird_glide_left_1, bird_glide_left_2, bird_glide_right_1, bird_glide_right_2;
    private Image bird_jump_right_1, bird_jump_right_2, bird_jump_left_1, bird_jump_left_2;
    private Image bird_divebomb_right_1, bird_divebomb_right_2, bird_divebomb_right_3;
    private Image bird_divebomb_left_1, bird_divebomb_left_2, bird_divebomb_left_3;
    private Image robot_fire_walk_left_1, robot_fire_walk_left_2, robot_fire_walk_left_3, robot_fire_walk_left_4, robot_fire_walk_left_5, robot_fire_walk_left_6, robot_fire_walk_left_7, robot_fire_walk_left_8, robot_fire_walk_left_9, robot_fire_walk_left_10, robot_fire_walk_left_11, robot_fire_walk_left_12, robot_fire_walk_left_13, robot_fire_walk_left_14, robot_fire_walk_left_15, robot_fire_walk_left_16;
    private Image robot_fire_walk_right_1, robot_fire_walk_right_2, robot_fire_walk_right_3, robot_fire_walk_right_4, robot_fire_walk_right_5, robot_fire_walk_right_6, robot_fire_walk_right_7, robot_fire_walk_right_8, robot_fire_walk_right_9, robot_fire_walk_right_10, robot_fire_walk_right_11, robot_fire_walk_right_12, robot_fire_walk_right_13, robot_fire_walk_right_14, robot_fire_walk_right_15, robot_fire_walk_right_16;
    private Image robot_helmet_walk_left_1, robot_helmet_walk_left_2, robot_helmet_walk_left_3, robot_helmet_walk_left_4, robot_helmet_walk_left_5, robot_helmet_walk_left_6, robot_helmet_walk_left_7, robot_helmet_walk_left_8, robot_helmet_walk_left_9, robot_helmet_walk_left_10, robot_helmet_walk_left_11, robot_helmet_walk_left_12, robot_helmet_walk_left_13, robot_helmet_walk_left_14, robot_helmet_walk_left_15, robot_helmet_walk_left_16;
    private Image robot_helmet_walk_right_1, robot_helmet_walk_right_2, robot_helmet_walk_right_3, robot_helmet_walk_right_4, robot_helmet_walk_right_5, robot_helmet_walk_right_6, robot_helmet_walk_right_7, robot_helmet_walk_right_8, robot_helmet_walk_right_9, robot_helmet_walk_right_10, robot_helmet_walk_right_11, robot_helmet_walk_right_12, robot_helmet_walk_right_13, robot_helmet_walk_right_14, robot_helmet_walk_right_15, robot_helmet_walk_right_16;
    private Image robot_weak_walk_left_1, robot_weak_walk_left_2, robot_weak_walk_left_3, robot_weak_walk_left_4, robot_weak_walk_left_5, robot_weak_walk_left_6, robot_weak_walk_left_7, robot_weak_walk_left_8, robot_weak_walk_left_9, robot_weak_walk_left_10, robot_weak_walk_left_11, robot_weak_walk_left_12, robot_weak_walk_left_13, robot_weak_walk_left_14, robot_weak_walk_left_15, robot_weak_walk_left_16;
    private Image robot_weak_walk_right_1, robot_weak_walk_right_2, robot_weak_walk_right_3, robot_weak_walk_right_4, robot_weak_walk_right_5, robot_weak_walk_right_6, robot_weak_walk_right_7, robot_weak_walk_right_8, robot_weak_walk_right_9, robot_weak_walk_right_10, robot_weak_walk_right_11, robot_weak_walk_right_12, robot_weak_walk_right_13, robot_weak_walk_right_14, robot_weak_walk_right_15, robot_weak_walk_right_16;
    private Animation birdWalkRight, birdWalkLeft, birdGlideRight, birdGlideLeft, birdDivebombRight, birdDivebombLeft, robotFireWalkLeft, robotFireWalkRight, robotHelmetWalkLeft, robotHelmetWalkRight, robotWeakWalkLeft, robotWeakWalkRight;
    private static Background bg1, bg2;
    int elapsedSecs = 0;
    
    private long startTime = System.currentTimeMillis();
    private Font font = new Font("Courier", Font.BOLD, 30);
    private Font titleFont = new Font("Courier", Font.BOLD, 70);
    private Font ridersFont = new Font("Courier", Font.ITALIC, 10);
    private int flashInterval = 0;
    private boolean flash = false;
    private int GOInterval = 0;
    private boolean playing = false;
    
    public static Image tile01, tile02, tile03, tile04;
    private ArrayList<Tile> tilearray = new ArrayList<Tile>();
    
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
        
        // Loads assets
        loadAssets();
        
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
        bird = new Bird(225, 50, 61, 44);
        
        try {
        	loadMap("resources/map.txt");
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
        Thread thread = new Thread(this);
        thread.start();
    }
    

    // Load map function for the tiles
    private void loadMap(String filename) throws IOException {
    	ArrayList lines = new ArrayList();
    	int width = 0;
    	int height = 0;
    	
    	enemies = new ArrayList<Enemy>();
    	
    	BufferedReader reader = new BufferedReader(new FileReader(filename));
    	while(true){
    		String line = reader.readLine();
    		
    		if (line == null){
    			reader.close();
    			break;
    		}
    		
    		if (!line.startsWith("~")){
    			lines.add(line);
    			width = Math.max(width, line.length());
    		}
    	}
    	
    	height = lines.size();
    	
    	for (int j = 0; j < 20; j++){
    		String line = (String) lines.get(j);
    		for (int i = 0; i < width; i++){
    			if (i < line.length()){
    				char ch = line.charAt(i);
    				if (Character.getNumericValue(ch) == 9){
    					// Create Fire Enemy
    					enemies.add(new Robot_fire((float) ((i - 21) * 30) - 10, (float)((j * 30) - 20), 50, 50, ((i - 21) * 30) - 100, ((i - 21) * 30) + 100));
    				} else if (Character.getNumericValue(ch) == 8) {
    					// Create Helmet Enemy
    					enemies.add( new Robot_helmet((float) ((i - 21) * 30) - 10, (float)((j * 30) - 20), 50, 50, ((i - 21) * 30) - 100, ((i - 21) * 30) + 100));
    				} else if ((Character.getNumericValue(ch) == 7)){
    					// Create Weak Enemy
    					enemies.add(new Robot_weak((float) ((i - 21) * 30) - 10, (float)((j * 30) - 20), 50, 50,((i - 21) * 30) - 100, ((i - 21) * 30) + 100));
    				} else {
    					Tile t = new Tile(i, j, Character.getNumericValue(ch));
    					tilearray.add(t);
    				}
    			}
    		}
    	}
    	
    	height = lines.size();
    	
    	for (int j = 0; j < 20; j++){
    		String line = (String) lines.get(j);
    		for (int i = 0; i < width; i++){
    			if (i < line.length()){
    				char ch = line.charAt(i);
    				Tile t = new Tile(i, j, Character.getNumericValue(ch));
    				tilearray.add(t);
    			}
    		}
    	}
    }
    
    @Override
    public void stop() {
    }
    
    @Override
    public void destroy() {
    }
    
    // This is the game loop - as the GameState is set to
    // Running, it'll update the Bird object, animate anything,
    // and render/repaint the screen. The Thread.sleep(17) is
    // to steady the framerate
    public void run() {  
            while (true) {
            	while(state == GameState.Title){
            		flashInterval ++;
            		if(flashInterval >= 600000){
            			flash = !flash;
            			flashInterval = 0;
            		}
            		
            		if(playing){
            			state = GameState.Running;
            		}
            		
            		repaint();
            	}
            	
            	while(state == GameState.Dead){

            		
            		repaint();
            	}
        	
	        	while(state == GameState.Running){
	                bird.update();
	                
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
	                
	                if(bird.isJumped() && !bird.isFalling() && bird.isFacingRight() && !bird.isGliding()){
	                	currentSprite = bird_jump_right_1;
	                } else if (bird.isJumped() && bird.isFalling() && bird.isFacingRight() && !bird.isGliding()){
	                	currentSprite = bird_jump_right_2;
	                } else if (bird.isJumped() && !bird.isFalling() && !bird.isFacingRight() && !bird.isGliding()){
	                	currentSprite = bird_jump_left_1;
	                } else if (bird.isJumped() && bird.isFalling() && !bird.isFacingRight() && !bird.isGliding()){
	                	currentSprite = bird_jump_left_2;
	                }
	                
	                if (bird.isDivebomb() && bird.isFacingRight()){
	                	currentSprite = birdDivebombRight.getImage();
	                } else if (bird.isDivebomb() && !bird.isFacingRight()){
	                	currentSprite = birdDivebombLeft.getImage();
	                }
	                
	                if (bird.getY() > 900){
	                	state = GameState.Dead;
	                }
	                updateTiles();
	                
	                bg1.update();
	                bg2.update();
	                
	                for(int i=0; i<enemies.size();i++){
	                	enemies.get(i).update();
	                }
	                
	                if (elapsedSecs > 300){
	                	bird.kill();
	                	bird.setSpeedY(8);
	                }
	
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
        birdDivebombRight.update(20);
        birdDivebombLeft.update(20);
        robotFireWalkLeft.update(4);
        robotFireWalkRight.update(4);
        robotHelmetWalkLeft.update(4);
        robotHelmetWalkRight.update(4);
        robotWeakWalkLeft.update(4);
        robotWeakWalkRight.update(4);
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
    
    // Rendering function
    @Override
    public void paint(Graphics g) {
        if (state == GameState.Running) {
            g.setColor(Color.MAGENTA);
            g.fillRect(0, 0, 900, 600);
            
            // Draws background images
            g.drawImage(bg, bg1.getBgX(), bg1.getBgY(), this);
            g.drawImage(bg, bg2.getBgX(), bg2.getBgY(), this);
            paintTiles(g);
           
            g.drawImage(currentSprite, (int)bird.getX(), (int)bird.getY(), this);
            
            for(int i=0; i<enemies.size(); i++){
            	Enemy enemy = enemies.get(i);
            	
            	if(enemy.getType() == 0){
            		if (enemy.getFacingRight()){
            			g.drawImage(robotWeakWalkRight.getImage(), (int)enemy.getX(), (int)enemy.getY(), this);
            		} else {
            			g.drawImage(robotWeakWalkLeft.getImage(), (int)enemy.getX(), (int)enemy.getY(), this);
            		}
            	} else if (enemy.getType() == 1){
            		 if (enemy.getFacingRight()){
                     	g.drawImage(robotHelmetWalkRight.getImage(), (int)enemy.getX(), (int)enemy.getY(), this);
                     } else {
                     	g.drawImage(robotHelmetWalkLeft.getImage(), (int)enemy.getX(), (int)enemy.getY(), this);
                     }
            	} else if (enemy.getType() == 2){
            		if (enemy.getFacingRight()){
                    	g.drawImage(robotFireWalkRight.getImage(), (int)enemy.getX(), (int)enemy.getY(), this);
                    } else {
                    	g.drawImage(robotFireWalkLeft.getImage(), (int)enemy.getX(), (int)enemy.getY(), this);
                    }
            	}
            }
            
            if (bird.isAlive() && state == GameState.Running){
            	elapsedSecs = (int)(System.currentTimeMillis() - startTime)/1000;
            } else if (state == GameState.Title){
            	startTime = System.currentTimeMillis();
            }
            g.setColor(Color.BLUE);
            g.setFont(font);
            g.drawString(String.valueOf(elapsedSecs) + " / 300", 723, 27);
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(elapsedSecs) + " / 300", 725, 25);
        } else if (state == GameState.Dead) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 900, 600);
            g.setColor(Color.RED);
            g.drawString("GAME OVER", 350, 300);
        } else if (state == GameState.Title){
        	g.setColor(Color.WHITE);
        	g.fillRect(0,0,900,600);
        	g.setColor(Color.BLUE);
        	g.setFont(titleFont);
        	g.drawString("BIRD SIMULATOR 2016", 50, 200);
        	g.setFont(ridersFont);
        	g.drawString("Made by the Riders: Jacob Brown, Conor Tracey, & Zhibin Zhang", 300, 230);
        	
       
        	if(flash){
            	g.setFont(font);
            	g.drawString("PRESS SPACE TO PLAY", 300, 400 );
        	}

        }
    }
    
    private void updateTiles(){
    	for (int i = 0; i < tilearray.size(); i++){
    		Tile t = (Tile) tilearray.get(i);
    		t.update();
    	}
    }
    
    private void paintTiles(Graphics g){
    	for (int i = 0; i < tilearray.size(); i++){
    		Tile t = (Tile) tilearray.get(i);
    		g.drawImage(t.getTileImage(), t.getTileX(), t.getTileY(), this);
    	}
    }
    
    // keyPressed checks to see if any of these are pressed and acts
    // accordingly
    public void keyPressed(KeyEvent e) {
        
        switch (e.getKeyCode()) {
                
        
                // Jump / Double Jump
            case KeyEvent.VK_W:
            	if (bird.isAlive()){
            		bird.jump();
            	}
                break;
                
                // Move Left
            case KeyEvent.VK_A:
            	if (bird.isAlive()){
            		bird.moveLeft();
            		bird.setFacingRight(false);
            	}
                break;
                
            case KeyEvent.VK_S:
            	if (bird.isAlive()){
            		bird.diveBomb();
            	}
                break;
                
                // Move right
            case KeyEvent.VK_D:
            	if (bird.isAlive()){
            		bird.moveRight();
            		bird.setFacingRight(true);
            	}
                break;
                
                // Glide
            case KeyEvent.VK_K:
            	if (bird.isAlive()){
            		bird.glide();
            	}
                break;
        
                
                
            case KeyEvent.VK_SPACE:
            	if (state == GameState.Title){
            		playing = true;
            	}
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
    
    public void loadAssets(){
    	bird_stand_right = getImage(base, "resources/bird/walk_cycle/bird_walk_right_2.png");
        bird_walk_right_1 = getImage(base, "resources/bird/walk_cycle/bird_walk_right_1.png");
        bird_walk_right_2 = getImage(base, "resources/bird/walk_cycle/bird_walk_right_2.png");
        bird_stand_left = getImage(base, "resources/bird/walk_cycle/bird_walk_left_2.png");
        bird_walk_left_1 = getImage(base, "resources/bird/walk_cycle/bird_walk_left_1.png");
        bird_walk_left_2 = getImage(base, "resources/bird/walk_cycle/bird_walk_left_2.png");
        
        bird_glide_left_1 = getImage(base, "resources/bird/glide/bird_glide_left_1.png");
        bird_glide_left_2 = getImage(base, "resources/bird/glide/bird_glide_left_2.png");
        bird_glide_right_1 = getImage(base, "resources/bird/glide/bird_glide_right_1.png");
        bird_glide_right_2 = getImage(base, "resources/bird/glide/bird_glide_right_2.png");
        
        bird_jump_right_1 = getImage(base, "resources/bird/jump/bird_jump_right_1.png");
        bird_jump_right_2 = getImage(base, "resources/bird/jump/bird_jump_right_2.png");
        bird_jump_left_1 = getImage(base, "resources/bird/jump/bird_jump_left_1.png");
        bird_jump_left_2 = getImage(base, "resources/bird/jump/bird_jump_left_2.png");
        
        bird_divebomb_right_1 = getImage(base, "resources/bird/divebomb/bird_divebomb_right_1.png");
        bird_divebomb_right_2 = getImage(base, "resources/bird/divebomb/bird_divebomb_right_2.png");
        bird_divebomb_right_3 = getImage(base, "resources/bird/divebomb/bird_divebomb_right_3.png");
        bird_divebomb_left_1 = getImage(base, "resources/bird/divebomb/bird_divebomb_left_1.png");
        bird_divebomb_left_2 = getImage(base, "resources/bird/divebomb/bird_divebomb_left_2.png");
        bird_divebomb_left_3 = getImage(base, "resources/bird/divebomb/bird_divebomb_left_3.png");
        
        robot_fire_walk_left_1 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_left_01.png");
        robot_fire_walk_left_2 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_left_02.png");
        robot_fire_walk_left_3 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_left_03.png");
        robot_fire_walk_left_4 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_left_04.png");
        robot_fire_walk_left_5 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_left_05.png");
        robot_fire_walk_left_6 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_left_06.png");
        robot_fire_walk_left_7 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_left_07.png");
        robot_fire_walk_left_8 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_left_08.png");
        robot_fire_walk_left_9 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_left_09.png");
        robot_fire_walk_left_10 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_left_10.png");
        robot_fire_walk_left_11 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_left_11.png");
        robot_fire_walk_left_12 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_left_12.png");
        robot_fire_walk_left_13 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_left_13.png");
        robot_fire_walk_left_14 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_left_14.png");
        robot_fire_walk_left_15 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_left_15.png");
        robot_fire_walk_left_16 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_left_16.png");
        
        robot_fire_walk_right_1 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_right_01.png");
        robot_fire_walk_right_2 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_right_02.png");
        robot_fire_walk_right_3 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_right_03.png");
        robot_fire_walk_right_4 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_right_04.png");
        robot_fire_walk_right_5 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_right_05.png");
        robot_fire_walk_right_6 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_right_06.png");
        robot_fire_walk_right_7 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_right_07.png");
        robot_fire_walk_right_8 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_right_08.png");
        robot_fire_walk_right_9 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_right_09.png");
        robot_fire_walk_right_10 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_right_10.png");
        robot_fire_walk_right_11 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_right_11.png");
        robot_fire_walk_right_12 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_right_12.png");
        robot_fire_walk_right_13 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_right_13.png");
        robot_fire_walk_right_14 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_right_14.png");
        robot_fire_walk_right_15 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_right_15.png");
        robot_fire_walk_right_16 = getImage(base, "resources/robot/fire/walk_cycle/robot_fire_walk_right_16.png");
        
        robot_helmet_walk_right_1 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_right_01.png");
        robot_helmet_walk_right_2 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_right_02.png");
        robot_helmet_walk_right_3 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_right_03.png");
        robot_helmet_walk_right_4 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_right_04.png");
        robot_helmet_walk_right_5 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_right_05.png");
        robot_helmet_walk_right_6 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_right_06.png");
        robot_helmet_walk_right_7 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_right_07.png");
        robot_helmet_walk_right_8 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_right_08.png");
        robot_helmet_walk_right_9 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_right_09.png");
        robot_helmet_walk_right_10 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_right_10.png");
        robot_helmet_walk_right_11 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_right_11.png");
        robot_helmet_walk_right_12 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_right_12.png");
        robot_helmet_walk_right_13 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_right_13.png");
        robot_helmet_walk_right_14 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_right_14.png");
        robot_helmet_walk_right_15 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_right_15.png");
        robot_helmet_walk_right_16 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_right_16.png");
        
        robot_helmet_walk_left_1 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_left_01.png");
        robot_helmet_walk_left_2 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_left_02.png");
        robot_helmet_walk_left_3 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_left_03.png");
        robot_helmet_walk_left_4 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_left_04.png");
        robot_helmet_walk_left_5 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_left_05.png");
        robot_helmet_walk_left_6 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_left_06.png");
        robot_helmet_walk_left_7 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_left_07.png");
        robot_helmet_walk_left_8 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_left_08.png");
        robot_helmet_walk_left_9 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_left_09.png");
        robot_helmet_walk_left_10 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_left_10.png");
        robot_helmet_walk_left_11 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_left_11.png");
        robot_helmet_walk_left_12 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_left_12.png");
        robot_helmet_walk_left_13 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_left_13.png");
        robot_helmet_walk_left_14 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_left_14.png");
        robot_helmet_walk_left_15 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_left_15.png");
        robot_helmet_walk_left_16 = getImage(base, "resources/robot/helmet/walk_cycle/robot_helmet_walk_left_16.png");
        
        robot_weak_walk_right_1 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_right_01.png");
        robot_weak_walk_right_2 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_right_02.png");
        robot_weak_walk_right_3 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_right_03.png");
        robot_weak_walk_right_4 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_right_04.png");
        robot_weak_walk_right_5 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_right_05.png");
        robot_weak_walk_right_6 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_right_06.png");
        robot_weak_walk_right_7 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_right_07.png");
        robot_weak_walk_right_8 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_right_08.png");
        robot_weak_walk_right_9 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_right_09.png");
        robot_weak_walk_right_10 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_right_10.png");
        robot_weak_walk_right_11 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_right_11.png");
        robot_weak_walk_right_12 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_right_12.png");
        robot_weak_walk_right_13 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_right_13.png");
        robot_weak_walk_right_14 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_right_14.png");
        robot_weak_walk_right_15 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_right_15.png");
        robot_weak_walk_right_16 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_right_16.png");
        
        robot_weak_walk_left_1 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_left_01.png");
        robot_weak_walk_left_2 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_left_02.png");
        robot_weak_walk_left_3 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_left_03.png");
        robot_weak_walk_left_4 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_left_04.png");
        robot_weak_walk_left_5 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_left_05.png");
        robot_weak_walk_left_6 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_left_06.png");
        robot_weak_walk_left_7 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_left_07.png");
        robot_weak_walk_left_8 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_left_08.png");
        robot_weak_walk_left_9 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_left_09.png");
        robot_weak_walk_left_10 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_left_10.png");
        robot_weak_walk_left_11 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_left_11.png");
        robot_weak_walk_left_12 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_left_12.png");
        robot_weak_walk_left_13 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_left_13.png");
        robot_weak_walk_left_14 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_left_14.png");
        robot_weak_walk_left_15 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_left_15.png");
        robot_weak_walk_left_16 = getImage(base, "resources/robot/weak/walk_cycle/robot_weak_walk_left_16.png");
        
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
        
        birdDivebombRight = new Animation();
        birdDivebombRight.addFrame(bird_divebomb_right_1, 10);
        birdDivebombRight.addFrame(bird_divebomb_right_2, 10);
        birdDivebombRight.addFrame(bird_divebomb_right_3, 10);
        
        birdDivebombLeft = new Animation();
        birdDivebombLeft.addFrame(bird_divebomb_left_1, 10);
        birdDivebombLeft.addFrame(bird_divebomb_left_2, 10);
        birdDivebombLeft.addFrame(bird_divebomb_left_3, 10);
        
        robotFireWalkLeft = new Animation();
        robotFireWalkLeft.addFrame(robot_fire_walk_left_1, 10);
        robotFireWalkLeft.addFrame(robot_fire_walk_left_2, 10);
        robotFireWalkLeft.addFrame(robot_fire_walk_left_3, 10);
        robotFireWalkLeft.addFrame(robot_fire_walk_left_4, 10);
        robotFireWalkLeft.addFrame(robot_fire_walk_left_5, 10);
        robotFireWalkLeft.addFrame(robot_fire_walk_left_6, 10);
        robotFireWalkLeft.addFrame(robot_fire_walk_left_7, 10);
        robotFireWalkLeft.addFrame(robot_fire_walk_left_8, 10);
        robotFireWalkLeft.addFrame(robot_fire_walk_left_9, 10);
        robotFireWalkLeft.addFrame(robot_fire_walk_left_10, 10);
        robotFireWalkLeft.addFrame(robot_fire_walk_left_11, 10);
        robotFireWalkLeft.addFrame(robot_fire_walk_left_12, 10);
        robotFireWalkLeft.addFrame(robot_fire_walk_left_13, 10);
        robotFireWalkLeft.addFrame(robot_fire_walk_left_14, 10);
        robotFireWalkLeft.addFrame(robot_fire_walk_left_15, 10);
        robotFireWalkLeft.addFrame(robot_fire_walk_left_16, 10);
        
        robotFireWalkRight = new Animation();
        robotFireWalkRight.addFrame(robot_fire_walk_right_1, 10);
        robotFireWalkRight.addFrame(robot_fire_walk_right_2, 10);
        robotFireWalkRight.addFrame(robot_fire_walk_right_3, 10);
        robotFireWalkRight.addFrame(robot_fire_walk_right_4, 10);
        robotFireWalkRight.addFrame(robot_fire_walk_right_5, 10);
        robotFireWalkRight.addFrame(robot_fire_walk_right_6, 10);
        robotFireWalkRight.addFrame(robot_fire_walk_right_7, 10);
        robotFireWalkRight.addFrame(robot_fire_walk_right_8, 10);
        robotFireWalkRight.addFrame(robot_fire_walk_right_9, 10);
        robotFireWalkRight.addFrame(robot_fire_walk_right_10, 10);
        robotFireWalkRight.addFrame(robot_fire_walk_right_11, 10);
        robotFireWalkRight.addFrame(robot_fire_walk_right_12, 10);
        robotFireWalkRight.addFrame(robot_fire_walk_right_13, 10);
        robotFireWalkRight.addFrame(robot_fire_walk_right_14, 10);
        robotFireWalkRight.addFrame(robot_fire_walk_right_15, 10);
        robotFireWalkRight.addFrame(robot_fire_walk_right_16, 10);
        
        robotHelmetWalkRight = new Animation();
        robotHelmetWalkRight.addFrame(robot_helmet_walk_right_1, 10);
        robotHelmetWalkRight.addFrame(robot_helmet_walk_right_2, 10);
        robotHelmetWalkRight.addFrame(robot_helmet_walk_right_3, 10);
        robotHelmetWalkRight.addFrame(robot_helmet_walk_right_4, 10);
        robotHelmetWalkRight.addFrame(robot_helmet_walk_right_5, 10);
        robotHelmetWalkRight.addFrame(robot_helmet_walk_right_6, 10);
        robotHelmetWalkRight.addFrame(robot_helmet_walk_right_7, 10);
        robotHelmetWalkRight.addFrame(robot_helmet_walk_right_8, 10);
        robotHelmetWalkRight.addFrame(robot_helmet_walk_right_9, 10);
        robotHelmetWalkRight.addFrame(robot_helmet_walk_right_10, 10);
        robotHelmetWalkRight.addFrame(robot_helmet_walk_right_11, 10);
        robotHelmetWalkRight.addFrame(robot_helmet_walk_right_12, 10);
        robotHelmetWalkRight.addFrame(robot_helmet_walk_right_13, 10);
        robotHelmetWalkRight.addFrame(robot_helmet_walk_right_14, 10);
        robotHelmetWalkRight.addFrame(robot_helmet_walk_right_15, 10);
        robotHelmetWalkRight.addFrame(robot_helmet_walk_right_16, 10);
        
        robotHelmetWalkLeft = new Animation();
        robotHelmetWalkLeft.addFrame(robot_helmet_walk_left_1, 10);
        robotHelmetWalkLeft.addFrame(robot_helmet_walk_left_2, 10);
        robotHelmetWalkLeft.addFrame(robot_helmet_walk_left_3, 10);
        robotHelmetWalkLeft.addFrame(robot_helmet_walk_left_4, 10);
        robotHelmetWalkLeft.addFrame(robot_helmet_walk_left_5, 10);
        robotHelmetWalkLeft.addFrame(robot_helmet_walk_left_6, 10);
        robotHelmetWalkLeft.addFrame(robot_helmet_walk_left_7, 10);
        robotHelmetWalkLeft.addFrame(robot_helmet_walk_left_8, 10);
        robotHelmetWalkLeft.addFrame(robot_helmet_walk_left_9, 10);
        robotHelmetWalkLeft.addFrame(robot_helmet_walk_left_10, 10);
        robotHelmetWalkLeft.addFrame(robot_helmet_walk_left_11, 10);
        robotHelmetWalkLeft.addFrame(robot_helmet_walk_left_12, 10);
        robotHelmetWalkLeft.addFrame(robot_helmet_walk_left_13, 10);
        robotHelmetWalkLeft.addFrame(robot_helmet_walk_left_14, 10);
        robotHelmetWalkLeft.addFrame(robot_helmet_walk_left_15, 10);
        robotHelmetWalkLeft.addFrame(robot_helmet_walk_left_16, 10);
        
        robotWeakWalkRight = new Animation();
        robotWeakWalkRight.addFrame(robot_weak_walk_right_1, 10);
        robotWeakWalkRight.addFrame(robot_weak_walk_right_2, 10);
        robotWeakWalkRight.addFrame(robot_weak_walk_right_3, 10);
        robotWeakWalkRight.addFrame(robot_weak_walk_right_4, 10);
        robotWeakWalkRight.addFrame(robot_weak_walk_right_5, 10);
        robotWeakWalkRight.addFrame(robot_weak_walk_right_6, 10);
        robotWeakWalkRight.addFrame(robot_weak_walk_right_7, 10);
        robotWeakWalkRight.addFrame(robot_weak_walk_right_8, 10);
        robotWeakWalkRight.addFrame(robot_weak_walk_right_9, 10);
        robotWeakWalkRight.addFrame(robot_weak_walk_right_10, 10);
        robotWeakWalkRight.addFrame(robot_weak_walk_right_11, 10);
        robotWeakWalkRight.addFrame(robot_weak_walk_right_12, 10);
        robotWeakWalkRight.addFrame(robot_weak_walk_right_13, 10);
        robotWeakWalkRight.addFrame(robot_weak_walk_right_14, 10);
        robotWeakWalkRight.addFrame(robot_weak_walk_right_15, 10);
        robotWeakWalkRight.addFrame(robot_weak_walk_right_16, 10);
        
        robotWeakWalkLeft = new Animation();
        robotWeakWalkLeft.addFrame(robot_weak_walk_left_1, 10);
        robotWeakWalkLeft.addFrame(robot_weak_walk_left_2, 10);
        robotWeakWalkLeft.addFrame(robot_weak_walk_left_3, 10);
        robotWeakWalkLeft.addFrame(robot_weak_walk_left_4, 10);
        robotWeakWalkLeft.addFrame(robot_weak_walk_left_5, 10);
        robotWeakWalkLeft.addFrame(robot_weak_walk_left_6, 10);
        robotWeakWalkLeft.addFrame(robot_weak_walk_left_7, 10);
        robotWeakWalkLeft.addFrame(robot_weak_walk_left_8, 10);
        robotWeakWalkLeft.addFrame(robot_weak_walk_left_9, 10);
        robotWeakWalkLeft.addFrame(robot_weak_walk_left_10, 10);
        robotWeakWalkLeft.addFrame(robot_weak_walk_left_11, 10);
        robotWeakWalkLeft.addFrame(robot_weak_walk_left_12, 10);
        robotWeakWalkLeft.addFrame(robot_weak_walk_left_13, 10);
        robotWeakWalkLeft.addFrame(robot_weak_walk_left_14, 10);
        robotWeakWalkLeft.addFrame(robot_weak_walk_left_15, 10);
        robotWeakWalkLeft.addFrame(robot_weak_walk_left_16, 10);
        
        currentSprite = bird_stand_right;
        bg = getImage(base, "resources/background.png");
        
        tile01 = getImage(base, "resources/tile01.png");
        tile02 = getImage(base, "resources/tile02.png");
        tile03 = getImage(base, "resources/tile03.png");
        tile04 = getImage(base, "resources/tile04.png");
        
    }
    
    public static Background getBg1(){
    	return bg1;
    }
    
    public static Background getBg2(){
    	return bg2;
    }
    
    public static Bird getBird(){
    	return bird;
    }
    
}