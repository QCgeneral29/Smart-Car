import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {
	private Image image;
	private int xpos = 0;
	private int ypos = 0;
	private Boolean isVisable = true;
	
	/**
	 * General purpose class used to draw images in Smart-Car. Includes helper methods for manipulating
	 * sprite attributes.
	 * @param img String that is used to find image file in project folder. EX: images/cars/pink_car.png
	 * @param xpos Starting x position for Sprite.
	 * @param ypos Starting y position for Sprite.
	 */
	public Sprite(String img, int xpos, int ypos) {
		this.image = new Image(img);
		this.xpos = xpos;
		this.ypos = ypos;
	}
	
	/**
	 * Draws sprite on given GraphicsContext using current image file and x/y location.
	 * @param gc
	 */
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
	
	/**
	 * Returns the image object set in the constructor. 
	 * @return
	 */
	public Image getImage() {
		return this.image;
	}
	
	/**
	 * Returns a Rectangle2D object that is the size of the current image file. Used for detecting collision.
	 * @return
	 */
	public Rectangle2D getBoundary() {
		return new Rectangle2D(xpos, ypos, image.getWidth(), image.getHeight());
	}
	
	/**
	 * Detects if this object is overlapping with a given Sprite object and returns a boolean.
	 * @param object
	 * @return 
	 */
	public boolean intersects(Sprite object) {
		return this.getBoundary().intersects(object.getBoundary());
	}
}
