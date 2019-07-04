package de.unisaar.faphack.model;

/**
 * @author
 *
 */
public class DoorTile extends WallTile {
	private boolean locked = false;
	private Hallway hallway;
	/**
	 * To be opened by an item (key) the Effect of that item needs to create a
	 * matching ID.
	 */
	private int keyId;

	public DoorTile() {

	}

}
