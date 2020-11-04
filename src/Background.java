import javafx.scene.canvas.GraphicsContext;

public class Background {
	private Sprite background1, background2;
	private final int HEIGHT;
	private int speed;
	
	/**
	 * Draws a looping background. Draw method both changes sprite location and draws image to scene.
	 * @param img String that is used to find image file in project folder. EX: images/cars/pink_car.png
	 * @param speed Controls the "speed" of the moving background.
	 * @param HEIGHT Set this parameter to window height. Affects when sprite location is reset.
	 */
	public Background(String img, int speed, int HEIGHT) {
		background1 = new Sprite(img, 0, -HEIGHT);
		background2 = new Sprite(img, 0, 0);
		this.HEIGHT = HEIGHT;
		this.speed = speed;
	}
	
	/**
	 * Moves the background times the current speed then draws to the given GraphicsContext object.
	 * @param gc
	 */
	public void draw(GraphicsContext gc) {
		if(background1.getYPosition() >= HEIGHT) {
			background1.setPosition(0, -HEIGHT);
		}
		if(background2.getYPosition() >= HEIGHT) {
			background2.setPosition(0, -HEIGHT);
		}
		
		background1.setPosition(0, background1.getYPosition() + speed);
		background2.setPosition(0, background2.getYPosition() + speed);
		
		background1.draw(gc);
		background2.draw(gc);
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
