package ca.csf.RTS.Menu.model;

import java.util.ArrayList;

import ca.csf.RTS.Menu.controller.MenuController;
import ca.csf.RTS.eventHandler.MenuEventHandler;
import ca.csf.simpleFx.SimpleFXApplication;
import ca.csf.simpleFx.SimpleFXApplicationLauncher;
import ca.csf.simpleFx.SimpleFXScene;
import ca.csf.simpleFx.SimpleFXStage;
import javafx.stage.StageStyle;

public class Menu extends SimpleFXApplication {

	public static final String MENUWINDOW_PATH = "../vue/MenuWindow.fxml";
	public static final String CSS_PATH = "../vue/menu.css";
	public static final String TITLE = "RTS TP3 2014";

	private ArrayList<MenuEventHandler> menuEventHandler;
	
	public void run(String[] args) {
		SimpleFXApplicationLauncher.startSimpleFXApplication(Menu.class, args);
	}

	@Override
	public void start() {
		try {
			SimpleFXScene simpleFXScene = new SimpleFXScene(MenuController.class.getResource(MENUWINDOW_PATH),
					MenuController.class.getResource(CSS_PATH), new MenuController());
			SimpleFXStage simpleFXStage = new SimpleFXStage(TITLE, StageStyle.DECORATED, simpleFXScene, this);
			simpleFXStage.setResizable(false);
			simpleFXStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void addEvent(MenuEventHandler Handler){
		menuEventHandler.add(Handler);
	}
}
