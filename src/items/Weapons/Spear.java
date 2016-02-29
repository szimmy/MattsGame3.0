package items.Weapons;

import items.Item;
import items.Rarity;

/**
 * Unique weapon class for spears
 * @author Matthew Gimbut
 *
 */
public class Spear extends Weapon {

	public Spear(int atk, int def, int speedModifier, double weight, int hpBoost, int value, Rarity howRare,
			WeaponType weaponType) {
		super(atk, def, speedModifier, weight, hpBoost, value, howRare, weaponType);
		this.setImageLocation("Images\\Weapons\\" + this.getWeaponType().toString() + "\\Spear.png");
	}

	public Spear() {
		super(14, 0, 5, 8, 0, 200, Item.randomRareness(), Weapon.getRandomWeaponType());
		this.setImageLocation("Images\\Weapons\\" + this.getWeaponType().toString() + "\\spear\\Spear.png");
	}
	
	@Override
	public String getSimpleName() {
		return this.getWeaponType() + " spear";
	}

	@Override
	public String getItemToolTipText() {
		return "A " + this.getWeaponType() + " spear.";
	}

}
