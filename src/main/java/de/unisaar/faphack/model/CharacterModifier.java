package de.unisaar.faphack.model;

public class CharacterModifier implements Storable {
  // what this modifier does to the various aspects of a character
  public int health;
  public int magic;
  public int power;

  private int howLong;

  public CharacterModifier(){}

  public CharacterModifier(int h, int m, int p, int hl) {
    health = h;
    magic = m;
    power = p;
    howLong = hl;
  }

  /**
   * Apply the changes of this modifier to c, but only if howLong is not zero
   */
  public boolean applyTo(Character c) {
    // TODO please implement me!
    if (this.howLong() == 0) { return false; }
    c.health += this.health;
    c.magic += this.magic;
    c.power += this.power;
    return true;
  }

  public int howLong() {
    return howLong;
  }

  @Override
  public void marshal(MarshallingContext c) {
    // TODO please implement me!
    c.write("CharacterModifier", this);
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    // TODO please implement me!
    c.read("CharacterModifier");
  }
}
