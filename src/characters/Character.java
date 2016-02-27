package characters;

import java.io.Serializable;
import java.util.Random;
import items.Item;
import main.GameController;

/**
 * Abstract character superclass for all characters appearing in the game.
 * @author Matthew Gimbut
 *
 */
public abstract class Character implements Serializable {

	private static final long serialVersionUID = -7026591060236208199L;
	private int lvl;
	private String name;
	private int currentHP;
	private int maxHP;
	private int atk;
	private int def;
	private int speed;
	private double carryCap;
	private double currentCarry;
	private boolean isDead;
	private String image;
	
	public Character(String name, int lvl, int currentHP, int maxHP, int atk, int def, int speed, int currentCarry, int carryCap) {
		this.lvl = lvl;
		this.name = name;
		this.currentHP = currentHP;
		this.maxHP = maxHP;
		this.atk = atk;
		this.def = def;
		this.speed = speed;
		this.currentCarry = currentCarry;
		this.carryCap = carryCap;
		this.isDead = false;
	}
	
	public Character(String name, int lvl, int currentHP, int maxHP, int atk, int def, int speed, int currentCarry, int carryCap, String image) {
		this.lvl = lvl;
		this.name = name;
		this.currentHP = currentHP;
		this.maxHP = maxHP;
		this.atk = atk;
		this.def = def;
		this.speed = speed;
		this.currentCarry = currentCarry;
		this.carryCap = carryCap;
		this.isDead = false;
		this.image = image;
	}
	
	/**
	 * Adds to base stats according to item equipped.
	 * @param i The item equipped
	 */
	public void equipUpdateStats(Item i) {
		this.atk  += i.getAtk();
		this.def += i.getDef();
		this.maxHP += i.getHpBoost();
		this.speed -= i.getSpeedModifier();	
	}
	
	
	/**
	 * Removes from base stats according to item unequipped.
	 * @param i The item unequipped
	 */
	public void unequipUpdateStats(Item i) {
		this.atk  -= i.getAtk();
		this.def -= i.getDef();
		this.maxHP -= i.getHpBoost();
		this.speed += i.getSpeedModifier();	
	}
	
	/**
	 * Method to level up the character and increase stats
	 */
	public void lvlUp(int leftoverXP) {
		this.lvl++;
		//GameController.playSound(""); //TODO
		Random r = new Random();
		this.speed += r.nextInt(3);
		this.atk += r.nextInt(2);
		this.def += r.nextInt(2);
		this.carryCap += r.nextInt(4);
		int hpBoost = r.nextInt(7);
		this.maxHP += hpBoost;
		this.currentHP += hpBoost;
	}
	
	public double getCurrentCarry() {
		return currentCarry;
	}

	public void setCurrentHP(int hp) {
		this.currentHP = hp;
	}
	
	public void modifyCurrentCarry(double currentCarry) {
		this.currentCarry += currentCarry;
	}
	
	public double getCarryCap() {
		return carryCap;
	}

	public void modifyCarryCap(double carryCap) {
		this.carryCap += carryCap;
	}
	
	public int getLvl() {
		return this.lvl;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getAtk() {
		return atk;
	}

	public void modifyAtk(int atk) {
		this.atk += atk;
	}

	public int getDef() {
		return def;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void modifyMaxHP(int hp) {
		this.maxHP += hp;
	}

	public void modifyDef(int def) {
		this.def += def;
	}

	public int getSpeed() {
		return speed;
	}

	public void modifySpd(int speed) {
		this.speed += speed;
	}
	
	
	public int getCurrentHP() {
		return currentHP;
	}

	public void modifyCurrentHP(int hpMod) {
		if (currentHP + hpMod <= maxHP) {
			currentHP += hpMod;
		}
		else {
			currentHP = maxHP;
		}
		
		if (currentHP < 0) {
			currentHP = 0;
		}
	}
	
	public void setDead(boolean dead) {
		this.isDead = dead;
	}
	
	public boolean getIsDead() {
		return this.isDead;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
