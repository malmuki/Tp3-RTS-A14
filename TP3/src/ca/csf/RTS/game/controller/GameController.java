package ca.csf.RTS.game.controller;

import java.util.ArrayList;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.Mouse.Button;
import org.jsfml.window.Mouse;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

import ca.csf.RTS.Menu.model.Menu;
import ca.csf.RTS.entity.Entity;
import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.model.Game;
import ca.csf.RTS.game.model.Tile;

public class GameController implements GameEventHandler {

	private static final float SENSITIVITY = 250;
	private static final int SELECTION_THICKNESS = 2;
	private Game game;

	public GameController() {
		game = new Game();
		game.addEventHandler(this);
	}

	public void newGame() {
		game.newGame();

		RenderWindow window = new RenderWindow();
		window.create(VideoMode.getDesktopMode(), Menu.TITLE,
				WindowStyle.FULLSCREEN);

		// declare une nouvelle vue pour pouvoir la deplacer
		View defaultView = (View) window.getDefaultView();
		View gameView = new View(defaultView.getCenter(), defaultView.getSize());
		//View uiView = new View(defaultView.getCenter(), defaultView.getSize());

		// pour que les movement soit constant
		Clock frameClock = new Clock();
		Boolean isLeftButtonPressed = false;

		RectangleShape selection = new RectangleShape();
		selection.setFillColor(Color.TRANSPARENT);
		selection.setOutlineColor(Color.BLACK);
		selection.setOutlineThickness(SELECTION_THICKNESS);
		
		while (window.isOpen()) {
			
			window.setView(gameView);
			// pour obtenir le temps depuis la derniere frame
			float dt = frameClock.restart().asSeconds();
			// Fill the window with red
			window.clear(Color.RED);

			// dessine toutes les entitys
			for (Entity entity : game.getAllEntity()) {
				entity.draw(window);
			}
			window.draw(selection);
			// Display what was drawn
			window.display();
			

			if (Keyboard.isKeyPressed(Key.D)) {
				if (gameView.getCenter().x * 2 < Game.MAP_SIZE * Tile.TILE_SIZE) {
					gameView.move(dt * SENSITIVITY, 0);
				}
			}
			if (Keyboard.isKeyPressed(Key.A)) {
				if (gameView.getCenter().x - gameView.getSize().x / 2 > 0) {
					gameView.move(dt * -SENSITIVITY, 0);
				} else {
					gameView.setCenter(gameView.getSize().x / 2, gameView.getCenter().y);
				}
			}
			if (Keyboard.isKeyPressed(Key.S)) {
				if (gameView.getCenter().y * 2 < Game.MAP_SIZE * Tile.TILE_SIZE) {
					gameView.move(0, dt * SENSITIVITY);
				}
			}
			if (Keyboard.isKeyPressed(Key.W)) {
				if (gameView.getCenter().y - gameView.getSize().y / 2 > 0) {
					gameView.move(0, dt * -SENSITIVITY);
				}
			}

			// pour la selection des units et des buildings
			if (Mouse.isButtonPressed(Button.LEFT)) {
				if (isLeftButtonPressed) {
					Vector2f mousePos = window.mapPixelToCoords(new Vector2i(
							Mouse.getPosition().x, Mouse.getPosition().y));
					selection.setSize(new Vector2f(mousePos.x
							- selection.getPosition().x, mousePos.y
							- selection.getPosition().y));
					game.selectEntity(selection.getPosition(), mousePos);
				} else {
					isLeftButtonPressed = true;
					selection.setPosition(window.mapPixelToCoords(new Vector2i(
							Mouse.getPosition().x, Mouse.getPosition().y)));
				}
			} else {
				isLeftButtonPressed = false;
				selection.setSize(new Vector2f(0, 0));
			}

			if (Mouse.isButtonPressed(Button.RIGHT)) {

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

	@Override
	public void highlightSelected(ArrayList<Entity> entity) {
		
	}
}
