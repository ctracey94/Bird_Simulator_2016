package testPackage;

public class Model {

	public static Bird bird = new Bird(0, 0, 30, 30);
	
	public void update() {
		// TODO Auto-generated method stub
		bird.update();
		
	}
	
	public static Bird getBird(){
		return bird;
	}

}