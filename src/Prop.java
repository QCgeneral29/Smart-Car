import java.util.Random; 

public class Prop extends Sprite {
	private int speed;
	
	public Prop(String img, Boolean isVisible, int speed) {
		super(img, randInt(400,100), -50, isVisible);
		this.speed = speed;
	}
	
	public static int randInt(int max, int min) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
	
	public void movingDown() {
		super.setPosition(super.getXPosition(), super.getYPosition() + speed);
	}
	
	public void returnUp() {
		super.setPosition(randInt(400,100), -50);
	}
	
	
}
