package items;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;

import items.Armor.Armor;
import items.Armor.Boots;
import items.Armor.ChestPiece;
import items.Armor.Gloves;
import items.Armor.Helmet;
import items.Armor.Legs;
import items.Armor.Shield;
import items.Consumables.Potion;
import items.Weapons.Axe;
import items.Weapons.Cleaver;
import items.Weapons.Dagger;
import items.Weapons.Mace;
import items.Weapons.RustyKnife;
import items.Weapons.Spear;
import items.Weapons.Sword;
import items.Weapons.Weapon;

/**
 * Abstract item class that any item must be a subclass of
 * @author Matthew Gimbut
 *
 */
public abstract class Item implements Serializable {

	private static final long serialVersionUID = 3550233679832297833L;
	private int atk;
	private int def;
	private String imageLocation;
	private int value;
	private Rarity howRare;
	private Random r;
	private int random;
	private int speedModifier;
	private double weight;
	private int hpBoost;
	
	/**
	 * Constructor for item that takes all the stats for the specific item, usually set in the subclass
	 * @param atk
	 * @param def
	 * @param speedModifier
	 * @param weight
	 * @param hpBoost
	 * @param image
	 * @param value
	 * @param howRare
	 */
	public Item(int atk, int def, int speedModifier, double weight, int hpBoost, int value, Rarity howRare) {
		this.r = new Random();
		random = r.nextInt(2);
		this.howRare = howRare;
		if(this instanceof Weapon) {
			this.atk = (int) Math.round((atk * (Item.rarityMultiplier(howRare)))) + random;
			this.def = (int) Math.round((def * (Item.rarityMultiplier(howRare))));
		}
		else if (this instanceof Armor) {
			this.atk = (int) Math.round((atk * (Item.rarityMultiplier(howRare))));
			this.def = (int) Math.round((def * (Item.rarityMultiplier(howRare)))) + random;
		}
		else {
			this.atk = (int) Math.round((atk * (Item.rarityMultiplier(howRare))));
			this.def = (int) Math.round((def * (Item.rarityMultiplier(howRare))));
		}
		this.value = (int) Math.round((value * (Item.rarityMultiplier(howRare)))) + random;
		this.speedModifier = speedModifier;
		this.weight = weight;
	}
	
	public int getAtk() {
		return atk;
	}

	public int getDef() {
		return def;
	}

	public String getImageLocation() {
		return imageLocation;
	}
	
	public void setImageLocation(String image) {
		this.imageLocation = image;
	}
	
	public int getValue() {
		return value;
	}
	
	public Rarity getHowRare() {
		return howRare;
	}
	
	public int getSpeedModifier() {
		return speedModifier;
	}
	
	public double getWeight() {
		return weight;
	}

	public int getHpBoost() {
		return hpBoost;
	}
	
	/**
	 * Generates a certain number of random items.
	 * Really just calls the generateRandomItem
	 * method a bunch of times in a loop.
	 * @param howMany
	 * @return
	 */
	public static LinkedList<Item> generateRandomItem(int howMany) {
		LinkedList<Item> randomItems = new LinkedList<Item>();
		for (int i = 0; i < howMany+1; i++) {
			randomItems.add(generateRandomItem());
		}
		return randomItems;
	}
	
	/**
	 * Generates a random item. 
	 * @return
	 */
	public static Item generateRandomItem() {
		Random randy = new Random();
		int randomItemNum = randy.nextInt(16);
		switch (randomItemNum) {
		case 1:
			return new RustyKnife();
		case 2: 
			 return new Cleaver();
		case 3: 
			return new Boots();
		case 4: 
			return new ChestPiece();
		case 5:
			return new Gloves();
		case 6: 
			return new Helmet();
		case 7: 
			return new Legs();
		case 8:
			return new Shield();
		case 9: 
			return new Dagger();
		case 10: 
			return new Sword();
		case 11: 
			return new Axe();
		case 12:
			return new Mace();
		case 13: 
			return new Spear();
		case 14: 
			return new Potion("Health", 75);
		default:
			return new Potion("Health", 80);
		}
	}
	
	/**
	 * Generates a random rareness for an item.
	 * @return The random rareness
	 */
	public static Rarity randomRareness() {
		Random randy = new Random();
		int random = randy.nextInt(150);
		if(random >= 0 && random < 25) {
			return Rarity.JUNK;
		}
		else if(random >= 25 && random < 120) {
			return Rarity.COMMON;
		}
		else if(random >= 120 && random < 135) {
			return Rarity.UNCOMMON;
		}
		else if(random >= 135 && random < 143) {
			return Rarity.RARE;
		}
		else if(random >= 143 && random < 148) {
			return Rarity.VERY_RARE;
		}
		else if(random >= 148 && random < 150) {
			return Rarity.LEGENDARY;
		}
		else {
			return Rarity.JUNK;
		}
	}
	
	/**
	 * Gets the rarity multiplier for a specific rarity.
	 * @param rare The specific rarity
	 * @return The multiplier
	 */
	public static double rarityMultiplier(Rarity rare) {
		switch(rare) {
		case JUNK: 
			return 0.85;
		case COMMON: 
			return 0.95;
		case UNCOMMON: 
			return 1.05;
		case RARE: 
			return 1.15;
		case VERY_RARE: 
			return 1.25;
		case LEGENDARY: 
			return 1.35;
		default:
			return 1.0;
		}
	}
	
	public abstract String getItemToolTipText();
	public abstract String getSimpleName();	
}
