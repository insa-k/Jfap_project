package de.unisaar.faphack.model.effects;

import de.unisaar.faphack.model.CharacterModifier;

/**
 * Additive effects directly modify the health and magic of a character.
 *
 * @author
 *
 */
public class MultiplicativeEffect extends ModifyingEffect {

  public MultiplicativeEffect(){}

  public MultiplicativeEffect(double h, double m, double p, double hl) {
    health = h;
    magic = m;
    power = p;
  }

  /**
   * Modifies the values the given character.
   *
   * @param on
   * @return void
   */
  public CharacterModifier apply(CharacterModifier c) {
    // TODO: FILL THIS
    return null;
  }

}
