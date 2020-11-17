/*	The Main class for the Smart-Car project.
 *  
 *  Made by Landen Love, Mike Bui, Jay Nguyen
 *  
 *  You need this VM argument to run the project:
 *  --module-path "C:\Program Files\javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.media,javafx.base,javafx.graphics,javafx.swing
 */

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;

public class Main extends Application{
	
	final private int WIDTH = 512;
	final private int HEIGHT = 512;
	
	//Player score
	long score = 0;
	
	@Override
	public void start(Stage stage) throws Exception {
		// Setup window and scene
		stage.setTitle("Smart-Car");
        stage.setResizable(false);
        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        
        // This is passed to everything we want to draw on screen ex: draw(ex)
        GraphicsContext gc = canvas.getGraphicsContext2D();
                
        // Setup the looping background
		Background bg = new Background("images/backgrounds/road.png", 7, HEIGHT);
		
		// Player input and player character
		InputHandler input = new InputHandler(scene);
		Player player = new Player("images/cars/blue_car.png",  WIDTH / 2, HEIGHT / 2);
		
		// This array list should be used for adding new sprites to the scene. 
		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
		// This array list should be used for Sprites with collision.
		ArrayList<Sprite> colliders = new ArrayList<Sprite>();
		
		// This is the driving sound we run indefinitely
		URL drivingSource = getClass().getResource("sounds/driving.mp3");
		AudioClip drivingSound = new AudioClip(drivingSource.toString());
		drivingSound.setCycleCount(AudioClip.INDEFINITE);
		drivingSound.play();
		

		URL stopSource = getClass().getResource("sounds/stop.mp3");
		AudioClip stopSound = new AudioClip(stopSource.toString());
		stopSound.setRate(1.0);
		
		// We use a Timeline to run the main gameLoop. We set it to run indefinitely 
		Timeline gameLoop = new Timeline();
		gameLoop.setCycleCount(Timeline.INDEFINITE);
		
		// Enemy hacker car
		Enemy enemycar = new Enemy("images/cars/hacker_car.png", -50, -50, 1);
		colliders.add(enemycar);
		enemycar.setRandomLocation();
		
		// Prop cars
		Prop prop1 = new Prop("images/cars/pink_car.png", 3, 600);
		Prop prop2 = new Prop("images/cars/green_truck.png", 3, 800);
		
		// Add all sprites to arrays
		sprites.add(prop1);
		sprites.add(prop2);
		colliders.add(prop1);
		colliders.add(prop2);
		
		/**
		 * This is the main game loop. Everything within handle() is called every frame.
		 * The game loop runs at 60 frames per second. This is set by Duration.seconds.
		 */
        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.017), // 60 FPS
                new EventHandler<ActionEvent>()
                {
                    public void handle(ActionEvent ae)
                    {
                    	// Detect collision with player
                    	for(int i = 0; i < colliders.size(); i++) {
                    		if(player.intersects(colliders.get(i))) {
                    			switch(colliders.get(i).getClass().getName()) {
                    				case "Enemy":
                    					player.hack(3000);
                    					Enemy hacker = (Enemy) colliders.get(i);
                    					hacker.setRetreat(true);
                    					break;
                    				case "Prop":
                    					player.attackPlayer(50);
                    					break;
                    			}
                    		}
                    	}
                    	
                    	// check if Player's driving for sound
                    	if (drivingSound.isPlaying() == false)
                    		drivingSound.play();
                    	
		        		// Controls background loop
                    	// DO NOT DRAW ANY IMAGES ABOVE THE BACKGROUND. THEY WILL NOT
                    	// SHOW UP ON SCREEN
		        		bg.draw(gc);
		        		
		        		// Draw all sprites.
		        		for(int i = 0; i < sprites.size(); i++) {
                    		sprites.get(i).draw(gc);
                    	}
		        		
		        		// Player controls
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
		        			drivingSound.stop();
		        			if (stopSound.isPlaying() == false)
		        				stopSound.play();
		        		}
		        		
		        		// draw and move enemy
		        		enemycar.attack(player, gc);
		        		
		        		// draw and move player
		        		player.draw(gc, xDirection, yDirection);
		        		
		        		gc.setFont(new Font(50));
		        		gc.setFill(Color.WHITE);
		        		gc.fillText("Health: " + String.valueOf(player.getHealth()), 130, 50);
		        		
		        		// Add to score
		        		score++;
		        		gc.setFont(new Font(30));
		        		gc.fillText("Score: " + String.valueOf(score), 0, 500);
		        		
		        		if(player.getHealth() <= 0) {
		        			bg.draw(gc);
		        			bg.draw(gc);
		        			
		        			gc.setFont(new Font(70));
			        		gc.setFill(Color.RED);
		        			gc.fillText("GAME OVER!!!", 40, 200);
		        			
		        			gc.setFont(new Font(40));
			        		gc.setFill(Color.WHITE);
			        		gc.fillText("Score: " + String.valueOf(score), 40, 260);
		        			gameLoop.stop();
		        		}
                    }
                });

        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();
        
        stage.show();
        
        
	}
	
	public static void main(String[] args) {
		launch();
	}

}
