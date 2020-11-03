/*	The Main class for the Smart-Car project.
 *  
 *  Made by Landen Love, Mike Bui, Jay Ng
 *  hiiiiiiiii
 *  
 *  Might need VM argument:
 *  --module-path "javafx-sdk-11.0.2/lib" --add-modules javafx.controls
 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {
		launch();
	}
	
	final private int WIDTH = 512;
	final private int HEIGHT = 512;

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
                
		Background bg = new Background("images/backgrounds/road.png", 5, HEIGHT);
        
        new AnimationTimer() {
        	public void handle(long currentNanoTime) {
        		bg.draw(gc);
        	}
        }.start();
        
        stage.show();
	}

}
