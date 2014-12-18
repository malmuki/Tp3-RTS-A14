package ca.csf.RTS.game;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Font;
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
import org.jsfml.graphics.Text;

import ca.csf.RTS.Menu.model.Menu;
import ca.csf.RTS.game.entity.GameObject;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.Fightable;
import ca.csf.RTS.game.entity.controllableEntity.human.FootMan;
import ca.csf.RTS.game.entity.ressource.Ressource;
import ca.csf.RTS.game.sound.MusicPlayer;

public class GameController {

	private static final float SENSITIVITY = 250;
	private static final int SELECTION_THICKNESS = 2;
	private Game game;
	private Texture gui;
	private Texture gazon;
	private boolean isFocused = true;
	private MusicPlayer music;
	private Texture rockIconTexture;
	private Texture treeIconTexture;
	private Texture footman;
	private Texture worker;
	private Texture treeSprite;
	private Text labelTreeRessource = new Text();
	private Text labelRockRessource = new Text();
	private Text selectedEntityHP = new Text();
	private Text selectedEntityName = new Text();
	private Text selectedEntityDamage = new Text();
	private Font arial = new Font();
	private int UISizeWidth = VideoMode.getDesktopMode().width;
	private int UISizeHeight = VideoMode.getDesktopMode().height;
	private RectangleShape guiRectangle = new RectangleShape(new Vector2f(UISizeWidth, UISizeHeight));
	private RectangleShape rockRessource = new RectangleShape(new Vector2f(350, 35));
	private RectangleShape treeRessource = new RectangleShape(new Vector2f(750, 90));
	private RectangleShape selectedEntityIcon = new RectangleShape(new Vector2f(1200, 200));

