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

  }

  Room(Tile[][] tiles){
    this.tiles = tiles;
  }

  public Tile getNextTile(Tile t, Direction d) {
    return null;
  }

}
