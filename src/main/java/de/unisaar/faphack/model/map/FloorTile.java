package de.unisaar.faphack.model.map;

import java.util.List;

import de.unisaar.faphack.model.Character;
import de.unisaar.faphack.model.Item;

/**
 * @author
 *
 */
public class FloorTile extends Tile {

  public FloorTile() {

  }

  public FloorTile(int x, int y, Room room, List<Item> items) {
    super(x, y, room, items);
  }

  @Override
  public Tile willTake(Character c) {
    return this;
  }

}
