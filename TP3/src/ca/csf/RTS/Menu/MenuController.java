package ca.csf.RTS.Menu;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

import ca.csf.simpleFx.SimpleFXController;
import javafx.fxml.FXML;

public class MenuController extends SimpleFXController {

	
	public MenuController() {
		
	}
	
	@FXML
	public void startGame() {
		getSimpleFxStage().close();
		//Create the window
		RenderWindow window = new RenderWindow();
		window.create(new VideoMode(1366, 768), "Hello JSFML!", WindowStyle.FULLSCREEN);

		//Limit the framerate
		window.setFramerateLimit(60);

		//Main loop
		while(window.isOpen()) {
		    //Fill the window with red
		    window.clear(Color.RED);

		    //Display what was drawn (... the red color!)
		    window.display();

		    //Handle events
		    for(Event event : window.pollEvents()) {
		    	KeyEvent keyEvent = event.asKeyEvent();
		    	switch (event.type) {
				case KEY_PRESSED:
					if(keyEvent.key == Key.ESCAPE) {
			            //The user pressed the close button
			            window.close();
			        }
					break;
				default:
					break;
				}
		    }
		}
	}

	@FXML
	public void options() {
		
	}

}
