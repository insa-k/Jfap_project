package de.unisaar.faphack.model.map;

import de.unisaar.faphack.model.Direction;

/**
 * @author
 *
 */
public class Room {

  /** The world this room belongs to */
  private World w;

  /**
   * A 2-dimensional Array defining the layout of the tiles in the room.
   */
  private Tile[][] tiles;

  public Room() {
	  // TODO: set World and Tile Array
  }

  Room(Tile[][] tiles){
    this.tiles = tiles;
  }

  public Tile getNextTile(Tile t, Direction d) {
    int new_x = t.x + d.x;
    int new_y = t.y + d.y;
    // TODO: what if you're now outside the room
    return this.tiles[new_x][new_y];
  }

}
