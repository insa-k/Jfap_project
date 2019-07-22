package de.unisaar.faphack.model.map;

import de.unisaar.faphack.model.Character;
import de.unisaar.faphack.model.MarshallingContext;

/**
 * @author
 *
 */
public class StairTile extends Tile {
  protected Stair stair;

  protected Trap trap;

  public StairTile() {
    trait = STAIR;
  }

  public StairTile(int x, int y, Room room){
    super(x, y, room);
    trait = STAIR;
  }

  /**
   * A stair can (possibly) be used in both directions: it depends on where you
   * are currently.
   *
   * Remember to update the level of the character.
   *
   * @return the new tile, or null if not possible to use
   */
  @Override
  public Tile willTake(Character c) {
    // TODO: FILL THIS
    return null;
  }

  /** Return non-null if this is a trap */
  @Override
  public Trap hasTrap() {
    // TODO: FILL THIS
    return null;
  }

  @Override
  public void marshal(MarshallingContext c) {
    // TODO: FILL THIS
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    // TODO: FILL THIS
  }

  public Stair getStair(){
    return stair;
  }
}
