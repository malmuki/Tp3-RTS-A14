package ca.csf.RTS.game;

import java.util.ArrayList;

import ca.csf.RTS.game.entity.Entity;

public class Team {
	private int wood = 0;
	private int stone = 0;
	private ArrayList<Entity> units;
	private String name;
	
	public Team(String name) {
		this.name = name;
		units = new ArrayList<Entity>();
	}
	
	public Team(){}
	
	public void addWood(int amountGained) {
		wood += amountGained;
	}
	
	public void addStone(int amountGained) {
		stone += amountGained;
	}
	
	public void substractWood(int amountLost) {
		wood -= amountLost;
	}
	
	public void substractStone(int amountLost) {
		stone -= amountLost;
	}
	
	public int getWood() {
		return wood;
	}
	
	public int getStone() {
		return stone;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Entity> getUnits() {
		return units;
	}
	
	public void addUnit(Entity newUnit) {
		units.add(newUnit);
	}
	
	public void removeUnit(Entity removedUnit) {
		units.remove(removedUnit);
	}
}