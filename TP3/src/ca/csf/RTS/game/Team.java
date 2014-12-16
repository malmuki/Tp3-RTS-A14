package ca.csf.RTS.game;

import java.util.ArrayList;

import org.jsfml.graphics.Color;

import ca.csf.RTS.game.entity.Entity;

public class Team {
	private int wood = 0;
	private int stone = 0;
	private ArrayList<Entity> units;
	private String name;
	private Color color;
	
	public Team(String name, Color color) {
		this.name = name;
		units = new ArrayList<Entity>();
		this.color = color;
	}
	
	public void addWood(int amountGained) {
		wood += amountGained;
	}
	
	public void addStone(int amountGained) {
		stone += amountGained;
	}
	
	public boolean substractWood(int amountLost) {
		if (amountLost <= wood) {
			wood -= amountLost;
			return true;
		}else {
			return false;
		}
	}
	
	public boolean substractStone(int amountLost) {
		if (amountLost <= stone) {
			stone -= amountLost;
			return true;
		}else {
			return false;
		}
	}
	
	public int getWood() {
		return wood;
	}
	
	public int getStoned() {
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

	public Color getColor() {
		return color;
	}
}