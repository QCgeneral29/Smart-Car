// Test.java is a prototype for the smart cars assignment. Much of this
// code can be used in the main game loop.

import java.util.ArrayList;

// TODO clean up imports
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

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
        
        ArrayList<String> input = new ArrayList<String>();
        
        // Event handling for key presses
        scene.setOnKeyPressed(
        		new EventHandler<KeyEvent>() {
        			public void handle(KeyEvent e) {
        				String code = e.getCode().toString();
        				
        				if (!input.contains(code)) {
        					input.add(code);
        				}
        			}
        		}
        		);
        
        // Event handling for key releases
        scene.setOnKeyReleased(
        		new EventHandler<KeyEvent>() {
        			public void handle(KeyEvent e) {
        				String code = e.getCode().toString();
        				input.remove(code);
        			}
        		}
        		);
        
        // Sets GraphicsContext to 2D. We will use gc to draw images/text
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        Sprite car = new Sprite("images\\cars\\pink_car.png", WIDTH / 2, HEIGHT / 2, true);
        int carSpeed = 3;
        
        Sprite road1 = new Sprite("images\\backgrounds\\road.png", 0, -HEIGHT, true);
        Sprite road2 = new Sprite("images\\backgrounds\\road.png", 0, 0, true);
                
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
        		
        		// set car movement direction based off of input received.
        		byte xDirection = 0, yDirection = 0;
        		
        		if(input.contains("A")) {
        			xDirection--;
        		}
        		if (input.contains("D")) {
        			xDirection++;
        		}
        		
        		if(input.contains("W")) {
        			yDirection--;
        		}
        		if (input.contains("S")) {
        			yDirection++;
        		}
        		
        		// Set and draw car position
        		car.setPosition(car.getXPosition() + (xDirection * carSpeed), car.getYPosition() + (yDirection * carSpeed));
        		gc.drawImage(car.getImage(), car.getXPosition(), car.getYPosition());

        	}
        }.start();
        
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
