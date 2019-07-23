package de.unisaar.faphack.model.map;

import de.unisaar.faphack.model.MarshallingContext;

/**
 * An obstacle tile could have different traits, so we have to store it
 */
public class ObstacleTile extends WallTile {

  /** default trait: boulder */
  public ObstacleTile() {
    trait = BOULDER;
  }

  @Override
  public void marshal(MarshallingContext c) {
    // TODO please implement me!
    super.marshal(c);
    c.write("ObstacleTile", this);
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    // TODO please implement me!
    super.unmarshal(c);
    c.read("ObstacleTile");
  }
}
