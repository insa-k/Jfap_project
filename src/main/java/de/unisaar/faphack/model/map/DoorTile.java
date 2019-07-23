package de.unisaar.faphack.model.map;

import de.unisaar.faphack.model.Character;
import de.unisaar.faphack.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 *
 */
public class DoorTile extends WallTile implements Storable, Observable<DoorTile> {
  private boolean open = false;

  private boolean locked = false;

  private Hallway hallway;

  private List<Observer<DoorTile>> observers;

  /**
   * To be opened by an item (key) the Effect of that item needs to create a m
   * atching ID.
   */
  private int keyId;

  public DoorTile() {
  }

  public DoorTile(int x, int y, Room room){
    super(x, y, room);
  }

  /**
   * 1. An character should not be able to enter a DoorTile if it is locked
   * 2. however the door can be opened by force if the character has enough power.
   * 3. And, if the door is open the characte can simply use it to get to another room
   */
  @Override
  public Tile willTake(Character c) {
    // TODO please implement me! (done)
    if (!(locked) || c.hasKey(keyId) || (destructible > 0 && c.getPower() >= destructible)) {
      locked = false;
      open = true;
      Hallway hallway = getHallway();
      // check if current tile is fromTile in Hallway and return the toTile
      if(hallway.from().equals(this)) {
        return hallway.to();
      }
      // otherwise return the "fromTile" --> in this case the actual toTile
      else {
        return hallway.from();
      }
    }
    return null;
  }

  @Override
  public void marshal(MarshallingContext c) {
    // TODO please implement me!
    super.marshal(c);
    c.write("DoorTile", this);
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    // TODO please implement me!
    super.unmarshal(c);
    c.read("DoorTile");
  }

  public Hallway getHallway(){
    return hallway;
  }

  @Override
  public String getTrait() { return open ? OPENDOOR : DOOR; }

  @Override
  public void register(Observer<DoorTile> observer) {
    // lazy initialization
    // TODO please implement me!

  }

  @Override
  public void notifyObservers(DoorTile object) {
    // TODO please implement me!
  }


}
