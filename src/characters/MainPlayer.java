package characters;

import java.util.LinkedList;
import javax.swing.JOptionPane;
import items.Item;
import items.Armor.Armor;
import items.Armor.Boots;
import items.Armor.ChestPiece;
import items.Armor.Gloves;
import items.Armor.Helmet;
import items.Armor.Legs;
import items.Armor.Shield;
import items.Consumables.Potion;
import items.Weapons.Weapon;

/**
 * Class to represent the current player.
 * @author Matthew Gimbut
 *
 */
public class MainPlayer extends Character {

	private static final long serialVersionUID = -678393137554282967L;
	public static final String FACING_NORTH = "Images\\Player\\PlayerNorth.png";
	public static final String FACING_SOUTH = "Images\\Player\\PlayerSouth.png";
	public static final String FACING_EAST = "Images\\Player\\PlayerEast.png";
	public static final String FACING_WEST = "Images\\Player\\PlayerWest.png";
	private final static int STARTING_LEVEL = 1;
	private final static int STARTING_CURRENT_HP = 100;
	private final static int STARTING_MAX_HP = 100;
	private final static int STARTING_ATK = 50;
	private final static int STARTING_DEF = 25;
	private final static int STARTING_SPD = 30;
	private final static int STARTING_MAX_CARRY = 150;
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
	
	//Player settings
	private int textScrollingSpeed;
	
	/**
	 * Constructor for new players
	 * @param username A String for the player's username
	 */
	public MainPlayer(String username) {
		super(username , STARTING_LEVEL, STARTING_CURRENT_HP, STARTING_MAX_HP, STARTING_ATK, STARTING_DEF, STARTING_SPD, 0, STARTING_MAX_CARRY);
		xp = 0;
		gold = 0;
		textScrollingSpeed = 35; //Milliseconds
		inventory = new LinkedList<Item>();
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
			weaponHandR = null;
		} else if (i instanceof Shield) {
			leftHand = null;
		} else if (i instanceof Helmet) {
			helmet = null;
		} else if (i instanceof ChestPiece) {
			chestPiece = null;
		} else if (i instanceof Legs) {
			leggings = null;
		} else if (i instanceof Gloves) {
			gloves = null;
		} else{
			boots = null;
		}
		unequipUpdateStats(i);
	}
	
	/**
	 * Method to equip weapons 
	 * @param w The weapon to equip
	 */
	public void equip(Weapon w) {
			if(this.weaponHandR == null) {
				weaponHandR = w;
				inventory.remove(w);
			} else {
				inventory.add(this.weaponHandR);
				unequipUpdateStats(this.weaponHandR);
				weaponHandR = w;
				inventory.remove(w);
			}
		equipUpdateStats(w);
	}
	
	/**
	 * Method to equip armors
	 * @param a The armor to be equipped
	 */
	public void equip(Armor a) {
		if (a instanceof Boots) {
			if(boots == null) {
				boots = (Boots) a;
				inventory.remove(a);
			} else {
				inventory.add(boots);
				unequipUpdateStats(boots);
				boots = (Boots) a;
				inventory.remove(a);
			}
		} else if (a instanceof ChestPiece) {
			if(chestPiece == null) {
				chestPiece = (ChestPiece) a;
				inventory.remove(a);
			} else {
				inventory.add(chestPiece);
				unequipUpdateStats(chestPiece);
				chestPiece = (ChestPiece) a;
				inventory.remove(a);
			}
		} else if (a instanceof Gloves) {
			if(gloves == null) {
				gloves = (Gloves) a;
				inventory.remove(a);
			} else {
				inventory.add(gloves);
				unequipUpdateStats(gloves);
				gloves = (Gloves) a;
				inventory.remove(a);
			}
		} else if (a instanceof Helmet) {
			if(helmet == null) {
				helmet = (Helmet) a;
				inventory.remove(a);
			} else {
				inventory.add(helmet);
				unequipUpdateStats(helmet);
				helmet = (Helmet) a;
				inventory.remove(a);
			}
		} else if (a instanceof Legs) {
			if(leggings == null) {
				leggings = (Legs) a;
				inventory.remove(a);
			} else {
				inventory.add(leggings);
				unequipUpdateStats(leggings);
				leggings = (Legs) a;
				inventory.remove(a);
			}
		} else if(a instanceof Shield) {
			if(leftHand == null) {
				leftHand = (Shield) a;
				inventory.remove(a);
			} else {
				inventory.add(leftHand);
				unequipUpdateStats(leftHand);
				leftHand = (Shield) a;
				inventory.remove(a);
			}
		}
		equipUpdateStats(a);
	}

	public void consume(Potion p) {
		removeSingleItem(p);
		modifyCurrentHP(p.getAmount());
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

	public int getTextScrollingSpeed() {
		return textScrollingSpeed;
	}

	public void setTextScrollingSpeed(int textScrollingSpeed) {
		this.textScrollingSpeed = textScrollingSpeed;
	}
}
