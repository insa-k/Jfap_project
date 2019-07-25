package de.unisaar.faphack.model;

import static de.unisaar.faphack.model.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

import de.unisaar.faphack.dirtyhacks.StorableRegistrator;
import de.unisaar.faphack.model.map.Room;

public class CreateTestJsons {
  
  void genericSave(Storable storable, String filename) {
    File f = getTestResourceFile("", filename);
    StorableFactory fact = new StorableFactory();
    StorableRegistrator.registerStorables(fact);
    MarshallingContext mc = new JsonMarshallingContext(f, fact);
    mc.save(storable);
    assertTrue(f.canRead());
  }

  @Test
  void saveSimpleRoom() {
    Room room1 = createSimpleRoom(3,2,  1);
    genericSave(room1, "room_1.json");
  }

  @Test
  void saveRoomWithInhabitants (){
    Room room = createSimpleRoom(2,3,  2);
    
    Character c1 = createBaseCharacter("John", 10, 5);
    Character c2 = createBaseCharacter("Mary", 15, 3);
    addCharacter(room, 1, 0, c1);
    addCharacter(room, 0, 1, c2);
    // addCharacter currently can only add one character to a room, test again, when this is fixed

    genericSave(room, "room_with_inhabitants.json");
  }

  @Test
  void saveCharacter() {
    Character c1 = createBaseCharacter("John", 10, 5);
    Wearable bow = createWearable(1, true);
    Wearable water = createWearable(2, false);

    addActiveWeapon(c1, bow);
    addItemToCharacter(c1, water);

    // TODO add skills and effects
    //createCharacterModifier(int health, int magic, int power, int howlong )
    genericSave(c1, "john.json");
  }

}
