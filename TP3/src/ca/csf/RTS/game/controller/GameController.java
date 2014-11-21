package ca.csf.RTS.game.controller;

import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
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
		window.create(VideoMode.getDesktopMode(), Menu.TITLE,
				WindowStyle.FULLSCREEN);

		CircleShape circle = new CircleShape(50);
		circle.setOrigin(50, 50);
		circle.setPosition(320 - 50, 240);

		// declare une nouvelle vue pour pouvoir la deplacer
		View defaultView = (View) window.getDefaultView();
		View view = new View(defaultView.getCenter(), defaultView.getSize());

		window.setKeyRepeatEnabled(false);
		// Limit the framerate
		window.setFramerateLimit(60);

		while (window.isOpen()) {
			// Fill the window with red
			window.clear(Color.RED);

			window.draw(circle);
			// Display what was drawn (... the red color!)
			window.display();
			window.setView(view);

			if (Keyboard.isKeyPressed(Key.D)) {
				view.move(7, 0);
			}

			if (Keyboard.isKeyPressed(Key.A)) {
				view.move(-7, 0);
			}

			if (Keyboard.isKeyPressed(Key.S)) {
				view.move(0, 7);
			}

			if (Keyboard.isKeyPressed(Key.W)) {
				view.move(0, -7);
			}

			// Handle events
			for (Event event : window.pollEvents()) {
				KeyEvent keyEvent = event.asKeyEvent();
				switch (event.type) {
				case KEY_PRESSED:
					if (keyEvent.key == Key.ESCAPE) {
						window.close();
					}
				default:
					break;
				}
				break;
			}
		}
	}
}
// }
