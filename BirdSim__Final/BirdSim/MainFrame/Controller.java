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
	
	Model model;
	View view;
	Thread animation;
	
	public Controller() {
		init();
	}
	
	// set up JFrame
	public void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(900, 600);
		setFocusable(true);
		setTitle("Bird Sim");	
		addKeyListener(this);
	}
	
	public void start() {
		model = new Model();
		view = new View();
		Model.state = Model.GameState.Title;
		setLayout(new BorderLayout());
		this.add(BorderLayout.CENTER, view);
		view.setModel(model);
		
		try {view.loadMap("/Animation/resources/map.txt");} 
		catch (IOException e) {e.printStackTrace();}
		view.loadAssets();
		
		animation = new Thread(view);
		animation.start();
	}
	
	@SuppressWarnings("static-access")
	public void keyPressed(KeyEvent e) {
		
		switch (e.getKeyCode()) {
			
			case KeyEvent.VK_UP: case KeyEvent.VK_W:
				if (Model.getBird().isAlive())
					Model.getBird().jump();
				break;
				
			case KeyEvent.VK_DOWN: case KeyEvent.VK_S:
				if (Model.getBird().isAlive())
					Model.getBird().diveBomb();
				break;
			
			case KeyEvent.VK_LEFT: case KeyEvent.VK_A:
				if (Model.getBird().isAlive())
					Model.getBird().moveLeft();
				break;
			
			case KeyEvent.VK_RIGHT: case KeyEvent.VK_D:
				if (Model.getBird().isAlive())
					Model.getBird().moveRight();
				break;
			
			case KeyEvent.VK_SPACE: case KeyEvent.VK_K:
				if (Model.getBird().isAlive())
					Model.getBird().glide();
				break;
				
			case KeyEvent.VK_ENTER:
				if (Model.state == Model.state.Title) 
					Model.state = Model.GameState.Running;
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
				JFrame frame = new Controller();
				((Controller) frame).start();
			}
		});
	}
}
