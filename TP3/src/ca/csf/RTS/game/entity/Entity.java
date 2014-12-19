package ca.csf.RTS.game.entity;

import org.jsfml.system.Vector2i;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;

public abstract class Entity extends GameObject {

	protected Tile tilesOrigin;
	protected Vector2i dimensions;
	protected boolean selected;
	protected Team team;

	public Entity(Tile tiles, Team team, GameEventHandler game) {
		super(game);
		selected = false;
		this.tilesOrigin = tiles;
		this.team = team;
	}

	public Tile getTilesOrigin() {
		return tilesOrigin;
	}

	@Override
	protected void setSpritePos() {
		sprite.setPosition(tilesOrigin.getMapLocation().x * Tile.TILE_SIZE, tilesOrigin.getMapLocation().y * Tile.TILE_SIZE);
	}

	public Vector2i getDimention() {
		if (dimensions != null) {
			return dimensions;
		} else {
			return new Vector2i(1, 1);
		}

	}

	public void select() {
		selected = true;
	}

	public void deSelect() {
		selected = false;
	}

	public abstract void order(Entity onTile);

	public abstract void order(Tile target);

	public abstract void doTasks(float deltaTime);

	public Team getTeam() {
		return team;
	}

	public abstract boolean isObstacle();
}
