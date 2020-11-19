import java.util.Random;

import javafx.scene.canvas.GraphicsContext; 

public class Enemy extends Sprite{
	private int speed;
	private boolean retreat = false;
	private final int HEIGHT;
	
	public Enemy(String img, int xpos, int ypos, int speed, int HEIGHT) {
		super(img, xpos, ypos);
		this.speed = speed;
		this.HEIGHT = HEIGHT;
	}
	
	public static int randInt(int max, int min) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
	
	public void attack(Sprite player, GraphicsContext gc) {
		if(retreat) {
			super.setPosition(super.getXPosition(), super.getYPosition() + speed);
			
			if(super.getYPosition() > HEIGHT) {
				setRetreat(false);
				this.setRandomLocation();
			}
		}else {
			if(!this.intersects(player)) {
				int enemyX = super.getXPosition();
				int enemyY = super.getYPosition();
				
				int playerX = player.getXPosition();
				int playerY = player.getYPosition();
				
				if (enemyX > playerX)
					enemyX = enemyX - this.speed;
				else if (enemyX < playerX)
					enemyX = enemyX + this.speed;
					
				if (enemyY > playerY)
					enemyY = enemyY - this.speed;
				else if (enemyY < playerY)
					enemyY = enemyY + this.speed;
				
				super.setPosition(enemyX, enemyY);
		}
		}
		
		super.draw(gc);
	}
	
	public void setRandomLocation() {
		this.setPosition(randInt(400,100), 512);
	}
	
	public void setRetreat(boolean retreat) {
		this.retreat = retreat;
	}

	@Override
	public void reset() {
		super.setPosition(this.HEIGHT / 2, this.HEIGHT);
	}
}
