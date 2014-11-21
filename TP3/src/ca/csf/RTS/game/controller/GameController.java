package ca.csf.RTS.game.controller;

import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.system.Clock;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

import ca.csf.RTS.Menu.model.Menu;
import ca.csf.RTS.entity.E_Entity;
import ca.csf.RTS.entity.Entity;
import ca.csf.RTS.entity.EntityFactory;
import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.model.Game;

public class GameController implements GameEventHandler {

	private static final float SENSITIVITY = 250;
	private Game game;

	public GameController() {
		game = new Game();
	}

	public void newGame() {
		game.newGame();

		Entity soldat = EntityFactory.getInstance().getEntity(E_Entity.SOLDAT);
		
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

		// pour que les movement soit constant
		Clock frameClock = new Clock();

		while (window.isOpen()) {
			//pour obtenir le temps depuis la derniere frame
			float dt = frameClock.restart().asSeconds();
			// Fill the window with red
			window.clear(Color.RED);

			soldat.draw(window);
			window.draw(circle);
			// Display what was drawn (... the red color!)
			window.display();
			window.setView(view);

			if (Keyboard.isKeyPressed(Key.D)) {
				view.move(dt * SENSITIVITY, 0);
			}
			if (Keyboard.isKeyPressed(Key.A)) {
				view.move(dt * -SENSITIVITY, 0);
			}
			if (Keyboard.isKeyPressed(Key.S)) {
				view.move(0, dt * SENSITIVITY);
			}
			if (Keyboard.isKeyPressed(Key.W)) {
				view.move(0, dt * -SENSITIVITY);
			}

			// Handle events
			for (Event event : window.pollEvents()) {
				KeyEvent keyEvent = event.asKeyEvent();
				switch (event.type) {
				case KEY_PRESSED:
					if (keyEvent.key == Key.ESCAPE) {
						window.close();
					}
					break;
				default:
					break;
				}
				break;
			}
		}
	}
}
// }
