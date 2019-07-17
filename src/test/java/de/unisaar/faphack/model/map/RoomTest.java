/**
 * 
 */
package de.unisaar.faphack.model.map;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import de.unisaar.faphack.model.Direction;
import de.unisaar.faphack.model.Item;

/**
 * @author ca
 *
 */
class RoomTest {

  /**
   * @throws java.lang.Exception
   */
  @BeforeAll
  static void setUpBeforeClass() throws Exception {
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterAll
  static void tearDownAfterClass() throws Exception {
  }

  /**
   * @throws java.lang.Exception
   */
  @BeforeEach
  void setUp() throws Exception {
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterEach
  void tearDown() throws Exception {
  }

  /**
   * Test method for {@link de.unisaar.faphack.model.map.Room#Room()}.
   */
  @Test
  final void testRoom() {
    // Create small room
    Tile t1 = new FloorTile();
    Tile t2 = new FloorTile();
    Tile t3 = new FloorTile();
    Tile t4 = new FloorTile();
    
    Tile[][] tiles = {{t1, t2},{t3, t4}};
    
    Room r = new Room(tiles);
    // TODO: what do we know about the room's world?
  }

  /**
   * Test method for {@link de.unisaar.faphack.model.map.Room#Room(de.unisaar.faphack.model.map.Tile[][])}.
   */
  @Test
  final void testRoomTileArrayArray() {
    fail("Not yet implemented"); // TODO
  }

  /**
   * Test method for {@link de.unisaar.faphack.model.map.Room#getNextTile(de.unisaar.faphack.model.map.Tile, de.unisaar.faphack.model.Direction)}.
   */
  @Test
  final void testGetNextTile() {
    // Create small room
    List<Item> items = new ArrayList<Item>();
    Tile t1 = new FloorTile(0, 0, new Room(), items);
    Tile t2 = new FloorTile(0, 1, new Room(), items);
    Tile t3 = new FloorTile(1, 0, new Room(), items);
    Tile t4 = new FloorTile(1, 1, new Room(), items);
    
    Tile[][] tiles = {{t1, t2},{t3, t4}};
    
    Room r = new Room(tiles);
    
    // Check right neighbor
    Direction d = new Direction(1,0);
    Tile destination = r.getNextTile(t1, d);
    assert(destination == t3);
    
    // Check upper neighbor
    d = new Direction(0, -1);
    System.out.println(t4.x);
    System.out.println( t4.y);
    destination = r.getNextTile(t4, d);
    assert(destination == t3);
    
    // Check diagonal
    d = new Direction(1, 1);
    destination = r.getNextTile(t1, d);
    assert(destination == t4);
    
    // Check non-adjacent tile, TODO: new bigger Room
    d = new Direction(0, 8);
    destination = r.getNextTile(t1, d);
    assert(destination == null);
    
    // Check moving outside the room
    d = new Direction(0, -1);
    destination = r.getNextTile(t1, d);
    assert(destination == null);

  }

}
