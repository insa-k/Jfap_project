package de.unisaar.faphack.model;

import de.unisaar.faphack.model.effects.ModifyingEffect;
import de.unisaar.faphack.model.effects.MultiplicativeEffect;
import de.unisaar.faphack.model.map.Room;
import de.unisaar.faphack.model.map.Tile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author
 *
 */
public class Character extends AbstractObservable<TraitedTileOccupier>
implements Storable, TraitedTileOccupier {

  /**
   * I'm currently on this level
   */
  private int level = 0;

  /**
   * The position of the character.
   */
  protected Tile tile;
  /**
   * The characters inventory. The amount of items in the inventory is limited by
   * the maxWeight value of a character.
   */
  protected List<Wearable> items = new ArrayList<>();

  /**
   * The base health of the character, which can be modified by Modifiers.
   *
   * If health is zero, this character is dead!
   */
  int health = 100;

  /**
   * The base magic of the character, which can be modified by Modifiers.
   */
  int magic = 0;

  /**
   * The base power of the character, which can be modified by Modifiers.
   */
  int power = 0;

  /**
   * This models the character's trait, i.e., how effective are the different
   * skills of the character.
   */
  protected MultiplicativeEffect skills;

  /**
   * This might be shield / bodyarmor / etc.
   */
  protected List<Armor> armor = new ArrayList<>();

  /**
   * The maximal amount of weight the character can carry. The sum of the weight
   * of all items in the character's inventory plus the armor must not exceed this
   * value.
   */
  protected int maxWeight;

  /**
   * The currentWeight is the combined weights of armor, weapon and inventory
   */
  private int currentWeight = 0;

  /**
   * All effects that currently apply on the character, for example damage or heal
   * over time
   */
  protected Set<CharacterModifier> activeEffects = new HashSet<>();

  /**
   * That's my name
   */
  protected String name;

  /**
   * That's my role
   */
  protected String role;

  /**
   * The currently active weapon
   */
  protected Wearable activeWeapon;

  public Character() {

  }

  /**
   * Change my position to the given Tile.
   *
   * @param destination
   * @return void
   */
  public void move(Tile destination) {
    if (tile != null) {
      Room current = tile.getRoom();
      if (destination.getRoom() != current) {
        current.getInhabitants().remove(this);
        destination.getRoom().getInhabitants().add(this);
      }
    } else {
      destination.getRoom().getInhabitants().add(this);
    }
    tile = destination;
  }

  /**
   * Pick up the given Wearable. Returns true if the action is possible.
   * The character can only pickup an item if it is
   * 1. on the same tile
   * 2. the current weight of all items the character carries + the weight of the item is less then maxWeight
   *
   * @param what the item to be picked up
   * @return  boolean <code>true</code> if the action was successful, <code>false</code> otherwise
   */
  public boolean pickUp(Wearable what) {
    // TODO please implement me! (done?)
    //done
    if (what.getTile()!=this.getTile()){return false;}

    if ((what.weight + this.getWeight()) <= this.maxWeight){
      what.getTile().removeItem(what);
      what.pickUp(this);
      items.add(what);
      return true;
    }
    return false;
  }

  /**
   * @return void
   */
  public void interact() {
    // TODO Auto-generated method stub
    // Look around who or what you can interact with
    // List: 
    //  - Characters on neighboring tiles
    //  - Items to the current tile
    List<Character> neighbours = new ArrayList<Character>();
    List<Tile> otherTiles = tile.getNeighbourTiles();
    for (Tile t: tile.getNeighbourTiles()) {
      // Find out if Tile is occupied
      List<Character> inhabitants = t.getRoom().getInhabitants();
      if (t.isOccupied(this)){
        // Tile does not know which Character is currently on it
        // We have to loop over the room again
        for (Character character : inhabitants) {
          Tile character_tile = character.getTile();
          if (t.equals(character_tile)) {
            neighbours.add(character);
          }
        }
      }
    }
    // List items on current tile
    List<Item> availableItems = tile.onTile();
    
    // List options what you can do next
    // Print this or give string to GUI?
    System.out.printf("There are %d characters you can interact with:\n", neighbours.size());
    for (Character c: neighbours) {
      String position = String.format(" at position (%d, %d) ", c.getTile().getX(), c.getTile().getY());
      System.out.println(" - " + c.getName() + ", a " + c.getTrait() + position);
    }
    System.out.printf("There are %d items you can interact with:\n", availableItems.size());
    for (Item i: availableItems) {
      System.out.println(" - " + i.getTrait());
      if (i instanceof Wearable) {
        System.out.println("\tyou can try to pick it up");
      }
    }
    // (?) Call directly one of the interactable methods like attack or pickup?
  }

//  redundant because of getActiveWeapon()
//  public Wearable activeWeapon() {
//    return activeWeapon;
//  }

  public Tile getTile() {
    return tile;
  }

  public Room getRoom() {
    return tile.getRoom();
  }

  public int getHealth() {
    return health;
  }

  public String getName() {
    return name;
  }

  public int getLevel() {
    return level;
  }

  public int getMagic() {
    return magic;
  }

  public int getPower() {
    return power;
  }

  public int getMaxWeight() {
    return maxWeight;
  }

  public Wearable getActiveWeapon() {
    return activeWeapon;
  }

  public int getWeight() {
    //added
    int weight = currentWeight;
    for (int x = 0; x < (items.size()); x++){
      Wearable item = items.get(x);
      weight += item.weight;
    }
    return weight;
  }

  public int levelDown() {
    return ++level;
  }

  public int levelUp() {
    return --level;
  }

  /**
   * Apply the effects of an attack, taking into account the armor
   */
  public void applyAttack(CharacterModifier eff) {
    /*
     * Example of an attack - an adversary uses his weapon (different dimensions,
     * like affecting health, armor, magic ability, and how long the effect
     * persists)
     *
     * - several factors modulate the outcome of this effect: current health
     * stamina, quality of different armors, possibly even in the different
     * dimensions.
     */
    CharacterModifier modif = eff;

      for (Wearable arm : armor) {
          Armor castArm = (Armor) arm;
          modif = castArm.getModifyingEffect().apply(eff);
      }
      applyItem(modif);
  }
  // Changed Modifier based on Effects should be specified in the Modifier to generelize and simplify
  /**
   * Apply the effects of, e.g., a poisoning, eating something, etc.
   */
  public void applyItem(CharacterModifier eff) {
    eff.applyTo(this);
  }

  /**
   * removes the given Item from the characters inventory
   * @param item the item to be removed
   * @return <code>true</code> if the action was successful, <code>false</code> otherwise
   */
  public boolean dropItem(Wearable item){
    // TODO please implement me!

    if (!items.contains(item)){return false;}

    if (item == this.activeWeapon){activeWeapon = null; }

    if (armor.contains(item)){armor.remove(item);}

    this.items.remove(item);
    item.drop(this.getTile());
    this.getTile().addItem(item);

    return true;
  }
  /**
   * Equips the given Wearable as active Weapon or armor depending
   * @param wearable the item to be equipped
   * @return <code>true</code> the action was successful, <code>false</code> otherwise
   */
  public boolean equipItem(Wearable wearable){
    // TODO please implement me!
      if (items.contains(wearable)) {
        if (wearable instanceof Armor) {
          armor.add((Armor)wearable);
          return true;
        }
        if (wearable.isWeapon) {
          this.activeWeapon = wearable;
          return true;
        }
      }
    return false;
  }

  /**
   * Checks if a character has a key to open a given door in his inventory
   * @param keyId
   * @return <code>true</code> if character has a key with given keyId in items, <code>false</code> otherwise
   */
  public boolean hasKey(int keyId) {
    for (Item item : items) {
      // TODO: check if item is a key and the keyId matches the given one
      if (item.getTrait().equals(TraitOwner.KEY)) {
        Key key = (Key) item;
        if (key.matchedKeyId(keyId)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public String getTrait() { return (health == 0 ? "DEAD_" : "") + role; }

  @Override
  public void marshal(MarshallingContext c) {
    c.write("level", level);
    c.write("Tile", tile);
    c.write("Items", items);
    c.write("Health", health);
    c.write("Magic", magic);
    c.write("Power", power);
    c.write("Skills", skills);
    c.write("Armor", armor);
    c.write("MaxWeight", maxWeight);
    c.write("CurrentWeight", currentWeight);
    c.write("ActiveEffects", activeEffects);
    c.write("Name", name);
    c.write("Role", role);
    c.write("ActiveWeapon", activeWeapon);
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    level = c.readInt("level");
    tile = c.read("Tile");
    items =  new ArrayList<>();
    c.readAll("Items", items);
    health = c.readInt("Health");
    magic = c.readInt("Magic");
    power = c.readInt("Power");
    skills = c.read("Skills");
    armor = new ArrayList<>();
    c.readAll("Armor", armor);
    maxWeight = c.readInt("MaxWeight");
    currentWeight = c.readInt("CurrentWeight");
    activeEffects = new HashSet<>();
    c.readAll("ActiveEffects", activeEffects);
    name = c.readString("Name");
    role = c.readString("Role");
    activeWeapon = c.read("ActiveWeapon");
  }

  public void rest() {
    this.power += 5;
  }
  
  public boolean initiateTrade(Character trader, Wearable give, Wearable get) {
    // Check if trading characters stand next to each other
    Tile t1 = this.getTile();
    Tile t2 = trader.getTile();
    Direction diff = t1.getDistance(t2);
    //Direction diff = this.getTile().getDistance(trader.getTile());
    if (diff.x > 1 || diff.y > 1 || diff.x < -1 || diff.y < -1) {
      return false;
    }
    // Check if this character possess the give Wearable
    if (!(items.contains(give))) {
      return false;
    }
    // Check if trade is ok from trading partner
    if (trader.receiveTrade(this, get, give)){  //Switch get and give items
      items.remove(give);
      items.add(get);
      // Tell items that you traded them
      get.character = this;
      give.character = trader;
      return true;
    }
    return false;
  }
  
  public boolean receiveTrade(Character trader, Wearable give, Wearable get) {
    if (!(items.contains(give))) {
      return false;
    }
    items.remove(give);
    items.add(get);
    return true;
  }
}
