package de.unisaar.faphack.model.map;

import de.unisaar.faphack.model.Character;
import de.unisaar.faphack.model.Item;
import de.unisaar.faphack.model.MarshallingContext;
import de.unisaar.faphack.model.Wearable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 *
 */
public class FloorTile extends Tile {
  /**
   * The items placed on this tile.
   */
  protected List<Item> items = new ArrayList<>();

  public FloorTile() {
    trait = FLOOR;
  }

  public FloorTile(int x, int y, Room room){
    super(x, y, room);
    trait = FLOOR;
  }

  /**
   *
   * @param c the character that wants to enter this tile
   * @return  returns <code>this</code> tile if
   *            1. the class is not already occupied by an character or
   *            2. is occupied by the character itself
   *           else null
   *
   */
  @Override
  public Tile willTake(Character c) {
    if (isOccupied(c)) {
      return null;
    }
    return this;
  }

  /** FloorTiles can have items on them */
  public List<Item> onTile() {
    return items;
  }

  @Override
  public boolean removeItem(Wearable what) {
    return items.remove(what);
  }

  @Override
  public boolean addItem(Wearable what) {
    return items.add(what);
  }

  @Override
  public void marshal(MarshallingContext c) {
    // TODO please implement me!
    super.marshal(c);
    c.write("items", items);
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    // TODO please implement me!
    super.unmarshal(c);
    items = new ArrayList<Item>();
    c.readAll("items", items);
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

}
