package de.unisaar.faphack.model;

import de.unisaar.faphack.model.map.Tile;

/**
 * Wearables are Items that can be carried by a Character. These include armor,
 * weapons, food, potions, key and others.
 *
 * @author
 *
 */
public class Wearable extends Item {
  /**
   * The weight of the item.
   */
  protected int weight;

  /**
   *
   */
  protected boolean isWeapon;

  /**
   * The character who carries this item. This is null if the Item is placed on a
   * Tile.
   */
  protected Character character;

  public Wearable() {

  }

  @Override
  public void marshal(MarshallingContext c) {
    // TODO please implement me!
    c.write("weight", weight);
    c.write("isWeapon", isWeapon ? 1 : 0);
    c.write("Character", character);
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    // TODO please implement me!
    super.unmarshal(c);
    this.weight = c.readInt("weight");
    this.isWeapon = (c.readInt("isWeapon") == 1);
  }

  public void pickUp(Character c) {
    // TODO please implement me!
    c.items.add(this);
    this.character = c;
  }

  public void drop(Tile t) {
    // TODO please implement me
    t.addItem(this);
    this.character = null;
  }
}
