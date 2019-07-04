package de.unisaar.faphack.model;

import java.util.List;
import java.util.Set;

/**
 * @author
 *
 */
public class Character {
	/**
	 * The position of the character.
	 */
	protected Tile tile;
	/**
	 * The characters inventory. The amount of items in the inventory is limited by
	 * the maxWeight value of a character.
	 */
	protected List<Item> items;
	/**
	 * The base health of the character, which can be modified by Effects.
	 */
	protected int health = 100;
	protected Wearable armor;
	/**
	 * An instance of Effect defining the base damage of the character.
	 */
	protected AdditiveEffect baseDamage;
	/**
	 * The maximal amount of weight the character can carry. The sum of the weight
	 * of all items in the character's inventory must not exceed this value.
	 */
	protected int maxWeight;
	/**
	 * All effects that currently apply on the character, for example damage or heal
	 * over time
	 */
	protected Set<Effect> activeEffects;
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
		// TODO Auto-generated method stub
	}

	/**
	 * Pick up the given Wearable. Returns true if the action is possible.
	 *
	 * @param what
	 * @return boolean
	 */
	public boolean pickUp(Wearable what) {
		// TODO Auto-generated method stub
	}

	/**
	 * @return void
	 */
	public void interact() {
		// TODO Auto-generated method stub
	}

}
