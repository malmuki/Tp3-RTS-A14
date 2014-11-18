package ca.csf.RTS.Menu;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

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
		window.create(new VideoMode(640, 480), "Hello JSFML!");

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
		        if(event.type == Event.Type.CLOSED) {
		            //The user pressed the close button
		            window.close();
		        }
		    }
		}
	}

	@FXML
	public void options() {
		
	}

}
