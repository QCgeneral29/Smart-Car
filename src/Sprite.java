import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {
	private Image image;
	private int xpos = 0;
	private int ypos = 0;
	private Boolean isVisable = true;
	
	public Sprite(String img, int xpos, int ypos) {
		this.image = new Image(img);
		this.xpos = xpos;
		this.ypos = ypos;
	}
	
	public void draw(GraphicsContext gc) {
		gc.drawImage(image, xpos, ypos);
	}
	
	public void setPosition(int xpos, int ypos) {
		this.xpos = xpos;
		this.ypos = ypos;
	}
	
	public int getXPosition() {
		return this.xpos;
	}
	
	public int getYPosition() {
		return this.ypos;
	}
	
	public void setVisible(Boolean isVisable) {
		this.isVisable = isVisable;
	}
	
	public Boolean isVisible() {
		return this.isVisable;
	}
	
	public Image getImage() {
		return this.image;
	}
}
