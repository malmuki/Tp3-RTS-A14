package ca.csf.RTS.Menu.model;

import ca.csf.RTS.Menu.controller.MenuController;
import ca.csf.simpleFx.SimpleFXApplication;
import ca.csf.simpleFx.SimpleFXApplicationLauncher;
import ca.csf.simpleFx.SimpleFXScene;
import ca.csf.simpleFx.SimpleFXStage;
import javafx.stage.StageStyle;

public class Menu extends SimpleFXApplication {
	
	public void run(String[] args){
		SimpleFXApplicationLauncher.startSimpleFXApplication(Menu.class, args);
	}

	@Override
	public void start() {
		try {
			SimpleFXScene simpleFXScene = new SimpleFXScene(MenuController.class.getResource("MenuWindow.fxml"),
					MenuController.class.getResource("menu.css"), new MenuController());
			SimpleFXStage simpleFXStage = new SimpleFXStage("My Application", StageStyle.DECORATED, simpleFXScene, this);
			simpleFXStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
