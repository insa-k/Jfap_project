package de.unisaar.faphack.model;

import de.unisaar.faphack.model.effects.MoveEffect;
import de.unisaar.faphack.model.map.Room;
import de.unisaar.faphack.model.map.Tile;
import de.unisaar.faphack.model.map.World;

import java.util.List;
import java.util.Random;

/**
 * @author
 *
 */
public class Game implements Storable {
  private World world;
  private Character protagonist;

  public Game() {

  }

  /**
  * return the protagonist of this game
  */
  public Character getProtagonist() {
    return protagonist;
  }

  /**
   * tries to move the character into the given direction.
   * @param whom
   * @param direction
   * @return boolean
   */
  public boolean move(Character whom, Direction direction) {
    // TODO please implement me! (done)
    return new MoveEffect(direction).apply(whom);
  }

  /**
   * The character rests, i.e. it moves with direction (0,0) and its power increases by 5
  */
  public boolean rest(Character whom){
    // TODO please implement me! (done)
    whom.rest();
    return true;
  }

  /**
   *
   * @param who
   * @param direction
   * @return List<Item>
   */
  public List<Item> listItems(Character who, Direction direction) {
    Tile current = who.getTile();
    Tile next = current.getNextTile(direction);
    return next.onTile();
  }

  /**
   * Let a character pickup the given item
   * @param who the character
   * @param item the item to be picked up
   * @return boolean <code>true</code> if the character managed to pickup the item, <code>false</code> otherwise
   */
  public boolean pickUp(Character who, Item item) {
    // TODO please implement me!
    // TODO: fill this (done)
    return (item instanceof Wearable && who.pickUp((Wearable)item));


  }

  /**
   * Removes an item from the given characters inventory and places it on the tile
   * @param who the character performing the action
   * @param what the item to be removed
   * @return <code>true</code> if the action was successful, <code>false</code> otherwise
   */
  public boolean drop(Character who, Wearable what){
    // TODO please implement me!
    return who.dropItem(what);

  }

  /**
   * Equips the given Wearable as active Weapon or armor depending
   *
   * @param who the character performing the action
   * @param what the item to be equipped
   * @return <code>true</code> the action was successful, <code>false</code> otherwise
   */
  public boolean equip(Character who, Wearable what){
    // TODO please implement me!
    return who.equipItem(what);

  }

  @Override
  public void marshal(MarshallingContext c) {
    c.write("world", this.world);
    c.write("protagonist", this.protagonist);
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    world = c.read("world");
    protagonist = c.read("protagonist");
  }

  public World getWorld() {
    return world;
  }

  /** Add the game's protagonist to a random floor tile in the first room */
  public void setProtagonist(Character prot) {
    // TODO: fill here
    protagonist = prot;
    List<Room> mapElements = world.getMapElements();
    Room firstRoom = mapElements.get(0);
    // get random occupiable tile in first room and place protagonist there
    Tile[][] tiles = firstRoom.getTiles();
    // create random tiles until the randomTile can be occupied by protagonist
    // TODO: not sure about using a while true loop here...
    while (true) {
      int randomX = new Random().nextInt(tiles.length);
      int randomY = new Random().nextInt(tiles[randomX].length);
      Tile randomTile = tiles[randomX][randomY];
      if ( !(randomTile.willTake(prot).equals(null)) ) {
        protagonist.tile = randomTile;
        return;
      }
    }
  }

}
