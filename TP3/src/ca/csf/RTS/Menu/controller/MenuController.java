package ca.csf.RTS.Menu.controller;

import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
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
		// Create the window
		RenderWindow window = new RenderWindow();
		window.create(new VideoMode(1366, 768), "Hello JSFML!",
				WindowStyle.FULLSCREEN);

		// Limit the framerate
		window.setFramerateLimit(60);

		CircleShape circle = new CircleShape(150);
		while (window.isOpen()) {
			// Fill the window with red
			window.clear(Color.RED);

			window.draw(circle);
			// Display what was drawn (... the red color!)
			window.display();

			// Handle events
			for (Event event : window.pollEvents()) {
				KeyEvent keyEvent = event.asKeyEvent();
				switch (event.type) {
				case KEY_PRESSED:
					switch (keyEvent.key) {
					case ESCAPE:
						window.close();
						break;
					case D:
						circle.move(7, 0);
						break;
					case A:
						circle.move(-7, 0);
						break;
					case W:
						circle.move(0, -7);
						break;
					case S:
						circle.move(0, 7);
						break;
					default:
						break;
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
