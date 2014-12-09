package ca.csf.RTS.Menu.controller;

import ca.csf.RTS.eventHandler.MenuEventHandler;
import ca.csf.RTS.game.GameController;
import ca.csf.simpleFx.SimpleFXController;
import ca.csf.simpleFx.SimpleFXScene;
import ca.csf.simpleFx.SimpleFXStage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.stage.StageStyle;

public class MenuController extends SimpleFXController implements MenuEventHandler{
	@FXML
	private Button btnOptions;
	
	@FXML
	public Slider volumeMusicSlider;
	private GameController gameController;
	
	private static final String OPTIONSWINDOW_PATH = "../vue/OptionsWindow.fxml";
	private static final String CSS_OPTIONS_PATH = "../vue/options.css";
	private static final String TITLE = "Options";
	public MenuController() {
		gameController = new GameController();
	}

	@FXML
	public void startGame() {
		getSimpleFxStage().close();
		// Create the window
			gameController.newGame();
		
	}

	@FXML
	public void optionsClick() {
		try {
			SimpleFXScene simpleFXScene = new SimpleFXScene(MenuController.class.getResource(OPTIONSWINDOW_PATH),
					MenuController.class.getResource(CSS_OPTIONS_PATH), new MenuController());
			SimpleFXStage simpleFXStage = new SimpleFXStage(TITLE, StageStyle.DECORATED, simpleFXScene, getSimpleFXApplication());
			simpleFXStage.setResizable(false);
			simpleFXStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@FXML
	private void dragRelease() {
		gameController.getMusic().setVolume(((int) volumeMusicSlider.getValue()));
	}

}
