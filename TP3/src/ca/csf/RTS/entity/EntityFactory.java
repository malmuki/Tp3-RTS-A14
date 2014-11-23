package ca.csf.RTS.entity;

import ca.csf.RTS.entity.concrete.Soldat;

public class EntityFactory {

	private EntityFactory() {
	}

	private static class Holder {
		private static final EntityFactory instance = new EntityFactory();
	}

	public static EntityFactory getInstance() {
		return Holder.instance;
	}

	public Entity getEntity(E_Entity entity) {
		switch (entity) {
		case SOLDAT:
			return new Soldat();
		default:
			return null;
		}
	}

}
