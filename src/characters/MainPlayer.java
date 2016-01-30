package characters;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.Stack;

import javax.swing.JOptionPane;

import gui.MessageGUI;
import items.Item;
import items.Armor.Armor;
import items.Armor.Boots;
import items.Armor.ChestPiece;
import items.Armor.Gloves;
import items.Armor.Helmet;
import items.Armor.Legs;
import items.Armor.Shield;
import items.Consumables.Drink;
import items.Consumables.Food;
import items.Consumables.Potion;
import items.Weapons.Weapon;

/**
 * Class to represent the current player.
 * @author Matthew Gimbut
 *
 */
public class MainPlayer extends Character {

	private static final long serialVersionUID = -678393137554282967L;
	private String currentImage = "Images\\Player\\PlayerNorth.png";
	public static final String FACING_NORTH = "Images\\Player\\PlayerNorth.png";
	public static final String FACING_SOUTH = "Images\\Player\\PlayerSouth.png";
	public static final String FACING_EAST = "Images\\Player\\PlayerEast.png";
	public static final String FACING_WEST = "Images\\Player\\PlayerWest.png";
	private final static int STARTING_LEVEL = 1;
	private final static int STARTING_CURRENT_HP = 100;
	private final static int STARTING_MAX_HP = 100;
	private final static int STARTING_ATK = 50;
	private final static int STARTING_DEF = 25;
	private final static int STARTING_SPD = 25;
	private final static int STARTING_MAX_CARRY = 150;
	private final String FILE_NAME = "Saves\\player1.ser";
	private final boolean APPEND_TO_FILE = true;
	private final boolean DO_NOT_APPEND = false;
	public final static int MAX_XP = 100;
	private LinkedList<Item> inventory;
	private int xp;
	private int gold;
	private Weapon weaponHandR;
	private Shield leftHand;
	private ChestPiece chestPiece;
	private Legs leggings;
	private Boots boots;
	private Gloves gloves;
	private Helmet helmet;
	
	/**
	 * Constructor for new players
	 * @param username A String for the player's username
	 */
	public MainPlayer(String username) {
		super(username , STARTING_LEVEL, STARTING_CURRENT_HP, STARTING_MAX_HP, STARTING_ATK, STARTING_DEF, STARTING_SPD, 0, STARTING_MAX_CARRY);
		this.xp = 95;
		this.gold = 0;
		this.inventory = new LinkedList<Item>();
	}

	public Weapon getWeaponHandR() {
		return weaponHandR;
	}

	public Shield getLeftHand() {
		return leftHand;
	}

	public ChestPiece getChestPiece() {
		return chestPiece;
	}

	public Legs getLeggings() {
		return leggings;
	}

	public Boots getBoots() {
		return boots;
	}

	public Gloves getGloves() {
		return gloves;
	}


	public Helmet getHelmet() {
		return helmet;
	}
	
	/**
	 * Unequips an item from the player. 
	 * Checks to see what type the item is and changes things accordingly.
	 * @param i The item to unequip
	 */
	public void unequip(Item i) {
		this.inventory.add(i);
		if (i instanceof Weapon) {
			this.weaponHandR = null;
		}
		else if (i instanceof Shield) {
			this.leftHand = null;
		}
		else if (i instanceof Helmet) {
			this.helmet = null;
		}
		else if (i instanceof ChestPiece) {
			this.chestPiece = null;
		}
		else if (i instanceof Legs) {
			this.leggings = null;
		}
		else if (i instanceof Gloves) {
			this.gloves = null;
		}
		else{
			this.boots = null;
		}
		unequipUpdateStats(i);
	}
	
	/**
	 * Method to equip weapons 
	 * @param w The weapon to equip
	 */
	public void equip(Weapon w) {
			if(this.weaponHandR == null) {
				this.weaponHandR = w;
				this.inventory.remove(w);
			}
			else {
				 this.inventory.add(this.weaponHandR);
				 unequipUpdateStats(this.weaponHandR);
				 this.weaponHandR = w;
				 this.inventory.remove(w);
			}
		equipUpdateStats(w);
	}
	
