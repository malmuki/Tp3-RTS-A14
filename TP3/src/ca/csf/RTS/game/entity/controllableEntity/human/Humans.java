package ca.csf.RTS.game.entity.controllableEntity.human;

import java.util.ArrayList;

import ca.csf.RTS.game.entity.OnTileEntity;
import ca.csf.RTS.game.entity.Tile;
import ca.csf.RTS.game.entity.controllableEntity.ControlableEntity;

public abstract class Humans extends ControlableEntity {
	
	public Humans(ArrayList<Tile> tiles,String name, int maxHealth) {
		super( tiles, name, maxHealth );
	}
	
	public void move(Tile destination) {
	  tile.clear();
	  tile.add(destination);
	}
	
	@Override
	public void order(Tile target) {
	  move(target);
	}
	
	@Override
	public void order(OnTileEntity target) {
      
    }
}