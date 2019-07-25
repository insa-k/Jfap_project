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
    // TODO please implement me! (done?)

    Stair stair = getStair();
    // check if stair is only oneWay
    if (stair.onlyDown()) {
      // so: check if current tile is the fromTile
      if (stair.from() == this) {
        // the character goes a level down
        c.levelDown();
        // then the character can only go fromTile -> toTile
        return stair.to();
      }
      // otherwise you can't go this way and it should return null
      return null;
    }
    // stair goes both ways
    if (stair.from() == this) {
      c.levelDown();
      return stair.to();
    }
    // current tile is "toTile"
    c.levelUp();
    return stair.from();

//    TODO: check how traps work
//    // check if trap is masked as stairs
//    if( !(hasTrap().equals(null))) {
//      StairTile trapDoor = hasTrap().trapDoor;

  }

  /** Return non-null if this is a trap */
  @Override
  public Trap hasTrap() {
    return trap;
  }

  @Override
  public void marshal(MarshallingContext c) {
    super.marshal(c);
    c.write("stair", stair);
    c.write("trap", trap);
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    super.unmarshal(c);
    stair = c.read("stair");
    trap = c.read("trap");
  }

  public Stair getStair(){
    return stair;
  }
}
