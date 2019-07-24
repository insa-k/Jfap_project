package de.unisaar.faphack.model.effects;

import de.unisaar.faphack.model.Direction;
import de.unisaar.faphack.model.Character;
import de.unisaar.faphack.model.map.Tile;

public class MoveEffect implements Effect<Character, Boolean> {
  private Direction dir;

  public MoveEffect(Direction d) {
    if(d.x > 1 || d.x < -1 || d.y > 1 || d.y < -1) {
      throw new IllegalArgumentException("IllegalArgumentException, MoveEffect only takes values -1,0,1 ");
    }
    dir = d;
  }

  /**
   * @param c the character to move
   * @return true if successful, false otherwise
   */
  public Boolean apply(Character c) {
    // TODO: FILL THIS
    // Get current character tile
    Tile current_position = c.getTile();

    // Get destination tile
    Tile temp_destination = current_position.getNextTile(dir);
    // Movement is not possible
    if (temp_destination == null) { return false; }
    // check whether movement is ok
    Tile destination = temp_destination.willTake(c);
    // Movement possible
    if (destination == null) {return false; }
    c.move(destination);
    return true;
  }

}
