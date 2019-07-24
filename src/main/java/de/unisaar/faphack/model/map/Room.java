package de.unisaar.faphack.model.map;

import java.util.List;

import de.unisaar.faphack.model.Character;
import de.unisaar.faphack.model.Direction;
import de.unisaar.faphack.model.MarshallingContext;
import de.unisaar.faphack.model.Storable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 *
 */
public class Room implements Storable {

  /** The world this room belongs to */
  World w;

  /** The Characters that currently are in this room */
  private List<Character> inhabitants = new ArrayList<>();

  /**
   * A 2-dimensional Array defining the layout of the tiles in the room.
   */
  private Tile[][] tiles;

  public Room(){}

  /**
   /**
   * This method returns a tile determined by the specified tile <code> t </ code> and the <code> direction </ code> d.
   * If the path between the specified tile and the derived tile is blocked by a wall,
   * the wall tile is returned.
   *
   * HINT: use the computeDDA to compute the path
   *
   * @param t the start tile
   * @param d the direction to follow
   * @return
   */
  public Tile getNextTile(Tile t, Direction d) {
    // TODO please implement me!
    int new_x = t.x + d.x;
    int new_y = t.y + d.y;
    // move only one tile, allows diagonal movement
    int x_movement = t.x - new_x;
    int y_movement = t.y - new_y;
    // possible differences: -1, 1, 0 for x and y movement
    // if movement is possible return new tile else return null
    if(x_movement <= 1 && x_movement >= -1 && y_movement <= 1 && y_movement >= -1) {
      // check if new tile is still in the room (array)
      try { return this.tiles[new_x][new_y]; }
      catch (java.lang.ArrayIndexOutOfBoundsException e) { return null ; }
    }
    else { return null; }
  }


  private List<Tile> computeDDA(Tile t, Direction d){
    List<Tile> path = new ArrayList<>();

    // Calculate number of steps
    int steps = Math.abs(d.x) > Math.abs(d.y) ? Math.abs(d.x) : Math.abs(d.y);


    // Calculate increments
    double xIncrement = d.x / (float) steps;
    double yIncrement = d.y / (float) steps;

    // Compute points
    double x = t.x;
    double y = t.y;
    path.add(tiles[(int) x][((int) y)]);
    for (int i = 0; i < steps; i++) {
      x += xIncrement;
      y += yIncrement;
      if (x >= tiles.length || y >= tiles[0].length)
        break;
      Tile tile = tiles[(int) Math.round(x)][(int) Math.round(y)];
      path.add(tile);
    }
    return path;
  }

  public Tile[][] getTiles() {
    return tiles;
  }

  public List<Character> getInhabitants() {
    return inhabitants;
  }

  @Override
  public void marshal(MarshallingContext c) {
    // TODO please implement me!
    c.write("w", w);
    c.write("inhabitants", inhabitants);
    c.write("tiles", tiles);
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    // TODO please implement me!
    w = c.read("w");
    inhabitants = new ArrayList<Character>();
    c.readAll("inhabitants", inhabitants);
    tiles = c.readBoard("tiles");
  }
}
