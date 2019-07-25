package de.unisaar.faphack.model.map;

import de.unisaar.faphack.model.Game;
import de.unisaar.faphack.model.MarshallingContext;
import de.unisaar.faphack.model.Storable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 *
 */
public class World implements Storable {
  public Game g;

  private List<Room> mapElements;

  public World() {}

  @Override
  public void marshal(MarshallingContext c) {
    c.write("g", this.g);
    c.write("mapElements", this.mapElements);
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    g = c.read("g");
    mapElements = new ArrayList<Room>();
    c.readAll("mapElements", mapElements);
    return;
  }

  public List<Room> getMapElements(){
    return mapElements;
  }
}
