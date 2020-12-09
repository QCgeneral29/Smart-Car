/*	The Main class for the Smart-Car project.
 *  
 *  Made by Landen Love, Mike Bui, Jay Nguyen
 *  
 *  You need this VM argument to run the project:
 *  --module-path "javafx-sdk-11.0.2/lib" --add-modules javafx.controls,javafx.media,javafx.base,javafx.graphics,javafx.swing
 *  
 *  Make sure you use the correct sdk files for your platform.
 */

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


import java.net.URL;

public class Main extends Application{
	
	final private int WIDTH = 512;
	final private int HEIGHT = 512;
	private BorderPane layout;
	private Scene scene1;
	  
	
	
	//Player score
	long score = 0;
	
	@Override
	public void start(Stage stage) throws Exception {
		layout = new BorderPane();
		layout.setStyle("-fx-background-image: url('images/backgrounds/smartidea.jpg')");
		URL menuSource = getClass().getResource("sounds/menu.mp3");
		AudioClip menuSound = new AudioClip(menuSource.toString());
		menuSound.play();
		scene1 = new Scene (layout, WIDTH, HEIGHT);
		stage.setResizable(false);
		Button b = new Button("PLAY");
		Button c = new Button("Instruction");
		layout.setCenter(b);
		layout.setBottom(c);
				
		
		BorderPane ins = new BorderPane();
		ins.setStyle("-fx-background-image: url('images/backgrounds/smarti.png')");
		Text t = new Text("		   This is THE RULEE!!!!! \n"
				+ " 	GOAL: TO GET THE HIGHEST SCORE \n"
				+ "1. Control car with WASD \n"
				+ "2. Hit other cars does 10 damage \n"
				+ "3. Hitting the side of the road does 20 damage \n"
				+ "4. When hacked, your car tries to drive off the \n"
				+ "road \n"
				+ "5. Picking up the health pack gives 20 health.");
		t.setFont(Font.font ("Verdana", FontWeight.BOLD, 17));
		t.setFill(Color.ORANGE);
		ins.setCenter(t);
		Scene scene2 = new Scene (ins, WIDTH, HEIGHT);
		Button back = new Button("Back");
		ins.setBottom(back);
		back.setOnAction(e -> {
			stage.setScene(scene1);
		});
		
		
		stage.setScene(scene1);
		stage.show();
		
		c.setOnAction(e -> {
			stage.setScene(scene2);
			
		});
		
		
		
		
		// Setup window and scene
		stage.setTitle("Smart-Car");
        stage.setResizable(false);
        Group root = new Group();
        Scene scene = new Scene(root);
//        stage.setScene(scene);
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
		URL playingSource = getClass().getResource("sounds/playing.mp3");
		AudioClip playingSound = new AudioClip(playingSource.toString());
		playingSound.setCycleCount(AudioClip.INDEFINITE);
		//playingSound.play();
		
		// This sound is for speeding up (pressing "W")
		URL speedingSource = getClass().getResource("sounds/speed_up.mp3");
		AudioClip speedingSound = new AudioClip(speedingSource.toString());
		speedingSound.setRate(1.0);
		
		// This sound is for slowing down (pressing "S")
		URL stopSource = getClass().getResource("sounds/stop.mp3");
		AudioClip stopSound = new AudioClip(stopSource.toString());
		stopSound.setRate(1.0);
		
		// This sound is for when the game is over
		URL gameOverSource = getClass().getResource("sounds/game_over.mp3");
		AudioClip gameOverSound = new AudioClip(gameOverSource.toString()); 
		
		// We use a Timeline to run the main gameLoop. We set it to run indefinitely 
		Timeline gameLoop = new Timeline();
		gameLoop.setCycleCount(Timeline.INDEFINITE);
		
		// Enemy hacker car
		Enemy enemycar = new Enemy("images/cars/hacker_car.png", -50, -50, 1, HEIGHT);
		colliders.add(enemycar);
		enemycar.setRandomLocation();
		
		// Prop cars
		Prop prop1 = new Prop("images/cars/pink_car.png", 3, 600);
		Prop prop2 = new Prop("images/cars/green_truck.png", 3, 800);
		Prop prop3 = new Prop("images/cars/red_car.png", 3, 700);
		
		// Health pack
		HealthPack healthPack = new HealthPack("images/healthpack/health_pack.png", 3, 1000);
		
		// Add all sprites to arrays
		sprites.add(prop1);
		sprites.add(prop2);
		sprites.add(prop3);
		sprites.add(healthPack);
		colliders.add(prop1);
		colliders.add(prop2);
		colliders.add(prop3);
		colliders.add(healthPack);
		
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
                    	// If player health is 0, game ends
		        		if(player.getHealth() <= 0) {
		        			bg.draw(gc);
		        			bg.draw(gc);
		        			bg.setSpeed(0);
		        			
		        			gc.setFont(new Font(70));
			        		gc.setFill(Color.RED);
		        			gc.fillText("GAME OVER!!!", 40, 200);
		        			
		        			gc.setFont(new Font(40));
			        		gc.setFill(Color.WHITE);
			        		gc.fillText("Score: " + String.valueOf(score), 40, 260);
			        		gc.setFont(new Font(20));
			        		gc.fillText("Press R to restart", 40, 300);
			        		
			        		playingSound.stop();
			        		if (!gameOverSound.isPlaying())
			        			gameOverSound.play();
			        		
		        			
		        			if(input.isKeyPressed("R")) {
		        				// Reset sprites
			        			for(int i = 0; i < sprites.size(); i++) {
			        				sprites.get(i).reset();
			        			}
			        			// Reset player, enemy, and score
			        			enemycar.reset();
			        			player.reset();
			        			score = 0;
			        			
			        			bg.setSpeed(7);
			        			
			        			// Stop game over sound
			        			gameOverSound.stop();
			        		}
		        		}else {
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
                    					player.attackPlayer(10);
                    					break;
                    				case "HealthPack":
                    					colliders.get(i).setVisible(false);
                    					player.gainHealth(20);
                    					break;
                    			}
                    		}
                    	}
                    	
                    	// check if Player is driving for sound
                    	if (!playingSound.isPlaying())
                    		playingSound.play();
                    	
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
		        			if (!speedingSound.isPlaying())
		        				speedingSound.play();
		        		}
		        		if (input.isKeyPressed("S")) {
		        			yDirection++;
		        			if (!stopSound.isPlaying())
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
		        		
		        		}
                    }
                });
        
        gameLoop.getKeyFrames().add(kf);
//        gameLoop.play();
        
        
        b.setOnAction (e -> {
			stage.setScene(scene);
			gameLoop.play();
			menuSound.stop();
			playingSound.play();
		});

     

	}
	
	public static void main(String[] args) {
		launch();
	}

}
