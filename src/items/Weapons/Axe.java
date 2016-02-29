package items.Weapons;

import items.Item;
import items.Rarity;
/**
 * Generic weapon class for axes
 * @author Matthew Gimbut
 *
 */
public class Axe extends Weapon {

	public Axe(int atk, int def, int speedModifier, double weight, int hpBoost, int value, Rarity howRare,
			WeaponType weaponType) {
		super(atk, def, speedModifier, weight, hpBoost, value, howRare, weaponType);
		this.setImageLocation("Images\\Weapons\\" + this.getWeaponType().toString() + "\\Axe.png");
	}
	
	public Axe() {
		super(10, 0, 3, 6, 0, 180, Item.randomRareness(), Weapon.getRandomWeaponType());
		this.setImageLocation("Images\\Weapons\\" + this.getWeaponType().toString() + "\\axe\\Axe.png");
	}
	
	@Override
	public String getSimpleName() {
		return this.getWeaponType() + " axe";
	}
	
	@Override
	public String getItemToolTipText() {
		return "A " + this.getWeaponType() + " axe."; 
	}

}
