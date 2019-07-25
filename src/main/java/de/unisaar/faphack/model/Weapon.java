package de.unisaar.faphack.model;

import de.unisaar.faphack.model.map.Room;
import de.unisaar.faphack.model.map.Tile;

import java.util.List;

public class Weapon extends Wearable {

  // attack range of weapon
  protected int range;

  protected boolean isMagical;


  public Weapon(int range, boolean isMagical) {
    this.range = range;
    this.isMagical = isMagical;
  }

  public boolean isMagical() {
    return isMagical;
  }

  public int getRange() {
    return range;
  }

  /**
   * checks if attack by character in a given direction is in range, according to the active weapon
   * @param d direction of the attack
   * @return <code>true</code> if attack is in range, <code>false</code> otherwise
   */
  public boolean attackIsInRange(Direction d) {
    // get current tile of character
    Tile currentTile = character.getTile();
    Room currentRoom = character.getRoom();
    // get nextTile in direction
    Tile nextTile = currentRoom.getNextTile(currentTile, d);
    List<Tile> path = character.getRoom().getPath(currentTile, d);
    // check if length of reachable path is less or equal to range of weapon
    int pathLength = 0;
    for (Tile t : path.subList(1, path.size())) {
      pathLength++;
      // check if tile in path is nextTile (aka destination tile)
      if (nextTile == t && pathLength <= range) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void marshal(MarshallingContext c) {
    super.marshal(c);
    c.write("range", range);
    c.write("isMagical", isMagical? 1 : 0);
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    super.unmarshal(c);
    weight = c.readInt("range");
    isWeapon = (c.readInt("isMagical") == 1);
  }

}
