import javafx.scene.canvas.GraphicsContext;

public class Background {
	private Sprite background1, background2;
	private final int HEIGHT;
	private int speed;
	
	public Background(String img, int speed, int HEIGHT) {
		background1 = new Sprite(img, 0, -HEIGHT);
		background2 = new Sprite(img, 0, 0);
		this.HEIGHT = HEIGHT;
		this.speed = speed;
	}
	
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
}
