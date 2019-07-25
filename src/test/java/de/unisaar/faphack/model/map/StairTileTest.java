package de.unisaar.faphack.model.map;

import de.unisaar.faphack.model.Character;
import de.unisaar.faphack.model.CharacterModifier;
import de.unisaar.faphack.model.TestUtils;
import org.junit.jupiter.api.Test;

import static de.unisaar.faphack.model.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class StairTileTest {

  @Test
  void willTake() {
    Character character = createBaseCharacter("Foo", 3,3);
    StairTile t1 = new StairTile();
    StairTile t2 = new StairTile();
    connectStairTiles(t1,t2, false);
    assertEquals(t2, t1.willTake(character));
    assertEquals(t1, t2.willTake(character));
    connectStairTiles(t1, t2, true);
    assertEquals(t2, t1.willTake(character));
    assertNull(t2.willTake(character));
  }

  @Test
  void enterTrap(){
    Room r1 = createSimpleRoom(8,8, 2);
    Room r2 = createSimpleRoom(8, 8, 2);
    StairTile st1 = (StairTile) r1.getTiles()[4][4];
    StairTile st2 = (StairTile) r2.getTiles()[4][4];
    connectStairTiles(st1, st2, true);

    Trap trap = new Trap();
    placeTrapOnTile(trap, st1);
    trap.trapDoor = st1;
    st1.trap = trap;
    CharacterModifier characterModifier = TestUtils.createCharacterModifier(-10, -15, -1, 1);

    trap.setEffect(characterModifier);

    Character character = createBaseCharacter("Foo", 3,3);
    addCharacter(r1, 4,4,character);
    trap.applyTrapeffect(character);

    trap.applyTrapeffect(character);
    assertEquals(90, character.getHealth());
    assertEquals(st2, st1.willTake(character));
    assertNull(st2.willTake(character));



  }
}