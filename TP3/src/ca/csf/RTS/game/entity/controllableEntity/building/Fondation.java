package ca.csf.RTS.game.entity.controllableEntity.building;

import org.jsfml.system.Vector2i;

import ca.csf.RTS.game.entity.Entity;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.state.Idle;
import ca.csf.RTS.game.entity.state.State;

public class Fondation extends Building {

	private Building building;

	public Fondation(Building building) {
		super(building.getTilesOrigin(), building.getTeam(), building.getGame(), building.getDimention(), building.getTeam().getWatchTowerModel()
				.getHealthMax());
		this.building = building;
		
		sprite = building.getSprite();
		setSpritePos();
	}

	public void transform() {
		game.remove(this);
		game.add(building);
	}

	@Override
	public Vector2i getCenter() {
		return building.getCenter();
	}

	@Override
	public void order(Entity onTile) {
	}

	@Override
	public void order(Tile target) {
	}

	@Override
	public void doTasks(float deltaTime) {
		if (!stateStack.isEmpty()) {

			switch (stateStack.peek().action(deltaTime)) {

			case notFinished:
			case noTargetSighted:
				break;

			case ended:
				stateStack.pop();

				if (stateStack.isEmpty()) {
					stateStack.push(getDefaultState());
				}
				break;

			case dead:
				game.remove(building);
				game.remove(this);
				break;

			default:
				break;
			}
		}
	}

	@Override
	public String getName() {
		return building.getName();
	}

	@Override
	protected State getDefaultState() {
		return new Idle();
	}

	@Override
	public void loseLife(int damage) {
		super.loseLife(damage);
		building.loseLife(damage);
	}
}
