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
  }

  public Wearable activeWeapon() {
    return activeWeapon;
  }

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

    for(int i=0 ;i < this.items.size(); i++){

      if(items.get(i).equals(item)){
        this.items.remove(item);
        item.drop(this.getTile());
        this.getTile().addItem(item);
        return true;
      }

    }
    return false;

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
    // TODO please implement me!
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
    c.write("ActiveWeapon", activeWeapon);
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    // TODO please implement me!
    level = c.readInt("level");
    tile = c.read("Tile");
    items = c.read("Items");
    health = c.readInt("Health");
    magic = c.readInt("Magic");
    power = c.readInt("Power");
    skills = c.read("Skills");
    //restliche unmarshals noch schreiben

  }

  public void rest() {
    this.power += 5;
  }
}
