package ca.csf.RTS.entity;

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
		return null;
		// TODO
	}

}
