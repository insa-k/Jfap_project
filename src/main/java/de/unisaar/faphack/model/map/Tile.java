package de.unisaar.faphack.model.map;

import de.unisaar.faphack.model.Character;
import de.unisaar.faphack.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author
 *
 */
public abstract class Tile implements Storable, TraitOwner {

  protected int x;
  protected int y;

  /**
   * The room this tile is located in. This must not be null.
   */
  protected Room room;

  /**
   * The trait of this item.
   */
  protected String trait;

  public Tile() {

  }

  public Tile(int x, int y, Room room){
    this.room = room;
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public Room getRoom() {
    return room;
  }

  /**
   * Given the "vector" d, what's the tile you get in return? (Hint: ask the room)
   *
   * @return the next tile in direction d
   */
  public Tile getNextTile(Direction d) {
    // TODO please implement me!
    Tile t = room.getNextTile(this, d);
    return t;
  }
  
  /**
   * Computes all eight direct neighbours of this tile
   * @return a list with the neighbour tiles
   */
  public List<Tile> getNeighbourTiles() {
    List<Tile> neighbours = new ArrayList<Tile>();
    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        // Skip current tile
        if (i == 0 && j == 0) {
          continue;
        }
        neighbours.add(getNextTile(new Direction(i, j)));
      }
    }
    return neighbours;
  }

  /**
   * Can c proceed onto this tile?
   *
   * @return the current tile if you can move the Character c onto this tile, null
   *         otherwise
   */
  public abstract Tile willTake(Character c);

  /**
   *  Almost all tiles can not have items on them.
   * @return
   */
  public List<Item> onTile() {
    return Collections.emptyList();
  }

  @Override
  public String getTrait() { return trait; }

  /**
   * Most tiles have no trap
   */
  public Trap hasTrap() {
    return null;
  }

  public void marshal(MarshallingContext c) {
    c.write("x", x);
    c.write("y", y);
    c.write("room", room);
    c.write("trait", trait);
  }

  public void unmarshal(MarshallingContext c) {
    x = c.readInt("x");
    y = c.readInt("y");
    room = c.read("room");
    trait = c.readString("trait");
  }

  public boolean removeItem(Wearable what) {
    return false;
  }

  public boolean addItem(Wearable what){
    return false;
  }

  /**
   *  Almost all tiles can not be occupied by a character.
   */
  public boolean isOccupied(Character currentCharacter){
    return false;
  }

  /**
   * @return <code>null</code> for almost all tiles because they cannot be occupied by a character
   */
  public Character characterOnTile() { return null; }
  
  
  /**
   * Computes the distance between two tiles
   * @param otherTile: the destination tile
   * @return a Direction that specifies how to go from this tile to otherTile
   */
  public Direction getDistance(Tile otherTile) {
    int diffx = otherTile.getX() - x;
    int diffy = otherTile.getY() - y;
    return new Direction(diffx, diffy);
  }
}
