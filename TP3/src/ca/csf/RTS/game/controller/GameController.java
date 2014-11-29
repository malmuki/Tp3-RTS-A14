package ca.csf.RTS.game.controller;

import java.io.IOException;
import java.nio.file.Paths;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Texture;
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
import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.entity.GameEntity;
import ca.csf.RTS.game.model.Game;
import ca.csf.RTS.game.model.Tile;
import ca.csf.RTS.game.model.sound.MusicPlayer;

public class GameController implements GameEventHandler {

	private static final float SENSITIVITY = 250;
	private static final int SELECTION_THICKNESS = 2;
	private Game game;
	private Texture gui;
	private Texture gazon;
	private boolean isFocused = true;
	private MusicPlayer music;

	public GameController() {
		music = new MusicPlayer();
		game = new Game();
		game.addEventHandler(this);
		try {
			gui = new Texture();
			gui.loadFromFile(Paths.get("./ressource/GUI.png"));
			gazon = new Texture();
			gazon.loadFromFile(Paths.get("./ressource/gazon.jpg"));
			gazon.setRepeated(true);
			gazon.setSmooth(true);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void newGame() {
		game.newGame();

		RenderWindow window = new RenderWindow();
		window.create(VideoMode.getDesktopMode(), Menu.TITLE, WindowStyle.FULLSCREEN);

		window.setFramerateLimit(200);

		// declare une nouvelle vue pour pouvoir la deplacer
		View defaultView = (View) window.getDefaultView();
		View gameView = new View(defaultView.getCenter(), defaultView.getSize());
		gameView.setViewport(new FloatRect(0f, 0f, 0.85f, 1f));
		View guiView = new View(defaultView.getCenter(), defaultView.getSize());
		guiView.setViewport(new FloatRect(0.85f, 0f, 0.15f, 1f));

		// pour que les movement soit constant
		Clock frameClock = new Clock();
		Boolean isLeftButtonPressed = false;

		RectangleShape selection = new RectangleShape();
		selection.setFillColor(Color.TRANSPARENT);
		selection.setOutlineColor(Color.BLACK);
		selection.setOutlineThickness(SELECTION_THICKNESS);

		RectangleShape map = new RectangleShape(new Vector2f(Game.MAP_SIZE * Tile.TILE_SIZE, Game.MAP_SIZE * Tile.TILE_SIZE));
		map.setTexture(gazon);
		map.setTextureRect(new IntRect(0, 0, (int) (Game.MAP_SIZE * Tile.TILE_SIZE), (int) (Game.MAP_SIZE * Tile.TILE_SIZE)));

		music.playMusic(1);
		while (window.isOpen()) {

			music.musicPlaylist();
			if (isFocused) {
				// pour obtenir le temps depuis la derniere frame
				float dt = frameClock.restart().asSeconds();

				window.setView(guiView);
				// draw the GUI
				drawGUI(window);

				window.setView(gameView);

				window.draw(map);
				
				// dessine toutes les entitys
				for (GameEntity gameEntity : game.getAllEntity()) {
					gameEntity.draw(window);
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
					Vector2f mousePos = window.mapPixelToCoords(new Vector2i(Mouse.getPosition().x, Mouse.getPosition().y));

					//pour empecher que la selection depasse de la vue
					if (mousePos.x > gameView.getSize().x && mousePos.x > Game.MAP_SIZE * Tile.TILE_SIZE) {
						mousePos = new Vector2f(mousePos.x - (gameView.getSize().x/2 - gameView.getCenter().x), mousePos.y);
					}
					if (mousePos.y > gameView.getSize().y && mousePos.y > Game.MAP_SIZE * Tile.TILE_SIZE) {
						mousePos = new Vector2f(mousePos.x, mousePos.y - (gameView.getSize().y/2 - gameView.getCenter().y));
					}

					if (isLeftButtonPressed) {
						selection.setSize(new Vector2f(mousePos.x - selection.getPosition().x, mousePos.y
								- selection.getPosition().y));
						game.selectEntity(selection.getPosition(), mousePos);
					} else {
						isLeftButtonPressed = true;
						selection
								.setPosition(window.mapPixelToCoords(new Vector2i(Mouse.getPosition().x, Mouse.getPosition().y)));
					}
				} else {
					isLeftButtonPressed = false;
					selection.setSize(new Vector2f(0, 0));
				}

				if (Mouse.isButtonPressed(Button.RIGHT)) {

				}
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
				case LOST_FOCUS:
					isFocused = false;
					break;
				case GAINED_FOCUS:
					isFocused = true;
					break;
				default:
					break;
				}
				break;
			}
		}
	}

	private void drawGUI(RenderWindow window) {
		RectangleShape gui = new RectangleShape(new Vector2f(VideoMode.getDesktopMode().width,
				VideoMode.getDesktopMode().height));
		gui.setTexture(this.gui);
		window.draw(gui);
	}
}
