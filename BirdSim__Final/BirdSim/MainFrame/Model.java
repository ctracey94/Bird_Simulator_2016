/**************************************************
 *    											  *
 *    Model.java						              *
 *    from Kilobolt Studios under MIT license     *
 *    heavily modified by Jacob Brown, Conor	  *
 *    Tracey, Zhibin Zhang					      *
 *												  *
 **************************************************/

package MainFrame;
import java.util.ArrayList;

import Elements.Bird;
import Elements.Background;
import Elements.Robot_fire;
import Elements.Robot_helmet;
import Elements.Robot_weak;
import Elements.Tile;

public class Model {
	enum GameState {Title, Running, Dead};
	public static GameState state = GameState.Title;
	
	private static Background bg1 = new Background(0, 0);
	private static Background bg2 = new Background(900, 0);
	
	private static Bird bird = new Bird(225, 50, 61, 44);
	private static Robot_fire robotFire = new Robot_fire(0, 0, 0, 0, 0, 0);
	private static Robot_helmet robotHelmet = new Robot_helmet(0, 0, 0, 0, 0, 0);
	private static Robot_weak robotWeak = new Robot_weak(0, 0, 0, 0, 0, 0);
	@SuppressWarnings("rawtypes")
	private static ArrayList tileArray = View.tileArray;
	
	public static Bird getBird() {
		return bird;
	}
	
	public static Background getBg1() {
		return bg1;
	}
	
	public static Background getBg2() {
		return bg2;
	}
	
	public static Robot_fire getF() {
		return robotFire;
	}
	
	public static Robot_helmet getH() {
		return robotHelmet;
	}
	
	public static Robot_weak getW() {
		return robotWeak;
	}
	
	public void update() {
        if (bird.getY() > 900){
        	state = GameState.Dead;
        }
		bird.update();
		bg1.update();
		bg2.update();
		robotFire.update();
		robotHelmet.update();
		robotWeak.update();
		updateTiles();
	}
	
	public void updateTiles() {
		for (int i = 0; i < tileArray.size(); i++) {
			Tile t = (Tile) tileArray.get(i);
			t.update();
		}
	}

}
