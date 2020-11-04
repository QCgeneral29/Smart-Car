import javafx.scene.canvas.GraphicsContext;

public class Player extends Sprite{
	private int health = 100;
	private int speed = 3;
	
	public Player(String img, int xpos, int ypos) {
		super(img, xpos, ypos);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * This draw methods accepts an x and y direction variable which is expected to range between -1 to 1.
	 * The player will be moved in the desired direction times the current speed. Parent draw is then called.
	 * @param gc The Graphics context used to draw images in the scene
	 * @param xDirection Desired x direction. requires a byte between -1 to 1
	 * @param yDirection Desired y direction. requires a byte between -1 to 1 
	 */
	public void draw(GraphicsContext gc, byte xDirection, byte yDirection) {
		this.setPosition(this.getXPosition() + (xDirection * speed), this.getYPosition() + (yDirection * speed));
		this.draw(gc);
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
}
