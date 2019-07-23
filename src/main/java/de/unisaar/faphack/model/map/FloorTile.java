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

  @Override
  public Tile willTake(Character c) {
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
  public void marshal(MarshallingContext c) {
    // TODO please implement me!
    super.marshal(c);
    c.write("FloorTile", this);
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    // TODO please implement me!
    super.unmarshal(c);
    c.read("FloorTile");
  }

}
