import java.util.Random;

import javafx.scene.canvas.GraphicsContext; 

public class Enemy extends Sprite{
	private int speed;
	
	public Enemy(String img, Boolean isVisible, int speed) {
		super(img, randInt(400,100), 500);
		this.speed = speed;
	}
	
	public static int randInt(int max, int min) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
	
	public void attack(Sprite player, GraphicsContext gc) {
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
		super.draw(gc);
	}

}