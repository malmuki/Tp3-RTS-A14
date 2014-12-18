package ca.csf.RTS.game;

import org.jsfml.graphics.Color;

public class Team {
	private int wood = 150;
	private int stone = 150;
	private String name;
	private Color color;
	
	public Team(String name, Color color) {
		this.name = name;
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

	public Color getColor() {
		return color;
	}
}