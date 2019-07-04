package de.unisaar.faphack.model;

/**
 * Walltiles are used to determine the arrangement of a room. They usually define
 * the outer borders of a room, but might also be used within a room to separate
 * areas. __________________ | | | | | _______ ___ | | | |_________________|
 * 
 * @author
 *
 */
public class WallTile extends Tile {
	protected boolean destructible;

	public WallTile() {

	}

}
