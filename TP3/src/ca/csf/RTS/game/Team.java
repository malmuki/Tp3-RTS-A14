package ca.csf.RTS.game;

import org.jsfml.graphics.Color;
import ca.csf.RTS.game.unitsModel.BarrackModel;
import ca.csf.RTS.game.unitsModel.FootManModel;
import ca.csf.RTS.game.unitsModel.ForgeModel;
import ca.csf.RTS.game.unitsModel.TownCenterModel;
import ca.csf.RTS.game.unitsModel.WatchTowerModel;
import ca.csf.RTS.game.unitsModel.WorkerModel;

public class Team {
	private int wood = 150;
	private int stone = 150;
	private String name;
	private Color color;

	// Units models, to be changed through upgrades
	private FootManModel footManModel;
	private WorkerModel workerModel;
	private TownCenterModel townCenterModel;
	private ForgeModel forgeModel;
	private BarrackModel barrackModel;
	private WatchTowerModel watchTowerModel;

	public Team(String name, Color color) {
		this.name = name;
		this.color = color;
		footManModel = new FootManModel();
		workerModel = new WorkerModel();
		townCenterModel = new TownCenterModel();
		forgeModel = new ForgeModel();
		barrackModel = new BarrackModel();
		watchTowerModel = new WatchTowerModel();
	}

	public WatchTowerModel getWatchTowerModel() {
		return watchTowerModel;
	}

	public TownCenterModel getTownCenterModel() {
		return townCenterModel;
	}

	public BarrackModel getBarrackModel() {
		return barrackModel;
	}

	public ForgeModel getForgeModel() {
		return forgeModel;
	}

	public FootManModel getFootManModel() {
		return footManModel;
	}

	public WorkerModel getWorkerModel() {
		return workerModel;
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
		} else {
			return false;
		}
	}

	public boolean substractStone(int amountLost) {
		if (amountLost <= stone) {
			stone -= amountLost;
			return true;
		} else {
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