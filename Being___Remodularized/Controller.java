import java.applet.Applet;
import java.awt.Frame;

public class Controller extends Applet {
	
	Thread animation;
	Model model = new Model();
	View view = new View();
	
	public void init() {
		setSize(900, 600);
		setFocusable(true);
		
		setLayout(new BorderLayout());
		this.add(BorderLayout.CENTER, view);
		
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Factory Bird");	
		
		animation = new Thread(view);
		animation.start();
		
		// preparing parameters for view to display
		view.setDelay(17); // view.delay is always 17
		view.setModel(model);
		view.setController(this);
	}
	
	public void start() {
		view.setAlive(true);
		view.setRunning(true);
	}
	
	public void stop() {
		view.setRunning(false);
	}
	
	public void destroy() {
		view.setAlive(false);
	}
}
