package de.unisaar.faphack.model;

import java.util.List;

import de.unisaar.faphack.model.effects.MoveEffect;
import de.unisaar.faphack.model.map.Tile;
import de.unisaar.faphack.model.map.World;

/**
 * @author
 *
 */
public class Game {
  private World world;

  public Game() {

  }

  /**
   * @param whom
   * @param destination
   * @return boolean
   */
  public boolean move(Character whom, Direction direction) {
	// Get current character tile
    Tile current_position = whom.getTile();
    
    // Get destination tile
    Tile temp_destination = current_position.getNextTile(direction);
    // Movement is not possible
    if (temp_destination == null) { return false; }
    // check whether movement is ok
    Tile destination = temp_destination.willTake(whom);
    // Movement possible
    if (destination == null) {return false; }
    whom.move(destination);
    return true;
  }

  /**
   *
   * @param who
   * @param direction
   * @return List<Item>
   */
  public List<Item> listItems(Character who, Direction direction) {
    return null;
  }

  /**
   * @param who
   * @param item
   * @return boolean
   */
  public boolean pickUp(Character who, Item item) {
    return false;
  }

}
