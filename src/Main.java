/*	The Main class for the Smart-Car project.
 *  
 *  Made by Landen Love, Mike Bui, Jay Nguyen
 *  
 *  Might need VM argument:
 *  --module-path "javafx-sdk-11.0.2/lib" --add-modules javafx.controls
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
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Main extends Application{
	
	final private int WIDTH = 512;
	final private int HEIGHT = 512;
	private BorderPane layout;
	private Scene scene1;
	  
	
	
	@Override
	public void start(Stage stage) throws Exception {
		layout = new BorderPane();
		layout.setStyle("-fx-background-image: url('images/backgrounds/road.png')");
		scene1 = new Scene (layout, WIDTH, HEIGHT);
		stage.setResizable(false);
		Button b = new Button("PLAY");
		Button c = new Button("Instruction");
		layout.setCenter(b);
		layout.setBottom(c);
		
		BorderPane ins = new BorderPane();
		ins.setStyle("-fx-background-image: url('images/backgrounds/road.png')");
		Text t = new Text("This is a text sample \n"
				+ " nothing important");
		t.setFont(Font.font ("Verdana", 20));
		t.setFill(Color.RED);
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
                
		Background bg = new Background("images/backgrounds/road.png", 5, HEIGHT);
		
		InputHandler input = new InputHandler(scene);
		Player player = new Player("images/cars/pink_car.png",  WIDTH / 2, HEIGHT / 2);
		
		// This array list should be used for adding new sprites to the scene. 
		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
		// This array list should be used for Sprites with collision.
		ArrayList<Sprite> colliders = new ArrayList<Sprite>();
		
		// We use a Timeline to run the main gameLoop. We set it to run indefinitely 
		Timeline gameLoop = new Timeline();
		gameLoop.setCycleCount(Timeline.INDEFINITE);
		
		Enemy enemycar = new Enemy("images/cars/blue_car.png", -50, -50, 1);
		colliders.add(enemycar);
		enemycar.setRandomLocation();
		
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
                    			}
                    		}
                    	}
                    	
		        		// Controls background loop
		        		bg.draw(gc);
		        		
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
		        		}
		        		
		        		// draw and move enemy
		        		enemycar.attack(player, gc);
		        		
		        		// draw and move player
		        		player.draw(gc, xDirection, yDirection);
                    }
                });
        
        gameLoop.getKeyFrames().add(kf);
//        gameLoop.play();
        
        
        b.setOnAction (e -> {
			stage.setScene(scene);
			gameLoop.play();
		});

     
//        stage.show();
	}
	
	public static void main(String[] args) {
		launch();
	}

}
