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
    // check if wall is already destroyed or if it is destructible
    if (destructible == -1  || (destructible > 0 && c.getPower() >= destructible)) {
      destructible = -1;
      locked = false;
      open = true;
      return getHallwayDestinationTile();
    }
    if (open || !(locked) || c.hasKey(keyId)) {
      locked = false;
      open = true;
      return getHallwayDestinationTile();
    }
    return null;
  }

  /**
   * returns the destination tile when going through a hallway
   * @return
   */
  private Tile getHallwayDestinationTile() {
    Hallway hallway = getHallway();
    // check if current tile is fromTile in Hallway and return the toTile
    if(hallway.from().equals(this)) {
      return hallway.to();
    }
    // otherwise return the "fromTile" --> in this case the actual toTile
    return hallway.from();
  }

  @Override
  public void marshal(MarshallingContext c) {
    super.marshal(c);
    c.write("open", (open ? 1 : 0));
    c.write("locked", (locked ? 1 : 0));
    c.write("hallway", hallway);
    c.write("keyId", keyId);
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    super.unmarshal(c);
    open = (c.readInt("open") == 1);
    locked = (c.readInt("locked") == 1);
    hallway = c.read("hallway");
    keyId = c.readInt("keyId");
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
