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
import org.jsfml.window.Mouse;
import org.jsfml.window.Mouse.Button;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

import ca.csf.RTS.Menu.model.Menu;
import ca.csf.RTS.entity.Entity;
import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.model.Game;
import ca.csf.RTS.game.model.Tile;
import ca.csf.RTS.game.sound.MusicPlayer;

public class GameController implements GameEventHandler {

	private static final float SENSITIVITY = 250;
	private static final int SELECTION_THICKNESS = 2;
	private Game game;
	private MusicPlayer music = new MusicPlayer();

	public GameController() {
		game = new Game();
		game.addEventHandler(this);
	}

	public void newGame() {
		game.newGame();

		RenderWindow window = new RenderWindow();
		window.create(VideoMode.getDesktopMode(), Menu.TITLE, WindowStyle.FULLSCREEN);

		// declare une nouvelle vue pour pouvoir la deplacer
		View defaultView = (View) window.getDefaultView();
		View view = new View(defaultView.getCenter(), defaultView.getSize());

		window.setKeyRepeatEnabled(false);
		// Limit the framerate
		window.setFramerateLimit(60);

		// pour que les movement soit constant
		Clock frameClock = new Clock();
		Boolean isLeftButtonPressed = false;

		RectangleShape selection = new RectangleShape();
		selection.setFillColor(Color.TRANSPARENT);
		selection.setOutlineColor(Color.BLACK);
		selection.setOutlineThickness(SELECTION_THICKNESS);
		music.playMusic(1);

		while (window.isOpen()) {
			music.musicPlaylist();
			// pour obtenir le temps depuis la derniere frame
			float dt = frameClock.restart().asSeconds();
			// Fill the window with red
			window.clear(Color.RED);

			// dessine toutes les entitys
			for (Entity entity : game.getAllEntity()) {
				entity.draw(window);
			}
			window.draw(selection);
			// Display what was drawn (... the red color!)
			window.display();
			window.setView(view);

			if (Keyboard.isKeyPressed(Key.D)) {
				if (view.getCenter().x * 2 < Game.MAP_SIZE * Tile.TILE_SIZE) {
					view.move(dt * SENSITIVITY, 0);
				}
			}
			if (Keyboard.isKeyPressed(Key.A)) {
				if (view.getCenter().x - view.getSize().x / 2 > 0) {
					view.move(dt * -SENSITIVITY, 0);
				}
			}
			if (Keyboard.isKeyPressed(Key.S)) {
				if (view.getCenter().y * 2 < Game.MAP_SIZE * Tile.TILE_SIZE) {
					view.move(0, dt * SENSITIVITY);
				}
			}
			if (Keyboard.isKeyPressed(Key.W)) {
				if (view.getCenter().y - view.getSize().y / 2 > 0) {
					view.move(0, dt * -SENSITIVITY);
				}
			}
			

			// pour la selection des units et des buildings
			if (Mouse.isButtonPressed(Button.LEFT)) {
				if (isLeftButtonPressed) {
					Vector2f mousePos = window.mapPixelToCoords(new Vector2i(Mouse.getPosition().x, Mouse.getPosition().y));
					selection.setSize(new Vector2f(mousePos.x - selection.getPosition().x, mousePos.y
							- selection.getPosition().y));
					game.selectEntity(selection.getPosition(), mousePos);
				} else {
					isLeftButtonPressed = true;
					selection.setPosition(window.mapPixelToCoords(new Vector2i(Mouse.getPosition().x, Mouse.getPosition().y)));
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
