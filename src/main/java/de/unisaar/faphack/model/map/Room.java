package de.unisaar.faphack.model.map;

import java.util.List;

import de.unisaar.faphack.model.Direction;

/**
 * @author
 *
 */
public class Room {

  /** The world this room belongs to */
  private World w;

  /** The Characters that currently are in this room */
  private List<Character> inhabitants;

  /**
   * A 2-dimensional Array defining the layout of the tiles in the room.
   */
  private Tile[][] tiles;

  public Room() {
    // TODO: set World and Tile Array
  }

  public Room(Tile[][] tiles){
    this.tiles = tiles;
  }

  public Room(List<Character> inhabitants, Tile[][] tiles){
    this.tiles = tiles;
    this.inhabitants = inhabitants;
  }

  public Tile getNextTile(Tile t, Direction d) {
    int new_x = t.x + d.x;
    int new_y = t.y + d.y;
    // move only one tile, allows diagonal movement
    int x_movement = t.x - new_x;
    int y_movement = t.y - new_y;
    // possible differences: -1, 1, 0 for x and y movement
    // if movement is possible return new tile else return null
    if(x_movement <= 1 && x_movement >= -1 && y_movement <= 1 && y_movement >= -1) {
      // check if new tile is still in the room (array)
      try { return this.tiles[new_x][new_y]; }
      catch (java.lang.ArrayIndexOutOfBoundsException e) { return null ; }
    }
    else { return null; }
  }

}
