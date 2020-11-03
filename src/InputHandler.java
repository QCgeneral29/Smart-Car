import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

/**
 * Helper class that returns input. Requires a scene object.
 *
 */
public class InputHandler {
	private ArrayList<String> input = new ArrayList<String>();
	
	public InputHandler (Scene scene) {
		
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
	}
	
	// Returns true if the given key is pressed
	public Boolean isKeyPressed(String key) {
		return input.contains(key);
	}
}
