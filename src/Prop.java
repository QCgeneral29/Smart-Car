import java.util.Random;

import javafx.scene.canvas.GraphicsContext; 

public class Prop extends Sprite {
	private int speed;
	private final int HEIGHT;
	
	public Prop(String img, int speed, int HEIGHT) {
		super(img, randPosition(), -50);
		this.speed = speed;
		this.HEIGHT = HEIGHT;
	}
	
	public static int randPosition() {
		Random rand = new Random();
		int[] positions = new int[] {127, 205, 283, 361};
		
		return positions[rand.nextInt(4)];
	}
	
	public void movingDown() {
		super.setPosition(super.getXPosition(), super.getYPosition() + speed);
	}
	
	public void returnUp() {
		super.setPosition(randPosition(), -50);
	}
	
	public void draw(GraphicsContext gc) {
		if (this.getYPosition() >= HEIGHT) {
			this.returnUp();
		}
		
		movingDown();
		
		super.draw(gc);
	}
}
