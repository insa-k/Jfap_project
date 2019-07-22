package de.unisaar.faphack.model.map;

import de.unisaar.faphack.model.Direction;
import de.unisaar.faphack.model.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoomTest {

  @Test
  void getNextTile() {
    Room room = TestUtils.createSimpleRoom(4,4, 1);
    Tile[][] tiles = room.getTiles();
    // down
    assertEquals(tiles[1][1], room.getNextTile(tiles[2][1],new Direction(-1,0)));
    // right
    assertEquals(tiles[2][2], room.getNextTile(tiles[2][1],new Direction(0,1)));
    // left
    assertEquals(tiles[2][0], room.getNextTile(tiles[2][1],new Direction(0,-1)));
    // up
    assertEquals(tiles[3][1], room.getNextTile(tiles[2][1],new Direction(1,0)));
    // down left
    assertEquals(tiles[1][0], room.getNextTile(tiles[2][1],new Direction(-1,-1)));
    // down right
    assertEquals(tiles[1][2], room.getNextTile(tiles[2][1],new Direction(-1,1)));
    // up left
    assertEquals(tiles[3][0], room.getNextTile(tiles[2][1],new Direction(1,-1)));
    // up right
    assertEquals(tiles[3][2], room.getNextTile(tiles[2][1],new Direction(1,1)));


  }
}