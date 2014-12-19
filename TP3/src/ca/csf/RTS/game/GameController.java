package ca.csf.RTS.game;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

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
import ca.csf.RTS.game.audio.MusicPlayer;
import ca.csf.RTS.game.entity.GameObject;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.building.WatchTower;
import ca.csf.RTS.game.entity.controllableEntity.building.factory.Barrack;
import ca.csf.RTS.game.entity.controllableEntity.building.factory.Factory;
import ca.csf.RTS.game.entity.controllableEntity.building.factory.TownCenter;
import ca.csf.RTS.game.entity.controllableEntity.Trainee;
import ca.csf.RTS.game.entity.controllableEntity.human.FootMan;
import ca.csf.RTS.game.entity.controllableEntity.human.Worker;
import ca.csf.RTS.game.entity.ressource.Stone;
import ca.csf.RTS.game.entity.ressource.Tree;
import ca.csf.RTS.game.entity.state.Training;

public class GameController {

	private static final float SENSITIVITY = 250;
	private static final int SELECTION_THICKNESS = 2;
	private static final float GUI_SCALE = 0.15f;
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
	private Texture towncenter;
	private Texture barrack;
	private Texture forge;
	private Texture watchtower;
	private Text labelTreeRessource = new Text();
	private Text labelRockRessource = new Text();
	private Text selectedEntityHP = new Text();
	private Text selectedEntityName = new Text();
	private Text selectedEntityDamage = new Text();
	private Text selectedEntityRange = new Text();
	private Text selectedEntityAttackSpeed = new Text();
	private Text progressPourcentageUnit = new Text();
	private Font arial = new Font();
	private int UISizeWidth = VideoMode.getDesktopMode().width;
	private int UISizeHeight = VideoMode.getDesktopMode().height;
	private RectangleShape guiRectangle = new RectangleShape(new Vector2f(
			UISizeWidth, UISizeHeight));
	private RectangleShape rockRessource = new RectangleShape(new Vector2f(
			UISizeWidth * 0.18f, UISizeHeight * 0.04f));
	private RectangleShape treeRessource = new RectangleShape(new Vector2f(
			UISizeWidth * 0.47f, UISizeHeight * 0.10f));
	private RectangleShape selectedEntityIcon = new RectangleShape(
			new Vector2f(UISizeWidth * 0.65f, UISizeHeight * 0.18f));
	private ArrayList<Texture> buildingImageButtons = new ArrayList<Texture>(9);
	private RectangleShape[] buildingTabRectangle = new RectangleShape[6];
	private RectangleShape[] trainingQueueRectangle = new RectangleShape[5];

	// temporary
	private RectangleShape buildingPlacer;

