package ca.csf.RTS.game.entity;

import ca.csf.RTS.eventHandler.GameEventHandler;
import ca.csf.RTS.game.Team;

public abstract class Entity extends GameObject {

	protected Tile tilesOrigin;
	protected int height;
	protected int width;
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
		
		//TODO: figure out what this did
//		Vector2i mostTopLeftTile = new Vector2i(Game.MAP_SIZE, Game.MAP_SIZE);
//		for (Tile tile : tilesOrigin) {
//			if (tile.getMapLocation().x < mostTopLeftTile.x	&& tile.getMapLocation().y < mostTopLeftTile.y) {
//				mostTopLeftTile = tile.getMapLocation();
//			}
//		}
		
		sprite.setPosition(tilesOrigin.getMapLocation().x * Tile.TILE_SIZE, tilesOrigin.getMapLocation().y * Tile.TILE_SIZE);
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