	public GameController() {
		music = new MusicPlayer();
		game = new Game();
		try {
			arial.loadFromFile(Paths.get("./ressource/ARIBLK.TTF"));
			treeSprite = new Texture();
			treeSprite.loadFromFile(Paths.get("./ressource/Tree_Sprite.png"));
			footman = new Texture();
			footman.loadFromFile(Paths.get("./ressource/Soldat.png"));
			worker = new Texture();
			// worker.loadFromFile(Paths.get("./ressource/Worker.png"));
			treeIconTexture = new Texture();
			treeIconTexture.loadFromFile(Paths.get("./ressource/tree.png"));
			rockIconTexture = new Texture();
			rockIconTexture.loadFromFile(Paths.get("./ressource/rock.png"));
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

	public void newGame() throws IOException {
		game.newGame();

		RenderWindow window = new RenderWindow();
		window.create(VideoMode.getDesktopMode(), Menu.TITLE, WindowStyle.FULLSCREEN);

		window.setFramerateLimit(60);

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

			if (isFocused) {
				music.musicPlaylist();

				float dt = frameClock.restart().asSeconds();
				game.doTasks(dt);
				// pour obtenir le temps depuis la derniere frame

				window.setView(guiView);
				// draw the GUI
				drawGUI(window, game);

				window.setView(gameView);

				window.draw(map);

				// dessine toutes les entitys
				for (GameObject gameObject : game.getAllEntity()) {
					gameObject.draw(window);
				}

				window.draw(selection);

				// Display what was drawn
				window.display();

				if (Keyboard.isKeyPressed(Key.D)) {
					if (gameView.getCenter().x + (gameView.getSize().x / 2) < Game.MAP_SIZE * Tile.TILE_SIZE) {
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
					if (gameView.getCenter().y + (gameView.getSize().y / 2) < Game.MAP_SIZE * Tile.TILE_SIZE) {
						gameView.move(0, dt * SENSITIVITY);
					}
				}
				if (Keyboard.isKeyPressed(Key.W)) {
					if (gameView.getCenter().y - gameView.getSize().y / 2 > 0) {
						gameView.move(0, dt * -SENSITIVITY);
					}
				}

				// pour la selection des units et des buildings
				Vector2f mousePos = window.mapPixelToCoords(new Vector2i(Mouse.getPosition().x, Mouse.getPosition().y));
				if (mousePos.x < gameView.getCenter().x + (gameView.getSize().x / 2)) {
					if (Mouse.isButtonPressed(Button.LEFT)) {

						// pour empecher que la selection depasse de la vue
						if (mousePos.x > gameView.getSize().x && mousePos.x > Game.MAP_SIZE * Tile.TILE_SIZE) {
							mousePos = new Vector2f(mousePos.x - (gameView.getSize().x / 2 - gameView.getCenter().x), mousePos.y);
						}
						if (mousePos.y > gameView.getSize().y && mousePos.y > Game.MAP_SIZE * Tile.TILE_SIZE) {
							mousePos = new Vector2f(mousePos.x, mousePos.y - (gameView.getSize().y / 2 - gameView.getCenter().y));
						}

						if (isLeftButtonPressed) {
							selection.setSize(new Vector2f(mousePos.x - selection.getPosition().x, mousePos.y - selection.getPosition().y));
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

						game.giveOrder(mousePos);
					}
					// pour les selection dans le UI
				} else {
					
				}
			}

			// Handle events
			for (Event event : window.pollEvents()) {
				KeyEvent keyEvent = event.asKeyEvent();
				switch (event.type) {
				case KEY_PRESSED:
					if (keyEvent.key == Key.ESCAPE) {
						window.close();
					} else if (keyEvent.key == Key.NUM0) {
						game.allo();
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

	private void drawGUI(RenderWindow window, Game game) throws IOException {

		labelTreeRessource.setFont(arial);
		labelTreeRessource.setCharacterSize(50);
		labelTreeRessource.setColor(Color.WHITE);
		labelTreeRessource.setScale(3, 0.5f);

		labelRockRessource.setFont(arial);
		labelRockRessource.setCharacterSize(50);
		labelRockRessource.setColor(Color.WHITE);
		labelRockRessource.setScale(3, 0.5f);

		selectedEntityHP.setFont(arial);
		selectedEntityHP.setCharacterSize(40);
		selectedEntityHP.setColor(Color.CYAN);
		selectedEntityHP.setScale(3, 0.5f);

		selectedEntityName.setFont(arial);
		selectedEntityName.setCharacterSize(40);
		selectedEntityName.setColor(Color.CYAN);
		selectedEntityName.setScale(3, 0.5f);

		selectedEntityDamage.setFont(arial);
		selectedEntityDamage.setCharacterSize(40);
		selectedEntityDamage.setColor(Color.CYAN);
		selectedEntityDamage.setScale(3, 0.5f);

		rockRessource.setPosition(UISizeWidth * (0.20f), UISizeHeight * 0.05f);
		treeRessource.setPosition(UISizeWidth * (0.20f), UISizeHeight * 0.1f);
		selectedEntityIcon.setPosition(UISizeWidth * (0.20f), UISizeHeight * 0.50f);
		selectedEntityHP.setPosition(UISizeWidth * (0.20f), UISizeHeight * 0.70f);
		selectedEntityName.setPosition(UISizeWidth * (0.20f), UISizeHeight * 0.48f);
		selectedEntityDamage.setPosition(UISizeWidth * (0.20f), UISizeHeight * 0.725f);
		labelRockRessource.setPosition(UISizeWidth * (0.45f), UISizeHeight * 0.055f);
		labelTreeRessource.setPosition(UISizeWidth * (0.45f), UISizeHeight * 0.11f);

		if (!game.getAllSelected().isEmpty()) {
			selectedEntityIcon.setFillColor(Color.WHITE);
			switch (game.getAllSelected().get(0).getName()) {
			case "Footman":
				FootMan entity = (FootMan) game.getAllSelected().get(0);
				selectedEntityName.setString(entity.getName());
				selectedEntityDamage.setString("Damage : " + Integer.toString(entity.getDamage()));
				if (!selectedEntityIcon.equals(footman)) {
					selectedEntityIcon.setTexture(footman);
				}
				selectedEntityHP.setString("Health : " + Integer.toString(entity.getHP()) + " / " + entity.getMaxHealth());
				break;
			case "Worker":
				selectedEntityName.setString("Worker");
				Fightable entity2 = (Fightable) game.getAllSelected().get(0);
				if (!selectedEntityIcon.equals(worker)) {
					// selectedUnitIcon.setTexture(worker);
				}
				selectedEntityDamage.setString("");
				selectedEntityHP.setString("Health : " + Integer.toString(entity2.getHP()) + " / " + entity2.getMaxHealth());
				break;
			case "TownCenter":
				// TO DO
				// Set building tab to show trainable units
				break;
			case "Barrack":
				// TO DO
				// Set building tab to show trainable units
				break;
			case "Tree":
				Ressource entityRessource = (Ressource) game.getAllSelected().get(0);
				selectedEntityName.setString("Tree");
				if (!selectedEntityIcon.equals(treeSprite)) {
					selectedEntityIcon.setTexture(treeSprite);
				}
				selectedEntityDamage.setString("Ressource : " + Integer.toString(entityRessource.getRessources()));
				selectedEntityHP.setString("");
				break;
			case "Stone":
				break;
			case "WhateverOtherBuildingsWeHave":
				break;
			default:
				break;
			}
		} else {
			selectedEntityIcon.setFillColor(Color.TRANSPARENT);
		}
		guiRectangle.setTexture(this.gui);
		rockRessource.setTexture(this.rockIconTexture);
		treeRessource.setTexture(this.treeIconTexture);

		labelTreeRessource.setString(Integer.toString(game.getPlayerTeam().getWood()));
		labelRockRessource.setString(Integer.toString(game.getPlayerTeam().getStoned()));

		window.draw(guiRectangle);
		window.draw(rockRessource);
		window.draw(treeRessource);
		window.draw(selectedEntityIcon);
		window.draw(selectedEntityHP);
		window.draw(selectedEntityName);
		window.draw(selectedEntityDamage);
		window.draw(labelTreeRessource);
		window.draw(labelRockRessource);
	}

	public MusicPlayer getMusic() {
		return music;
	}
}
