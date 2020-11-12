import javafx.scene.canvas.GraphicsContext;

public class Player extends Sprite{
	private int health = 100;
	private int speed = 3;
	private long hitDelay = 0;
	private long hackPenalty = 0;
	
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
		// If hacked, player drives off road.
		if (hackPenalty < System.currentTimeMillis()) {
			this.movePlayer(xDirection, yDirection, this.speed);
		}else {
			// Drive player off screen.
			if(this.getXPosition() > 256) {
				xDirection = 1;
			}else {
				xDirection = -1;
			}
			this.movePlayer(xDirection, yDirection, this.speed / 2);
		}
		this.draw(gc);
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * When called will cause the player to drive off the road. They drive towards
	 * the closest wall.
	 * @param hackTime The time in milliseconds that the player will be hacked.
	 * There is a 3 second grace period after the hack.
	 */
	public void hack(long hackTime) {
		// There is a 3 second delay between hacks
		if(hackPenalty + 3000 < System.currentTimeMillis()) {
			hackPenalty = System.currentTimeMillis() + hackTime;
		}
	}
	
	/**
	 * Moves the player in the given x/y direction times speed.
	 * @param xDirection Pass a value between 1 and -1. 
	 * @param yDirection Pass a value between 1 and -1.
	 * @param speed The Speed at which the player will move.
	 */
	private void movePlayer(byte xDirection, byte yDirection, int speed) {
		if(this.getXPosition() >= (512 - this.getImage().getWidth()) && xDirection == 1) xDirection = 0;
		if(this.getXPosition() <= 0 && xDirection == -1) xDirection = 0;
		if(this.getYPosition() >= (512 - this.getImage().getHeight()) && yDirection == 1) yDirection = 0;
		if(this.getYPosition() <= 0 && yDirection == -1) yDirection = 0;

		this.setPosition(this.getXPosition() + (xDirection * speed), 
						 this.getYPosition() + (yDirection * speed));
	}
}
