package ca.csf.RTS.Menu.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import ca.csf.RTS.eventHandler.MenuEventHandler;
import ca.csf.RTS.game.GameController;
import ca.csf.simpleFx.SimpleFXController;

public class MenuController extends SimpleFXController implements MenuEventHandler {
	@FXML
	private Button btnOptions;

	@FXML
	public Slider volumeMusicSlider;
	private GameController gameController;

	public MenuController() {
		gameController = new GameController();
	}

	@FXML
	public void startGame() throws IOException {
		getSimpleFxStage().close();
		// Create the window
		gameController.newGame();

	}

	@FXML
	public void optionsClick() {

	}

	@FXML
	private void dragRelease() {
	}

}
