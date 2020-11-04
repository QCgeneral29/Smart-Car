// Test.java is a prototype for the smart cars assignment. Much of this
// code can be used in the main game loop.

// TODO clean up imports
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Test extends Application{
	
	//private double FPS = 60;
	private int HEIGHT = 512;
	private int WIDTH = 512;
	
	@Override
    public void start(Stage stage) {
		// Setup stage, root node, scene, and canvas
        stage.setTitle("Smart Car");
        stage.setResizable(false);
        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        
        InputHandler input = new InputHandler(scene);
        
        // Sets GraphicsContext to 2D. We will use gc to draw images/text
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        Sprite car = new Sprite("images/cars/pink_car.png", WIDTH / 2, HEIGHT / 2);
        int carSpeed = 3;
        
        Sprite road1 = new Sprite("images/backgrounds/road.png", 0, -HEIGHT);
        Sprite road2 = new Sprite("images/backgrounds/road.png", 0, 0);
        
        Prop prop1 = new Prop("/images/cars/blue_car.png", true, 2);
        Prop prop2 = new Prop("/images/cars/blue_car.png", true, 3);
        Prop prop3 = new Prop("/images/cars/blue_car.png", true, 4);
        
        ArrayList<Prop> props = new ArrayList<Prop>();
        props.add(prop1);
        props.add(prop2);
        props.add(prop3);
              
        new AnimationTimer() {
        	public void handle(long currentNanoTime) {
        	    //gc.setFill(Color.WHITE);
        	    //gc.fillRect(0, 0, WIDTH, HEIGHT);
        		
        		// Statements for drawing road loop
        		if(road1.getYPosition() >= HEIGHT) {
        			road1.setPosition(0, -HEIGHT);
        		}
        		if(road2.getYPosition() >= HEIGHT) {
        			road2.setPosition(0, -HEIGHT);
        		}
        		
        		road1.setPosition(0, road1.getYPosition() + 5);
        		road2.setPosition(0, road2.getYPosition() + 5);

        		
        		gc.drawImage(road1.getImage(), road1.getXPosition(), road1.getYPosition());
        		gc.drawImage(road2.getImage(), road2.getXPosition(), road2.getYPosition());
        		
        		
        		gc.drawImage(prop1.getImage(), prop1.getXPosition(), prop1.getYPosition());
        		gc.drawImage(prop2.getImage(), prop2.getXPosition(), prop2.getYPosition());
        		gc.drawImage(prop3.getImage(), prop3.getXPosition(), prop3.getYPosition());
        		
        		prop1.movingDown();
        		prop2.movingDown();
        		prop3.movingDown();
        		
        		if (prop1.getYPosition() >= HEIGHT)
        			prop1.returnUp();
        		if (prop2.getYPosition() >= HEIGHT)
        			prop2.returnUp();
        		if (prop3.getYPosition() >= HEIGHT)
        			prop3.returnUp();
        		
        		// set car movement direction based off of input received.
        		byte xDirection = 0, yDirection = 0;
        		
        		if(input.isKeyPressed("A")) {
        			xDirection--;
        		}
        		if (input.isKeyPressed("D")) {
        			xDirection++;
        		}
        		
        		if(input.isKeyPressed("W")) {
        			yDirection--;
        		}
        		if (input.isKeyPressed("S")) {
        			yDirection++;
        		}
        		
        		// Set and draw car position
        		car.setPosition(car.getXPosition() + (xDirection * carSpeed), car.getYPosition() + (yDirection * carSpeed));
        		gc.drawImage(car.getImage(), car.getXPosition(), car.getYPosition());

        		for(int i = 0; i < props.size(); i++) {
        			if(car.intersects(props.get(i))) System.out.println("Car hit!");
        		}
        	}
        }.start();
        
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
