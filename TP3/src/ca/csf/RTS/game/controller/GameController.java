package ca.csf.RTS.game.controller;

import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

import ca.csf.RTS.Menu.model.Menu;
import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.model.Game;

public class GameController implements GameEventHandler {

	private Game game;

	public GameController() {
		game = new Game();
	}

	public void newGame() {
		game.newGame();

		RenderWindow window = new RenderWindow();
		window.create(VideoMode.getDesktopMode(), Menu.TITLE, WindowStyle.FULLSCREEN);

		// Limit the framerate
		window.setFramerateLimit(60);

		CircleShape circle = new CircleShape(150);
		circle.set

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

}