	public GameController() {
		music = new MusicPlayer();
		game = new Game();
		try {
			arial.loadFromFile(Paths.get("./ressource/ARIBLK.TTF"));
			towncenter = new Texture();
			towncenter.loadFromFile(Paths.get("./ressource/towncenter.png"));
			barrack = new Texture();
			barrack.loadFromFile(Paths.get("./ressource/barrack.png"));
			forge = new Texture();
			// forge.loadFromFile(Paths.get("./ressource/forge.png"));
			watchtower = new Texture();
			watchtower.loadFromFile(Paths.get("./ressource/watchtower.png"));
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
			initializeGUI();

			buildingPlacer = new RectangleShape();
			buildingPlacer.setFillColor(Color.TRANSPARENT);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void newGame() throws IOException {
		game.newGame();

		RenderWindow window = new RenderWindow();
		window.create(VideoMode.getDesktopMode(), Menu.TITLE,
				WindowStyle.FULLSCREEN);

		window.setFramerateLimit(0);

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

		RectangleShape map = new RectangleShape(new Vector2f(Game.MAP_SIZE
				* Tile.TILE_SIZE, Game.MAP_SIZE * Tile.TILE_SIZE));
		map.setTexture(gazon);
		map.setTextureRect(new IntRect(0, 0,
				(int) (Game.MAP_SIZE * Tile.TILE_SIZE),
				(int) (Game.MAP_SIZE * Tile.TILE_SIZE)));

		MusicPlayer.musicStop();
		MusicPlayer.playMusic(1);
		while (window.isOpen()) {

			if (isFocused) {
				MusicPlayer.musicPlaylist();

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
				window.draw(buildingPlacer);
				window.draw(selection);

				// Display what was drawn
				window.display();

				if (Keyboard.isKeyPressed(Key.D)) {
					if (gameView.getCenter().x + (gameView.getSize().x / 2) < Game.MAP_SIZE
							* Tile.TILE_SIZE) {
						gameView.move(dt * SENSITIVITY, 0);
					} else {
						gameView.setCenter((Game.MAP_SIZE * Tile.TILE_SIZE)
								- gameView.getSize().x / 2,
								gameView.getCenter().y);
					}
				}
				if (Keyboard.isKeyPressed(Key.A)) {
					if (gameView.getCenter().x - gameView.getSize().x / 2 > 0) {
						gameView.move(dt * -SENSITIVITY, 0);
					} else {
						gameView.setCenter(gameView.getSize().x / 2,
								gameView.getCenter().y);
					}
				}
				if (Keyboard.isKeyPressed(Key.S)) {
					if (gameView.getCenter().y + (gameView.getSize().y / 2) < Game.MAP_SIZE
							* Tile.TILE_SIZE) {
						gameView.move(0, dt * SENSITIVITY);
					} else {
						gameView.setCenter(
								gameView.getCenter().x,
								(Game.MAP_SIZE * Tile.TILE_SIZE)
										- gameView.getSize().y / 2);
					}
				}
				if (Keyboard.isKeyPressed(Key.W)) {
					if (gameView.getCenter().y - gameView.getSize().y / 2 > 0) {
						gameView.move(0, dt * -SENSITIVITY);
					} else {
						gameView.setCenter(gameView.getCenter().x,
								gameView.getSize().y / 2);
					}
				}

				// pour la selection des units et des buildings
				Vector2f mousePos = window.mapPixelToCoords(new Vector2i(Mouse
						.getPosition().x, Mouse.getPosition().y));
				if (buildingPlacer.getFillColor() == Color.TRANSPARENT) {
					if (mousePos.x < gameView.getCenter().x
							+ (gameView.getSize().x / 2)) {
						if (Mouse.isButtonPressed(Button.LEFT)) {

							if (isLeftButtonPressed) {
								selection.setSize(new Vector2f(mousePos.x
										- selection.getPosition().x, mousePos.y
										- selection.getPosition().y));
								game.selectEntity(selection.getPosition(),
										mousePos);
							} else {
								isLeftButtonPressed = true;
								selection.setPosition(window
										.mapPixelToCoords(new Vector2i(Mouse
												.getPosition().x, Mouse
												.getPosition().y)));
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
						if (Mouse.isButtonPressed(Button.LEFT)) {
							if (mousePos.x >= guiView.getSize().x + UISizeWidth
									* GUI_SCALE * 0.20f
									&& mousePos.x <= guiView.getSize().x
											+ UISizeWidth * GUI_SCALE * 0.48f
									&& mousePos.y >= UISizeHeight * 0.20f
									&& mousePos.y <= UISizeHeight * 0.28f) {
								game.btnAction(0, this);
							} else if (mousePos.x >= guiView.getSize().x
									+ UISizeWidth * GUI_SCALE * 0.55f
									&& mousePos.x <= guiView.getSize().x
											+ UISizeWidth * GUI_SCALE * 0.83f
									&& mousePos.y >= UISizeHeight * 0.20f
									&& mousePos.y <= UISizeHeight * 0.28f) {
								game.btnAction(1, this);
							} else if (mousePos.x >= guiView.getSize().x
									+ UISizeWidth * GUI_SCALE * 0.20f
									&& mousePos.x <= guiView.getSize().x
											+ UISizeWidth * GUI_SCALE * 0.48f
									&& mousePos.y >= UISizeHeight * 0.30f
									&& mousePos.y <= UISizeHeight * 0.38f) {
								game.btnAction(2, this);
							} else if (mousePos.x >= guiView.getSize().x
									+ UISizeWidth * GUI_SCALE * 0.55f
									&& mousePos.x <= guiView.getSize().x
											+ UISizeWidth * GUI_SCALE * 0.83f
									&& mousePos.y >= UISizeHeight * 0.30f
									&& mousePos.y <= UISizeHeight * 0.38f) {
								game.btnAction(3, this);
							} else if (mousePos.x >= guiView.getSize().x
									+ UISizeWidth * GUI_SCALE * 0.20f
									&& mousePos.x <= guiView.getSize().x
											+ UISizeWidth * GUI_SCALE * 0.48f
									&& mousePos.y >= UISizeHeight * 0.40f
									&& mousePos.y <= UISizeHeight * 0.48f) {
								game.btnAction(4, this);
							} else if (mousePos.x >= guiView.getSize().x
									+ UISizeWidth * GUI_SCALE * 0.55f
									&& mousePos.x <= guiView.getSize().x
											+ UISizeWidth * GUI_SCALE * 0.83f
									&& mousePos.y >= UISizeHeight * 0.40f
									&& mousePos.y <= UISizeHeight * 0.48f) {
								game.btnAction(5, this);
							}
						}
					}
				} else {
					if (mousePos.x < gameView.getCenter().x
							+ (gameView.getSize().x / 2)) {
						buildingPlacer.setPosition(mousePos);
						if (game.canPlace(
								new Vector2i(Vector2f.div(mousePos,
										Tile.TILE_SIZE)), game
										.getBuildingSize())) {
							buildingPlacer.setSize(new Vector2f(Vector2i.mul(
									game.getBuildingSize(),
									(int) Tile.TILE_SIZE)));
							buildingPlacer.setFillColor(new Color(Color.GREEN,
									100));
							if (Mouse.isButtonPressed(Button.LEFT)) {
								game.build(new Vector2i(Vector2f.div(mousePos,
										Tile.TILE_SIZE)));
								clearBuilding();
							}
						} else {
							buildingPlacer.setFillColor(new Color(Color.RED,
									100));
							if (Mouse.isButtonPressed(Button.LEFT)) {
								clearBuilding();
								// play blocked sound
							}
						}
					}
					if (Mouse.isButtonPressed(Button.RIGHT)) {
						clearBuilding();
					}
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

	// clear the placer
	private void clearBuilding() {
		buildingPlacer.setFillColor(Color.TRANSPARENT);
		buildingPlacer.setSize(new Vector2f(0, 0));
		game.clearBuilding();
	}

	public void setBuildingColor() {
		buildingPlacer.setFillColor(new Color(Color.GREEN, 100));
	}

	private void initializeGUI() {

		for (int i = 0; i < 6; i++) {
			buildingTabRectangle[i] = new RectangleShape(new Vector2f(
					UISizeWidth * 0.28f, UISizeHeight * 0.08f));
		}
		
		for(int i = 0; i < 5; i++){
			trainingQueueRectangle[i] = new RectangleShape(new Vector2f(
					UISizeWidth * 0.10f, UISizeHeight * 0.08f));
		}

		labelTreeRessource.setFont(arial);
		labelTreeRessource.setCharacterSize(50);
		labelTreeRessource.setColor(Color.WHITE);
		labelTreeRessource.setScale(UISizeWidth * 0.0015f,
				UISizeHeight * 0.0005f);

		labelRockRessource.setFont(arial);
		labelRockRessource.setCharacterSize(50);
		labelRockRessource.setColor(Color.WHITE);
		labelRockRessource.setScale(UISizeWidth * 0.0015f,
				UISizeHeight * 0.0005f);

		selectedEntityHP.setFont(arial);
		selectedEntityHP.setCharacterSize(40);
		selectedEntityHP.setColor(Color.CYAN);
		selectedEntityHP
				.setScale(UISizeWidth * 0.0015f, UISizeHeight * 0.0005f);

		selectedEntityName.setFont(arial);
		selectedEntityName.setCharacterSize(40);
		selectedEntityName.setColor(Color.CYAN);
		selectedEntityName.setScale(UISizeWidth * 0.0015f,
				UISizeHeight * 0.0005f);

		selectedEntityDamage.setFont(arial);
		selectedEntityDamage.setCharacterSize(40);
		selectedEntityDamage.setColor(Color.CYAN);
		selectedEntityDamage.setScale(UISizeWidth * 0.0015f,
				UISizeHeight * 0.0005f);

		selectedEntityRange.setFont(arial);
		selectedEntityRange.setCharacterSize(40);
		selectedEntityRange.setColor(Color.CYAN);
		selectedEntityRange.setScale(UISizeWidth * 0.0015f,
				UISizeHeight * 0.0005f);

		selectedEntityAttackSpeed.setFont(arial);
		selectedEntityAttackSpeed.setCharacterSize(40);
		selectedEntityAttackSpeed.setColor(Color.CYAN);
		selectedEntityAttackSpeed.setScale(UISizeWidth * 0.0015f,
				UISizeHeight * 0.0005f);
		
		progressPourcentageUnit.setFont(arial);
		progressPourcentageUnit.setCharacterSize(40);
		progressPourcentageUnit.setColor(Color.CYAN);
		progressPourcentageUnit.setScale(UISizeWidth * 0.0015f,
				UISizeHeight * 0.0005f);

		buildingTabRectangle[0].setPosition(UISizeWidth * 0.20f,
				UISizeHeight * 0.20f);
		buildingTabRectangle[1].setPosition(UISizeWidth * 0.55f,
				UISizeHeight * 0.20f);
		buildingTabRectangle[2].setPosition(UISizeWidth * 0.20f,
				UISizeHeight * 0.30f);
		buildingTabRectangle[3].setPosition(UISizeWidth * 0.55f,
				UISizeHeight * 0.30f);
		buildingTabRectangle[4].setPosition(UISizeWidth * 0.20f,
				UISizeHeight * 0.40f);
		buildingTabRectangle[5].setPosition(UISizeWidth * 0.55f,
				UISizeHeight * 0.40f);
		
		trainingQueueRectangle[0].setPosition(UISizeWidth * 0.15f,
				UISizeHeight * 0.50f);
		trainingQueueRectangle[1].setPosition(UISizeWidth * 0.30f,
				UISizeHeight * 0.50f);
		trainingQueueRectangle[2].setPosition(UISizeWidth * 0.45f,
				UISizeHeight * 0.50f);
		trainingQueueRectangle[3].setPosition(UISizeWidth * 0.60f,
				UISizeHeight * 0.50f);
		trainingQueueRectangle[4].setPosition(UISizeWidth * 0.75f,
				UISizeHeight * 0.50f);


		rockRessource.setPosition(UISizeWidth * 0.20f, UISizeHeight * 0.05f);
		treeRessource.setPosition(UISizeWidth * 0.20f, UISizeHeight * 0.1f);
		selectedEntityName.setPosition(UISizeWidth * 0.20f,
				UISizeHeight * 0.62f);
		selectedEntityIcon.setPosition(UISizeWidth * 0.20f,
				UISizeHeight * 0.65f);
		selectedEntityHP.setPosition(UISizeWidth * 0.20f, UISizeHeight * 0.85f);
		selectedEntityDamage.setPosition(UISizeWidth * 0.20f,
				UISizeHeight * 0.875f);
		selectedEntityRange.setPosition(UISizeWidth * 0.20f,
				UISizeHeight * 0.90f);
		selectedEntityAttackSpeed.setPosition(UISizeWidth * 0.20f,
				UISizeHeight * 0.925f);
		progressPourcentageUnit.setPosition(UISizeWidth * 0.20f,
				UISizeHeight * 0.59f);
		labelRockRessource.setPosition(UISizeWidth * 0.50f,
				UISizeHeight * 0.055f);
		labelTreeRessource.setPosition(UISizeWidth * 0.50f,
				UISizeHeight * 0.11f);

	}
	
	private Texture getPortrait(Trainee trainee){

		switch (trainee) {
		case FOOTMAN:
			return footman;
		case WORKER:
			return worker;
		default:
			return rockIconTexture;
	}
	}

	private void drawGUI(RenderWindow window, Game game) throws IOException {

		if (!game.getAllSelected().isEmpty()) {
			buildingImageButtons.clear();
			selectedEntityIcon.setFillColor(Color.WHITE);
			for (RectangleShape rect : buildingTabRectangle) {
				rect.setFillColor(Color.WHITE);
			}
			for (RectangleShape queueRect : trainingQueueRectangle){
				queueRect.setFillColor(Color.TRANSPARENT);
				queueRect.setTexture(null);
			}
			
			switch (game.getAllSelected().get(0).getName()) {
			case "Footman":
				FootMan entityFootman = (FootMan) game.getAllSelected().get(0);
				selectedEntityName.setString(entityFootman.getName());
				selectedEntityDamage.setString("Damage : " + (entityFootman.getDamage()));
				selectedEntityHP.setString("Health : " + (entityFootman.getHP()) + " / " + entityFootman.getMaxHealth());
				selectedEntityRange.setString("Range : " + (entityFootman.getRange()));
				selectedEntityAttackSpeed.setString("APS : " + (1 / (entityFootman.getAttackDelay())));
				progressPourcentageUnit.setString("");
				for (RectangleShape rect : buildingTabRectangle) {
					rect.setFillColor(Color.TRANSPARENT);
				}
				if (!selectedEntityIcon.equals(footman)) {
					selectedEntityIcon.setTexture(footman);
				}

				break;
			case "Worker":
				selectedEntityName.setString("Worker");
				Worker entityWorker = (Worker) game.getAllSelected().get(0);
				selectedEntityDamage.setString("");
				selectedEntityHP.setString("Health : " + (entityWorker.getHP()) + " / " + entityWorker.getMaxHealth());
				selectedEntityRange.setString("");
				selectedEntityAttackSpeed.setString("");
				progressPourcentageUnit.setString("");
				buildingImageButtons.add(towncenter);
				buildingImageButtons.add(barrack);
				buildingImageButtons.add(forge);
				buildingImageButtons.add(watchtower);
				for (int i = 0; i <= 3; i++) {
					buildingTabRectangle[i].setTexture(buildingImageButtons.remove(0));
				}
				buildingTabRectangle[4].setFillColor(Color.TRANSPARENT);
				buildingTabRectangle[5].setFillColor(Color.TRANSPARENT);
				if (!selectedEntityIcon.equals(entityWorker.getHP())) {
					// selectedUnitIcon.setTexture(worker);
				}
				break;
			case "Town Center":
				TownCenter entityTownCenter = (TownCenter) game.getAllSelected().get(0);
				selectedEntityName.setString(entityTownCenter.getName());
				selectedEntityDamage.setString("");
				selectedEntityRange.setString("");
				selectedEntityAttackSpeed.setString("");
				selectedEntityHP.setString("Health : " + (entityTownCenter.getHP()) + " / " + entityTownCenter.getMaxHealth());
				buildingImageButtons.add(worker);
				buildingTabRectangle[0].setTexture(buildingImageButtons.get(0));
				Factory factoryQueue = (Factory)game.getAllSelected().get(0);
				if(!factoryQueue.getQueue().isEmpty()){
					for(int i = 0; i < factoryQueue.getQueue().size(); i++){
						trainingQueueRectangle[i].setTexture(getPortrait(factoryQueue.getQueue().get(i)));
						trainingQueueRectangle[i].setFillColor(Color.WHITE);
					}
					progressPourcentageUnit.setString(Float.toString(((Training)entityTownCenter.getStateStack().peek()).getPourcentageDone()));
				}
				for(int i = 1; i < 6; i++){
					buildingTabRectangle[i].setFillColor(Color.TRANSPARENT);
				}
				if (!selectedEntityIcon.equals(towncenter)) {
					selectedEntityIcon.setTexture(towncenter);
				}
				break;
			case "Barrack":
				Barrack entityBarrack = (Barrack) game.getAllSelected().get(0);
				selectedEntityName.setString(entityBarrack.getName());
				selectedEntityDamage.setString("");
				selectedEntityRange.setString("");
				selectedEntityAttackSpeed.setString("");
				selectedEntityHP.setString("Health : " + (entityBarrack.getHP()) + " / " + entityBarrack.getMaxHealth());
				buildingImageButtons.add(footman);
				buildingTabRectangle[0].setTexture(buildingImageButtons.get(0));
				for(int i = 1; i < 6; i++){
					buildingTabRectangle[i].setFillColor(Color.TRANSPARENT);
				}
				Factory factoryQueue2 = (Factory)game.getAllSelected().get(0);
				if(!factoryQueue2.getQueue().isEmpty()){
					for(int i = 0; i < factoryQueue2.getQueue().size(); i++){
						trainingQueueRectangle[i].setTexture(getPortrait(factoryQueue2.getQueue().get(i)));
						trainingQueueRectangle[i].setFillColor(Color.WHITE);
					}
					for (int i = factoryQueue2.getQueue().size(); i < 5; i++){
						trainingQueueRectangle[i].setTexture(null);
					}
					progressPourcentageUnit.setString(Float.toString(((Training)entityBarrack.getStateStack().peek()).getPourcentageDone()));
				}
				if (!selectedEntityIcon.equals(barrack)) {
					selectedEntityIcon.setTexture(barrack);
				}
				break;
			case "WatchTower":
				WatchTower entityWatchTower = (WatchTower) game.getAllSelected().get(0);
				selectedEntityName.setString(entityWatchTower.getName());
				selectedEntityDamage.setString("Damage : " + (entityWatchTower.getDamage()));
				selectedEntityRange.setString("Range : " + (entityWatchTower.getRange()));
				selectedEntityAttackSpeed.setString("APS : " + (1 / (entityWatchTower.getAttackDelay())));
				progressPourcentageUnit.setString("");
				for (RectangleShape rect : buildingTabRectangle) {
					rect.setFillColor(Color.TRANSPARENT);
				}
				selectedEntityHP.setString("Health : " + (entityWatchTower.getHP()) + " / " + entityWatchTower.getMaxHealth());
				if (!selectedEntityIcon.equals(watchtower)) {
					selectedEntityIcon.setTexture(watchtower);
				}
				break;
			case "Forge":
				selectedEntityDamage.setString("");
				selectedEntityRange.setString("");
				selectedEntityAttackSpeed.setString("");
				progressPourcentageUnit.setString("");
				for (RectangleShape rect : buildingTabRectangle) {
					rect.setFillColor(Color.TRANSPARENT);
				}
				break;
			case "Tree":
				Tree entityTree = (Tree) game.getAllSelected().get(0);
				selectedEntityName.setString(entityTree.getName());
				selectedEntityHP.setString("Ressources : " + (entityTree.getRessources()));
				selectedEntityDamage.setString("");
				selectedEntityRange.setString("");
				selectedEntityAttackSpeed.setString("");
				progressPourcentageUnit.setString("");
				for (RectangleShape rect : buildingTabRectangle) {
					rect.setFillColor(Color.TRANSPARENT);
				}
				if (!selectedEntityIcon.equals(treeSprite)) {
					selectedEntityIcon.setTexture(treeSprite);
				}
				break;
			case "Stone":
				Stone entityRock = (Stone) game.getAllSelected().get(0);
				selectedEntityName.setString(entityRock.getName());
				selectedEntityHP.setString("Ressources : " + (entityRock.getRessources()));
				selectedEntityRange.setString("");
				selectedEntityDamage.setString("");
				selectedEntityAttackSpeed.setString("");
				for (RectangleShape rect : buildingTabRectangle) {
					rect.setFillColor(Color.TRANSPARENT);
				}
				if (!selectedEntityIcon.equals(rockIconTexture)) {
					selectedEntityIcon.setTexture(rockIconTexture);
				}
			case "Fondation":
				//TODO
				break;
			default:
				break;
			}
		} else {
			selectedEntityIcon.setFillColor(Color.TRANSPARENT);
			for (RectangleShape rect : buildingTabRectangle) {
				rect.setFillColor(Color.TRANSPARENT);
			}
			for (RectangleShape queueRect : trainingQueueRectangle){
				queueRect.setFillColor(Color.TRANSPARENT);
				queueRect.setTexture(null);
			}
			selectedEntityAttackSpeed.setString("");
			progressPourcentageUnit.setString("");
			selectedEntityDamage.setString("");
			selectedEntityHP.setString("");
			selectedEntityName.setString("");
			selectedEntityRange.setString("");
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
		window.draw(selectedEntityRange);
		window.draw(selectedEntityAttackSpeed);
		window.draw(labelTreeRessource);
		window.draw(labelRockRessource);
		window.draw(progressPourcentageUnit);
		for (int i = 0; i <= 5; i++) {
			window.draw(buildingTabRectangle[i]);
		}
		for (int i = 0; i <= 4; i++) {
			window.draw(trainingQueueRectangle[i]);
		}
	}

	public MusicPlayer getMusic() {
		return music;
	}
}
