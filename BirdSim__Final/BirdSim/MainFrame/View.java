/**************************************************
 *    											  *
 *    View.java						              *
 *    from Kilobolt Studios under MIT license     *
 *    heavily modified by Jacob Brown, Conor	  *
 *    Tracey, Zhibin Zhang					      *
 *												  *
 **************************************************/

package MainFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageProducer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import Animation.Animation;
import Elements.Bird;
import Elements.Enemy;
import Elements.Robot_fire;
import Elements.Robot_helmet;
import Elements.Robot_weak;
import Elements.Tile;
import MainFrame.Model;

@SuppressWarnings("serial")
public class View extends Canvas implements Runnable {
    private Model model;
    protected Graphics graphics;
    private URL base;
    public static ArrayList<Tile> tileArray = new ArrayList<Tile>();
    public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
    private Image image, bg, currentSprite;
    private Image tile01, tile02, tile03, tile04;
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
	
    private int flashInterval = 0;
    private boolean flash = false;
    
    @SuppressWarnings("static-access")
	Robot_fire robotFire = model.getF();
	@SuppressWarnings("static-access")
	Robot_helmet robotHelmet = model.getH();
	@SuppressWarnings("static-access")
	Robot_weak robotWeak = model.getW();
	
	int elapsedSecs = 0;
	private long startTime = System.currentTimeMillis();
    private Font font = new Font("Courier", Font.BOLD, 30);
    private Font titleFont = new Font("Courier", Font.BOLD, 70);
    private Font ridersFont = new Font("Courier", Font.ITALIC, 10);
	
    public void setModel(Model m) {
    	model = m;
    }
    
    // set up proper view according to different game states
    @Override
    public void run() {  
        while (true) {
        	
        	if (Model.state == Model.GameState.Title){
        		flashInterval ++;
        		if(flashInterval >= 600000){
        			flash = !flash;
        			flashInterval = 0;
        		}
        		repaint();
        	}
    	
        	if (Model.state == Model.GameState.Running){
                model.update();
                render(model);
  
                if (elapsedSecs > 300){
                	Model.getBird().kill();
                	Model.getBird().setSpeedY(8);
                }

               animate();
               repaint();
               try {Thread.sleep(17);} 
               catch (InterruptedException e) {e.printStackTrace();}
        	}
        	
        }
    }
    
