package ca.csf.RTS.Menu;

import ca.csf.RTS.Game.Screen;
import ca.csf.simpleFx.SimpleFXController;
import javafx.fxml.FXML;

public class MenuController extends SimpleFXController {

	private Screen screen;
	
	public MenuController() {
		screen = new Screen();
	}
	
	@FXML
	public void startGame() {
		screen.execute();
	}

	@FXML
	public void options() {
		
	}

}
