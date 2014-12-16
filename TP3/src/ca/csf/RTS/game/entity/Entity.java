package ca.csf.RTS.game.entity;

import org.jsfml.system.Vector2i;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;

public abstract class Entity extends GameObject {

	protected Tile tilesOrigin;
	protected Vector2i dimentions;
	protected boolean selected;
	protected Team team;

	public Entity(Tile tiles, Team team, GameEventHandler game) {
		super(game);
		selected = false;
		this.tilesOrigin = tiles;
		this.team = team;
		sprite.setTexture(texture);
		setSpritePos();
		team.addUnit(this);
	}

	public Tile getTilesOrigin() {
		return tilesOrigin;
	}

	@Override
	protected void setSpritePos() {
		//if the entity take 1 tile but the sprite is bigger that the tile
		if (sprite.getTexture().getSize().y > Tile.TILE_SIZE) {
			sprite.setPosition(getTilesOrigin().getMapLocation().x * Tile.TILE_SIZE,
					getTilesOrigin().getMapLocation().y * Tile.TILE_SIZE
							- sprite.getTexture().getSize().y + Tile.TILE_SIZE);
		} else {
			sprite.setPosition(tilesOrigin.getMapLocation().x * Tile.TILE_SIZE,
					tilesOrigin.getMapLocation().y * Tile.TILE_SIZE);
		}
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
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
