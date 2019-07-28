package de.unisaar.faphack.model;

import de.unisaar.faphack.model.map.Room;
import de.unisaar.faphack.model.map.Tile;
import de.unisaar.faphack.model.map.WallTile;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static de.unisaar.faphack.model.TestUtils.*;
import static de.unisaar.faphack.model.TestUtils.placeCharacter;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {

  /**
   * Check if the a character is able to pickup the item correctly.
   * The item should no longer be in the tiles items list but in the inventory of the character
   */
  @Test
  void pickUp() {
    Game game = TestUtils.createGame();
    Room room = game.getWorld().getMapElements().get(0);
    Character testObject = createBaseCharacter("Foo", 2, 2);
    addCharacter(room, 1, 2, testObject);
    Wearable item1 = createWearable(2, false);

    placeItemsInRoom(room, 1,2,item1);
    assertTrue(game.pickUp(testObject, item1));
    // the item should have been removed from the tile and moved into the inventory of the character
    assertTrue(testObject.items.contains(item1));
    assertTrue(!room.getTiles()[1][2].onTile().contains(item1));
    assertEquals(testObject, item1.character);
    assertNull(item1.onTile);
    Fixtures fountain = new Fixtures();
    placeItemsInRoom(room, 1,2, fountain);
    assertFalse(game.pickUp(testObject, fountain));

    //test armor
    Wearable armor1 = createArmor(2,0,0);
    placeItemsInRoom(room, 1,2,armor1);
    assertTrue(game.pickUp(testObject,armor1));

  }

  /**
   * The game.move method returns <code>true</code> if a character was able to perform a move action
   * and <code>false</code> otherwise
   */
  @Test
  void move() {
    Game game = TestUtils.createGame();
    Room room = game.getWorld().getMapElements().get(2);
    Character testObject = room.getInhabitants().get(0);
    assertTrue(game.move(testObject, new Direction(1, 0)));
    assertTrue(game.move(testObject, new Direction(0, -1)));
    assertTrue(game.move(testObject, new Direction(-1, 0)));
    assertFalse(game.move(testObject, new Direction(-1, 0)));
  }

  /**
   * The game.listItems() method returns a list of all Items on a tile, which is determined by
   *  a character and a direction.
   *  1. get all items on the tile which is left of the character
   *  2. get all items on the tile of the character
   */
  @Test
  void listItems() {
    Game game = TestUtils.createGame();
    Room room = game.getWorld().getMapElements().get(0);
    Character testObject = room.getInhabitants().get(0);
    Wearable item1 = createWearable(2, false);
    Wearable item2 = createWearable(2, false);
    placeItemsInRoom(room, 1,2,item1, item2);
    ArrayList<Item> expected = new ArrayList<>(Arrays.asList(new Item[]{item1, item2}));
    List<Item> actual = game.listItems(testObject, new Direction(-1, 0));
    for(Item item : actual){
      assertTrue(expected.contains(item));
      expected.remove(item);
    }
    assertTrue(expected.isEmpty());
    placeCharacter(testObject, room.getNextTile(testObject.tile, new Direction(-1, 0)));
    expected = new ArrayList<>(Arrays.asList(new Item[]{item1, item2}));
    actual = game.listItems(testObject, new Direction(0, 0));
    for(Item item : actual){
      assertTrue(expected.contains(item));
      expected.remove(item);
    }
    assertTrue(expected.isEmpty());
  }

  /**
   * Resting will increase the character's power by 5
   */
  @Test
  void rest() {
    Game game = createGame();
    Character character = game.getWorld().getMapElements().get(0).getInhabitants().get(0);
    System.out.println(character.power);
    game.rest(character);
    assertEquals(15, character.getPower());
  }

  @Test
  void drop() {
    Game game = createGame();
    // this character has only one wearable in its inventory, which is also the character's active weapon
    Character character = game.getWorld().getMapElements().get(0).getInhabitants().get(0);
    Armor armor = createArmor(1,1,1);
    equipArmor(armor, character);
    Wearable sword = character.getActiveWeapon();
    assertTrue(game.drop(character, sword));
    assertTrue(character.tile.onTile().contains(sword));
    // now remove the armor from the inventory
    assertTrue(character.dropItem(armor));
    // try to remove an item which is not part of the inventory : returns false
    Wearable w = createWearable(1,false);
    assertFalse(game.drop(character, w));
  }

  @Test
  void equip() {
    Game game = createGame();
    // this character has only one wearable in its inventory, which is also the character's active weapon
    Character character = game.getWorld().getMapElements().get(0).getInhabitants().get(0);

    // Equip an armor
    Armor armor = createArmor(1,1,1);
    character.items.add(armor);
    // the armor should be in the character's armor list
    assertTrue(game.equip(character, armor));

    // Equip a weapon
    Wearable weapon = createWearable(1, true);
    character.items.add(weapon);
    assertTrue(game.equip(character,weapon));

    // Illegal equip ( item not in inventory)
    Wearable item = createWearable(1, true);
    assertFalse(game.equip(character,item));
  }

  /**
   * several attack cases:
   * 1. Check if attacks are in range
   *    1.1. sword (short range, requires power)
   *    1.2. bow (long range, requires power)
   *    1.3. magical staff (long range, requires magic ))
   * 2. Attack destructible tiles
   * 3. Attack other characters
   */
  @Test
  void attack() {
    Game game = TestUtils.createGame();
    Room room = game.getWorld().getMapElements().get(0);
    Character foo = createBaseCharacter("Foo", 10, 2);
    Character bar = createBaseCharacter("Bar", 2, 2);
    // powerful wizard
    Character fnord = createBaseCharacter("Fnord", 15, 2);

    addCharacter(room, 1, 1, foo);
    addCharacter(room, 1, 2, bar);
    addCharacter(room, 4, 2, fnord);


    // create three weapons
    Weapon sword = createWeapon(1, true, 1, false, -10, 0, -1, 1);
    Weapon bow = createWeapon(1, true, 4, false, -8, 0, -1, 1);
    Weapon magicalStaff = createWeapon(1, true, 10, true, -10, -15, -1, 1);

    // Foo equips sword
    foo.items.add(sword);
    game.equip(foo, sword);
    // Bar equips bow
    bar.items.add(bow);
    game.equip(bar, bow);
    // Fnord equips magical staff
    fnord.items.add(magicalStaff);
    game.equip(fnord, magicalStaff);

    // Foo attacks direction of Bar and hits because the weapon range reaches bar
    assertTrue(game.attack(foo, new Direction(0, 1)));
    assertEquals(90, bar.getHealth());
    
    // Bar moves and Foo attacks same direction but it does not hit Bar
    bar.move(room.getNextTile(bar.tile, new Direction(0,1 )));
    assertFalse(game.attack(foo, new Direction(0, 1)));

    // Bar attacks Foo direction with long range weapon - bow
    assertTrue(game.attack(bar, new Direction(0, -2)));
    assertEquals(92, foo.getHealth());

    // Fnord attacks Foo with long range weapon - magical staff
    assertTrue(game.attack(fnord, new Direction(-3, -1)));
    assertEquals(82, foo.getHealth());
    assertEquals(35, foo.getMagic());

    // Fnord tries to attack undestructible wall
    Tile undestructible = room.getTiles()[0][0];
    assertTrue(undestructible instanceof  WallTile && ((WallTile) undestructible).getDestructible() == 0);
    assertFalse(game.attack(fnord, new Direction(-4, -2)));

    // Fnord tries to attack destructible wall

  }
}