	/**
	 * Method to equip armors
	 * @param a The armor to be equipped
	 */
	public void equip(Armor a) {
		if (a instanceof Boots) {
			if(this.boots == null) {
				this.boots = (Boots) a;
				this.inventory.remove(a);
			}
			else {
				 this.inventory.add(this.boots);
				 unequipUpdateStats(this.boots);
				 this.boots = (Boots) a;
				 this.inventory.remove(a);
			}
		}
		else if (a instanceof ChestPiece) {
			if(this.chestPiece == null) {
				this.chestPiece = (ChestPiece) a;
				this.inventory.remove(a);
			}
			else {
				 this.inventory.add(this.chestPiece);
				 unequipUpdateStats(this.chestPiece);
				 this.chestPiece = (ChestPiece) a;
				 this.inventory.remove(a);
			}
		}
		else if (a instanceof Gloves) {
			if(this.gloves == null) {
				this.gloves = (Gloves) a;
				this.inventory.remove(a);
			}
			else {
				 this.inventory.add(this.gloves);
				 unequipUpdateStats(this.gloves);
				 this.gloves = (Gloves) a;
				 this.inventory.remove(a);
			}
		}
		else if (a instanceof Helmet) {
			if(this.helmet == null) {
				this.helmet = (Helmet) a;
				this.inventory.remove(a);
			}
			else {
				 this.inventory.add(this.helmet);
				 unequipUpdateStats(this.helmet);
				 this.helmet = (Helmet) a;
				 this.inventory.remove(a);
			}
		}
		else if (a instanceof Legs) {
			if(this.leggings == null) {
				this.leggings = (Legs) a;
				this.inventory.remove(a);
			}
			else {
				 this.inventory.add(this.leggings);
				 unequipUpdateStats(this.leggings);
				 this.leggings = (Legs) a;
				 this.inventory.remove(a);
			}
		}
		else if(a instanceof Shield) {
			if(this.leftHand == null) {
				this.leftHand = (Shield) a;
				this.inventory.remove(a);
			}
			else {
				 this.inventory.add(this.leftHand);
				 unequipUpdateStats(this.leftHand);
				 this.leftHand = (Shield) a;
				 this.inventory.remove(a);
			}
		}
		equipUpdateStats(a);
	}

	public void consume(Potion p) {
		removeSingleItem(p);
		modifyCurrentHP(p.getAmount());
	}
	
	public void consume(Food f) {
		
	}
	
	public void consume(Drink d) {
		
	}
	
	public LinkedList<Item> getInventory() {
		return inventory;
	}
	
	/**
	 * Removes a certain number of a single type of item from 
	 * the player's inventory.
	 * @param itemName A string containing the item name 
	 * @param howMany How many to drop
	 */
	public void removeItems(String itemName, int howMany) {
		int count = 0;
		for (int i = 0; i < howMany; i++) {
			for (Item item : inventory) {
				if (itemName.toLowerCase().equals(item.getSimpleName().toLowerCase())) {
					count++;
					inventory.remove(item);
				}
			}
		}
		JOptionPane.showConfirmDialog(null, "Removed " + count + " " + itemName.toLowerCase() + "(s).");
	}
	
	/**
	 * Adds a single item to the inventory
	 * @param i The item to be added
	 */
	public boolean addItem(Item i) {
		if (i.getWeight() <= (getCarryCap() - getCurrentCarry())) {
			this.modifyCurrentCarry(i.getWeight());
			this.inventory.add(i);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Removes a single item from the inventory
	 * @param i The item to be removed
	 */
	public void removeSingleItem(Item i) {
		this.inventory.remove(i);
		this.modifyCurrentCarry(-i.getWeight());
	}
	
	public void setCurrentImage(String image) {
		this.currentImage = image;
	}

	public int getXp() {
		return xp;
	}

	public void increaseXP(int xp) {
		if(this.xp + xp < 100) {
			this.xp += xp;
		}
		else {
			lvlUp((this.xp + xp) - 100);
		}
	}
	
	/**
	 * Method to level up the character and increase stats
	 */
	public void lvlUp(int leftoverXP) {
		super.lvlUp(leftoverXP);
		this.xp = leftoverXP;
	}
	
	public int getGold() {
		return gold;
	}
	
	public void modifyGold(int gold) {
		this.gold += gold;
	}
}
