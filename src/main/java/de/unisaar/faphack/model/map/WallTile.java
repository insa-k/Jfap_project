package de.unisaar.faphack.model.map;

import de.unisaar.faphack.model.Character;

/**
 * Walltiles are used to determine the arrangement of a room. They usually
 * define the outer borders of a room, but might also be used within a room to
 * separate areas.
 * __________________
 * |                |
 * |                |
 * |   _______      |
 * |  |_____  |     D
 * |________________|
 *
 * @author
 *
 */
public class WallTile extends Tile {
  /** 0 means infinitely strong, > 0 means: must apply at least this force */
  protected int destructible;

  public WallTile() {

  }

  @Override
  public Tile willTake(Character c) {
    return null;
  }

}
