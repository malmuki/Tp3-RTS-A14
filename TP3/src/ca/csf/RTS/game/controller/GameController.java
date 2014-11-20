package ca.csf.RTS.game.controller;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;
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

		 RectangleShape rectangle = new RectangleShape(new Vector2f(20, 20));

		while (window.isOpen()) {
			// Fill the window with red
			window.clear(Color.RED);

			window.draw(rectangle);
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
						rectangle.move(7, 0);
						break;
					case A:
						rectangle.move(-7, 0);
						break;
					case W:
						rectangle.move(0, -7);
						break;
					case S:
						rectangle.move(0, 7);
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
