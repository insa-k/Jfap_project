package de.unisaar.faphack.model.map;

import de.unisaar.faphack.model.Character;
import de.unisaar.faphack.model.MarshallingContext;

import java.util.List;

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
  /** 0 means infinitely strong, > 0 means: must apply at least this force, -1 means the wall is destroyed */
  protected int destructible;

  public WallTile() {
    trait = WALL;
  }

  public WallTile(int x, int y, Room room){
    super(x, y, room);
    trait = WALL;
  }

  public int getDestructible() {
    return destructible;
  }

  @Override
  public Tile willTake(Character c) {
    // TODO please implement me! (done)
    if ( destructible == -1 || (destructible > 0 && c.getPower() >= destructible)) {
      // destroyed wall --> destructible = -1
      destructible = -1;
      return this;
    }
    return null;
  }

  @Override
  public void marshal(MarshallingContext c) {
    // TODO please implement me!
    super.marshal(c);
    c.write("destructible", destructible);
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    super.unmarshal(c);
    destructible = c.readInt("destructible");
  }

  /**
   *
   * @return true if the tile is occupied by a character
   */
  @Override
  public boolean isOccupied(Character currentCharacter){
    List<Character> inhabitants = room.getInhabitants();
    for (Character c : inhabitants) {
      Tile tile = c.getTile();
      // if current character is on tile
      if (this == tile && !(c == currentCharacter)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String getTrait() { return destructible < 0 ? DESTROYED_WALL : WALL; }

  @Override
  public Character characterOnTile() {
    List<Character> inhabitants = room.getInhabitants();
    for (Character c : inhabitants) {
      Tile tile = c.getTile();
      if (this == tile) {
        return c;
      }
    }
    // no character on tile
    return null;
  }

}
