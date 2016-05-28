/**************************************************
 *    											  *
 *    Controller.java						              *
 *    from Kilobolt Studios under MIT license     *
 *    heavily modified by Jacob Brown, Conor	  *
 *    Tracey, Zhibin Zhang					      *
 *												  *
 **************************************************/
package MainFrame;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class Controller extends JFrame implements KeyListener {
	public Controller() {
		init();
	}
	
	Model model = new Model();
	View view = new View();
	Thread animation;
	
	public void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
		setVisible(true);
		
		setSize(900, 600);
		setFocusable(true);
		addKeyListener(this);
		
		setLayout(new BorderLayout());
		this.add(BorderLayout.CENTER, view);
		setTitle("Bird Sim");	
		
		// preparing parameters for view to display
		view.setModel(model);
		view.setDelay(17); // view.delay is always 17
		try {view.loadMap("Animation/resources/map.txt");} 
		catch (IOException e) {e.printStackTrace();}
		view.loadAssets();
		
		animation = new Thread(view);
		animation.start();
	}
	
	
	
	@SuppressWarnings("static-access")
	public void keyPressed(KeyEvent e) {
		
		switch (e.getKeyCode()) {
			
			case KeyEvent.VK_UP: case KeyEvent.VK_W:
				Model.getBird().jump();
				System.out.println("Jumped");
				break;
				
			case KeyEvent.VK_DOWN: case KeyEvent.VK_S:
				Model.getBird().diveBomb();
				System.out.println("Diving");
				break;
			
			case KeyEvent.VK_LEFT: case KeyEvent.VK_A:
				Model.getBird().moveLeft();
				System.out.println("Moving left");
				break;
			
			case KeyEvent.VK_RIGHT: case KeyEvent.VK_D:
				Model.getBird().moveRight();
				System.out.println("Moving right");
				break;
			
			case KeyEvent.VK_SPACE: case KeyEvent.VK_K:
				Model.getBird().glide();
				System.out.println("Gliding");
				break;
				
			case KeyEvent.VK_ENTER:
				if (Model.state == Model.state.Title) 
					view.setPlaying(true);
				break;
		}
		
	}

	public void keyReleased(KeyEvent e) {
		
		switch (e.getKeyCode()) {
		
			case KeyEvent.VK_UP: case KeyEvent.VK_W:
				break;
				
			case KeyEvent.VK_DOWN: case KeyEvent.VK_S:
				break;
		
			case KeyEvent.VK_LEFT: case KeyEvent.VK_A:
				if (Model.getBird().isMovingLeft())
					Model.getBird().stop();
				break;
		
			case KeyEvent.VK_RIGHT: case KeyEvent.VK_D:
				if (Model.getBird().isMovingRight())	
					Model.getBird().stop();
				break;
		
			case KeyEvent.VK_SPACE: case KeyEvent.VK_K:
				break;
		}
		
	}
	
	public void keyTyped(KeyEvent e) {
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				new Controller();
			}
		});
	}
}