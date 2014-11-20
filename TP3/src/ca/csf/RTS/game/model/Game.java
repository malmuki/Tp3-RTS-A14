package ca.csf.RTS.game.model;

import java.util.ArrayList;

import ca.csf.RTS.eventHandler.GameEventHandler;

public class Game {
	
	private ArrayList<GameEventHandler> gameEventHandler;
	private Tile[][] map;
	
	public Game() {
		
	}

	public void addEventHandler(GameEventHandler handler){
		gameEventHandler.add(handler);
	}

	public void newGame() {
		
		
	}
	
}
