package ca.csf.RTS.Menu.controller;

import ca.csf.RTS.eventHandler.MenuEventHandler;
import ca.csf.RTS.game.GameController;
import ca.csf.simpleFx.SimpleFXController;
import javafx.fxml.FXML;

public class MenuController extends SimpleFXController implements MenuEventHandler{

	private GameController gameController;
	
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
	public void options() {

	}

}
