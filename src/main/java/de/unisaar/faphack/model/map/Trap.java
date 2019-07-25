package de.unisaar.faphack.model.map;

import de.unisaar.faphack.model.CharacterModifier;
import de.unisaar.faphack.model.Fixtures;
import de.unisaar.faphack.model.MarshallingContext;

/**
 * A Trap is a special Fixture. Its effect is triggered by moving on the tile it
 * is placed on.
 *
 * @author
 *
 */
public class Trap extends Fixtures {
  /**
   * Traps might also be placed on StairTiles. In this case, the stair is masked
   * by the Trap and thus not visible for the character, i.e. a trap door.
   *
   */
  protected StairTile trapDoor = null;

  protected CharacterModifier modifier;

  public Trap() {

  }

  public void marshal(MarshallingContext c) {
    super.marshal(c);
    c.write("trapDoor", trapDoor);
  }

  public void unmarshal(MarshallingContext c) {
    super.unmarshal(c);
    trapDoor = c.read("trapDoor");
  }
}