    // animations for bird and robots
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
        robotWeakWalkRight.update(4);
        robotWeakWalkLeft.update(4);
	}
	
	public void update(Graphics g) {
		if (image == null) {
			image = createImage(900, 600);
			graphics = image.getGraphics();
		}
		paint(graphics);
		g.drawImage(image, 0, 0, this);
	}
	
	// paints depending on different states of model
	public void paint(Graphics g) {
        if (Model.state == Model.GameState.Running) {
            g.setColor(Color.MAGENTA);
            g.fillRect(0, 0, 900, 600);
            
            // Draws background images
            g.drawImage(bg, Model.getBg1().getBgX(), Model.getBg1().getBgY(), this);
            g.drawImage(bg, Model.getBg2().getBgX(), Model.getBg2().getBgY(), this);
            
            // paint enemies, tiles, bird (depending on move of bird)
            paintTiles(g);
            paintEnemies(g);
            g.drawImage(currentSprite, (int)Model.getBird().getX(), (int)Model.getBird().getY(), this);     
            
            // paint timer
            elapsedSecs = (int)(System.currentTimeMillis() - startTime)/1000;
            g.setColor(Color.BLUE);
            g.setFont(font);
            g.drawString(String.valueOf(elapsedSecs) + " / 300", 723, 27);
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(elapsedSecs) + " / 300", 725, 25);
            
        } 
        
        if (Model.state == Model.GameState.Dead) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 900, 600);
            g.setColor(Color.RED);
            g.drawString("GAME OVER", 350, 300);
        }
        
        if (Model.state == Model.GameState.Title){
        	startTime = System.currentTimeMillis();
        	
        	g.setColor(Color.WHITE);
        	g.fillRect(0,0,900,600);
        	g.setColor(Color.BLUE);
        	g.setFont(titleFont);
        	g.drawString("BIRD SIMULATOR 2016", 50, 200);
        	g.setFont(ridersFont);
        	g.drawString("Made by the Riders: Jacob Brown, Conor Tracey, & Zhibin Zhang", 300, 230);
        	
       
        	if(flash){
            	g.setFont(font);
            	g.drawString("PRESS ENTER TO PLAY", 300, 400 );
        	}

        }
	}
	
	// see which tile should be rendered depending on type
	private Image renderTile(int type) {
		if (type == 1)
			return tile01;
		else if (type == 2)
			return tile02;
		else if (type == 3) 
			return tile03;
		else if (type == 4)
			return tile04;
		return null;
	}
	
	// paint tiles
	private void paintTiles(Graphics g) {
		for (int i = 0; i < tileArray.size(); i++) {
			Tile t = (Tile) tileArray.get(i);
			g.drawImage(renderTile(t.getTileType()), t.getTileX(), t.getTileY(), this);
		}
	}
	
	// render different bird image depending on state of bird
	private void render(Model model) {
		@SuppressWarnings("static-access")
		Bird bird = model.getBird();
		
		if (bird.isMovingRight() && !bird.isGliding())
        	currentSprite = birdWalkRight.getImage();
        else if (bird.isMovingLeft() && !bird.isGliding())
        	currentSprite = birdWalkLeft.getImage();
        else if (bird.isGliding() && bird.isFacingRight())
        	currentSprite = birdGlideRight.getImage();
        else if (bird.isGliding() && !bird.isFacingRight())
        	currentSprite = birdGlideLeft.getImage();
        else if (!bird.isGliding() && bird.isFacingRight())
        	currentSprite = bird_stand_right;
        else if (!bird.isGliding() && !bird.isFacingRight())
        	currentSprite = bird_stand_left;  
        
        if(bird.isJumped() && !bird.isFalling() && bird.isFacingRight() && !bird.isGliding())
        	currentSprite = bird_jump_right_1;
        else if (bird.isJumped() && bird.isFalling() && bird.isFacingRight() && !bird.isGliding())
        	currentSprite = bird_jump_right_2;
        else if (bird.isJumped() && !bird.isFalling() && !bird.isFacingRight() && !bird.isGliding())
        	currentSprite = bird_jump_left_1;
        else if (bird.isJumped() && bird.isFalling() && !bird.isFacingRight() && !bird.isGliding())
        	currentSprite = bird_jump_left_2;
        
        if (bird.isDivebomb() && bird.isFacingRight())
        	currentSprite = birdDivebombRight.getImage();
        else if (bird.isDivebomb() && !bird.isFacingRight())
        	currentSprite = birdDivebombLeft.getImage();
	}
	
	// paint enemies
	private void paintEnemies(Graphics g) {
        for(int i=0; i < enemies.size(); i++){
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
	}
	
	// load map (load tiles)
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void loadMap(String filename) throws IOException {
    	ArrayList lines = new ArrayList();
    	int width = 0;
    	int height = 0;
    	
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
    	
    	for (int j = 0; j < height; j++){
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
    					tileArray.add(t);
    				}
    			}
    		}
    	}
    	
   
    }
	
	// load image; set currentSprint.
	public void loadAssets() {
		 
		// load background
		base = getClass().getResource("/Animation/resources/background.png");
		try {bg = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		
		// load tiles
		base = getClass().getResource("/Animation/resources/tiles/tile01.png");
		try {tile01 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/tiles/tile02.png");
		try {tile02 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/tiles/tile03.png");
		try {tile03 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/tiles/tile04.png");
		try {tile04 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		  
		 // load bird walk image
		base = getClass().getResource("/Animation/resources/bird/walk_cycle/bird_walk_right_2.png");
		try {bird_stand_right = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/bird/walk_cycle/bird_walk_right_1.png");
		try {bird_walk_right_1 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/bird/walk_cycle/bird_walk_right_2.png");
		try {bird_walk_right_2 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/bird/walk_cycle/bird_walk_left_2.png");
		try {bird_stand_left = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/bird/walk_cycle/bird_walk_left_1.png");
		try {bird_walk_left_1 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/bird/walk_cycle/bird_walk_left_2.png");
		try {bird_walk_left_2 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		
		// load bird glide image
		base = getClass().getResource("/Animation/resources/bird/glide/bird_glide_left_1.png");
		try {bird_glide_left_1 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/bird/glide/bird_glide_left_2.png");
		try {bird_glide_left_2 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/bird/glide/bird_glide_right_1.png");
		try {bird_glide_right_1 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/bird/glide/bird_glide_right_2.png");
		try {bird_glide_right_2 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		
		// load bird jump image
		base = getClass().getResource("/Animation/resources/bird/jump/bird_jump_right_1.png");
		try {bird_jump_right_1 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/bird/jump/bird_jump_right_2.png");
		try {bird_jump_right_2 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/bird/jump/bird_jump_left_1.png");
		try {bird_jump_left_1 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/bird/jump/bird_jump_left_2.png");
		try {bird_jump_left_2 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		
		// load bird divebomb image
		base = getClass().getResource("/Animation/resources/bird/divebomb/bird_divebomb_right_1.png");
		try {bird_divebomb_right_1 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/bird/divebomb/bird_divebomb_right_2.png");
		try {bird_divebomb_right_2 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/bird/divebomb/bird_divebomb_right_3.png");
		try {bird_divebomb_right_3 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/bird/divebomb/bird_divebomb_left_1.png");
		try {bird_divebomb_left_1 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/bird/divebomb/bird_divebomb_left_2.png");
		try {bird_divebomb_left_2 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/bird/divebomb/bird_divebomb_left_3.png");
		try {bird_divebomb_left_3 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		
		// load robot_fire walk-left image
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_left_01.png");
		try {robot_fire_walk_left_1 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_left_02.png");
		try {robot_fire_walk_left_2 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_left_03.png");
		try {robot_fire_walk_left_3 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_left_04.png");
		try {robot_fire_walk_left_4 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_left_05.png");
		try {robot_fire_walk_left_5 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_left_06.png");
		try {robot_fire_walk_left_6 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_left_07.png");
		try {robot_fire_walk_left_7 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_left_08.png");
		try {robot_fire_walk_left_8 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_left_09.png");
		try {robot_fire_walk_left_9 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_left_10.png");
		try {robot_fire_walk_left_10 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_left_11.png");
		try {robot_fire_walk_left_11 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_left_12.png");
		try {robot_fire_walk_left_12 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_left_13.png");
		try {robot_fire_walk_left_13 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_left_14.png");
		try {robot_fire_walk_left_14 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_left_15.png");
		try {robot_fire_walk_left_15 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_left_16.png");
		try {robot_fire_walk_left_16 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		
		// load robot_fire walk-right image
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_right_01.png");
		try {robot_fire_walk_right_1 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_right_02.png");
		try {robot_fire_walk_right_2 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_right_03.png");
		try {robot_fire_walk_right_3 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_right_04.png");
		try {robot_fire_walk_right_4 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_right_05.png");
		try {robot_fire_walk_right_5 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_right_06.png");
		try {robot_fire_walk_right_6 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_right_07.png");
		try {robot_fire_walk_right_7 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_right_08.png");
		try {robot_fire_walk_right_8 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_right_09.png");
		try {robot_fire_walk_right_9 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_right_10.png");
		try {robot_fire_walk_right_10 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_right_11.png");
		try {robot_fire_walk_right_11 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_right_12.png");
		try {robot_fire_walk_right_12 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_right_13.png");
		try {robot_fire_walk_right_13 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_right_14.png");
		try {robot_fire_walk_right_14 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_right_15.png");
		try {robot_fire_walk_right_15 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/fire/walk_cycle/robot_fire_walk_right_16.png");
		try {robot_fire_walk_right_16 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		
		// load robot_helmet walk-right image
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_right_01.png");
		try {robot_helmet_walk_right_1 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_right_02.png");
		try {robot_helmet_walk_right_2 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_right_03.png");
		try {robot_helmet_walk_right_3 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_right_04.png");
		try {robot_helmet_walk_right_4 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_right_05.png");
		try {robot_helmet_walk_right_5 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_right_06.png");
		try {robot_helmet_walk_right_6 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_right_07.png");
		try {robot_helmet_walk_right_7 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_right_08.png");
		try {robot_helmet_walk_right_8 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_right_09.png");
		try {robot_helmet_walk_right_9 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_right_10.png");
		try {robot_helmet_walk_right_10 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_right_11.png");
		try {robot_helmet_walk_right_11 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_right_12.png");
		try {robot_helmet_walk_right_12 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_right_13.png");
		try {robot_helmet_walk_right_13 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_right_14.png");
		try {robot_helmet_walk_right_14 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_right_15.png");
		try {robot_helmet_walk_right_15 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_right_16.png");
		try {robot_helmet_walk_right_16 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		
		// load robot_helmet walk-left image
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_left_01.png");
		try {robot_helmet_walk_left_1 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_left_02.png");
		try {robot_helmet_walk_left_2 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_left_03.png");
		try {robot_helmet_walk_left_3 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_left_04.png");
		try {robot_helmet_walk_left_4 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_left_05.png");
		try {robot_helmet_walk_left_5 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_left_06.png");
		try {robot_helmet_walk_left_6 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_left_07.png");
		try {robot_helmet_walk_left_7 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_left_08.png");
		try {robot_helmet_walk_left_8 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_left_09.png");
		try {robot_helmet_walk_left_9 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_left_10.png");
		try {robot_helmet_walk_left_10 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_left_11.png");
		try {robot_helmet_walk_left_11 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_left_12.png");
		try {robot_helmet_walk_left_12 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_left_13.png");
		try {robot_helmet_walk_left_13 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_left_14.png");
		try {robot_helmet_walk_left_14 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_left_15.png");
		try {robot_helmet_walk_left_15 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/helmet/walk_cycle/robot_helmet_walk_left_16.png");
		try {robot_helmet_walk_left_16 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_left_01.png");
		try {robot_weak_walk_left_1 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_left_02.png");
		try {robot_weak_walk_left_2 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_left_03.png");
		try {robot_weak_walk_left_3 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_left_04.png");
		try {robot_weak_walk_left_4 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_left_05.png");
		try {robot_weak_walk_left_5 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_left_06.png");
		try {robot_weak_walk_left_6 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_left_07.png");
		try {robot_weak_walk_left_7 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_left_08.png");
		try {robot_weak_walk_left_8 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_left_09.png");
		try {robot_weak_walk_left_9 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_left_10.png");
		try {robot_weak_walk_left_10 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_left_11.png");
		try {robot_weak_walk_left_11 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_left_12.png");
		try {robot_weak_walk_left_12 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_left_13.png");
		try {robot_weak_walk_left_13 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_left_14.png");
		try {robot_weak_walk_left_14 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_left_15.png");
		try {robot_weak_walk_left_15 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_left_16.png");
		try {robot_weak_walk_left_16 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_right_01.png");
		try {robot_weak_walk_right_1 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_right_02.png");
		try {robot_weak_walk_right_2 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_right_03.png");
		try {robot_weak_walk_right_3 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_right_04.png");
		try {robot_weak_walk_right_4 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_right_05.png");
		try {robot_weak_walk_right_5 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_right_06.png");
		try {robot_weak_walk_right_6 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_right_07.png");
		try {robot_weak_walk_right_7 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_right_08.png");
		try {robot_weak_walk_right_8 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_right_09.png");
		try {robot_weak_walk_right_9 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_right_10.png");
		try {robot_weak_walk_right_10 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_right_11.png");
		try {robot_weak_walk_right_11 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_right_12.png");
		try {robot_weak_walk_right_12 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_right_13.png");
		try {robot_weak_walk_right_13 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_right_14.png");
		try {robot_weak_walk_right_14 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_right_15.png");
		try {robot_weak_walk_right_15 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		base = getClass().getResource("/Animation/resources/robot/weak/walk_cycle/robot_weak_walk_right_16.png");
		try {robot_weak_walk_right_16 = createImage((ImageProducer)base.getContent());} 
		catch (Exception e) {e.printStackTrace();}
		
		
	    // Animate the images
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
       
       }
}
